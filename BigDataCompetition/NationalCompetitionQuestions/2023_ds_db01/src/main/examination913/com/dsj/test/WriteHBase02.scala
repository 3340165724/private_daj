package com.dsj.test

import org.apache.hadoop.hbase.client.{ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants, TableName}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.{Properties, Random}

/**
 * create "hbase_order_master","info"
 * create "hbase_order_detail","info"
 * create "hbase_product_browse","info"
 */
object WriteHBase02 {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setMaster("local").setAppName("read mysql write hbase")
      .set("spark.testing.memory", "471859200")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    //    连接数据的参数
    val MYSQLDBURL: String = "jdbc:mysql://172.20.37.78:3306/ds_db01?useUnicode=true&characterEncoding=utf-8" // mysql url地址
    val properties: Properties = new Properties()
    properties.put("user", "root") //用户名
    properties.put("password", "123456") // 密码
    properties.put("driver", "com.mysql.jdbc.Driver") // 驱动名称

    sparkSession.sql("show databases").show()
        val mySqlTable = Array("order_master", "order_detail", "product_browse")
//    val mySqlTable = Array("product_browse")
    val config = HBaseConfiguration.create()
    config.set(HConstants.ZOOKEEPER_QUORUM, "172.20.37.85,172.20.37.78,172.20.37.230")
    config.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181")
    val connection = ConnectionFactory.createConnection(config) //获取和hbase的链接
    //    val mySqlTable = Array("order_master")
    mySqlTable.foreach(x => {
      sparkSession.read.jdbc(MYSQLDBURL, x,
        properties).createOrReplaceTempView(x)
      var frame = sparkSession.sql(s"select * from $x where modified_time>='2023-09-12 00:00:00'")
        .withColumn("modified_time", from_unixtime(unix_timestamp(col("modified_time"), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"))
      println(frame.count())
      if (x.equals("order_master")) {
        frame = frame.withColumn("shipping_user", concat(col("shipping_user"), lit("test2")))
          .withColumn("order_money", col("order_money") + 2.5)
          .withColumn("order_id", col("order_id").cast("integer"))
          .withColumn("customer_id", col("customer_id").cast("integer"))
          .withColumn("order_point", col("order_point").cast("integer"))
      }
      else if (x.equals("order_detail")) {
        frame = frame.withColumn("product_name", concat(col("product_name"), lit("test2")))
          .withColumn("product_price", col("product_price") + 2.5)
          .withColumn("order_detail_id", col("order_detail_id").cast("integer"))
          .withColumn("product_id", col("product_id").cast("integer"))
          .withColumn("product_cnt", col("product_cnt").cast("integer"))
          .withColumn("w_id", col("w_id").cast("integer"))
          .withColumn("weight", col("weight").cast("float"))
      }
      else {
        frame = frame.withColumn("gen_order", lit(1))
          .withColumn("log_id", col("log_id").cast("integer"))
          .withColumn("product_id", col("product_id").cast("integer"))
          .withColumn("customer_id", col("customer_id").cast("integer"))
      }
      frame.show(5)
      //将数据写入hbase中
      val table = connection.getTable(TableName.valueOf("hbase_" + x)) //获取表对象
      val cols = frame.columns //所有列
      val fields = frame.schema.fields
      val sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val sdf2 = new SimpleDateFormat("yyyyMMddHHmmss")
      val putrdd = frame.collect().map(x => {
        val date = x.getAs("modified_time").toString
        val rowkey = new Random().nextInt(10) + sdf2.format(sdf1.parse(date))
        val put = new Put(rowkey.getBytes())
        for (i <- 0 until fields.length) {
          val col = fields(i).name
          fields(i).dataType.typeName match {
            case "long" => put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(x.getLong(i)))
            case "integer" =>
              put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(x.getInt(i).toInt))
            case "double" => put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(new java.math.BigDecimal(x.getDouble(i))))
            case "float" => put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(x.getFloat(i)))
            case typestr if typestr.contains("decimal") => put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(x.getDecimal(i)))
            case "timestamp" => put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(x.getTimestamp(i).toString))
            case _ => put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(col), Bytes.toBytes(x.getString(i)))
          }
        }
        table.put(put)    //写入hbase的表行数据
      })
      println(s"$x 插入成功")
    })

    sparkSession.stop()
  }

}
