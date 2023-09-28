package com.dsj.stage26

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.hadoop.mapred.JobConf
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.{Properties, Random}

/**
 * create "hbase_order_master","info"
 * create "hbase_order_detail","info"
 * create "hbase_product_info","info"
 */
object WriteHBase {
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
    val mySqlTable = Array("order_master", "order_detail", "product_info")
    mySqlTable.foreach(x => {
      sparkSession.read.jdbc(MYSQLDBURL, x,
        properties).createTempView(x)
      var frame = sparkSession.sql(s"select * from $x where modified_time>='2023-09-12 00:00:00'")
        .withColumn("modified_time", from_unixtime(unix_timestamp(col("modified_time"), "yyyy-MM-dd HH:mm:ss") + 600, "yyyy-MM-dd HH:mm:ss"))
      if (x.equals("order_master")) {
        frame = frame.withColumn("shipping_user", concat(col("shipping_user"), lit("test2")))
          .withColumn("order_money", col("order_money") + 2.5)
      }
      else if (x.equals("order_detail")) {
        frame = frame.withColumn("product_name", concat(col("product_name"), lit("test2")))
          .withColumn("product_price", col("product_price") + 2.5)
      }
      else {
        frame = frame.withColumn("gen_order", lit(1))
      }
      frame.show(5)
      //将数据写入hbase中
      val config = HBaseConfiguration.create()
      config.set(HConstants.ZOOKEEPER_QUORUM, "172.20.37.85,172.20.37.78,172.20.37.237")
      config.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181")
      val cols = frame.columns //所有列
      val fields = frame.schema.fields
      val sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val sdf2 = new SimpleDateFormat("yyyyMMddHHmmss")
      val putrdd = frame.rdd.map(x => {
        val date = x.getAs("modified_time").toString
        val rowkey = new Random().nextInt(10) + sdf2.format(sdf1.parse(date))
        val put = new Put(rowkey.getBytes())
        var i = 0
        fields.foreach(field => {
          val col = field.name
          field.dataType.typeName match {
            case "long" => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getAs[Long](col)))
            case "integer" => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getAs[Int](col)))
            case "double" => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(new java.math.BigDecimal(x.getAs[Double](col))))
            case "float" => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getAs[Float](col)))
            case typestr if typestr.contains("decimal") => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getDecimal(i)))
            case "timestamp" => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getTimestamp(i).toString))
            case _ => put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getAs[String](col)))
          }
          i += 1
        })
        (new ImmutableBytesWritable(), put)
      })
      val job = new JobConf(config)
      job.setOutputFormat(classOf[TableOutputFormat])
      job.set(TableOutputFormat.OUTPUT_TABLE, "hbase_" + x)
      putrdd.saveAsHadoopDataset(job)
      println(s"$x 插入成功")
    })

    sparkSession.stop()
  }

}
