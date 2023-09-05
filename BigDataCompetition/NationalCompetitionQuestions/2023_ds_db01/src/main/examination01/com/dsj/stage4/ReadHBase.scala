package com.dsj.stage4

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

import java.sql.Timestamp
import scala.jdk.CollectionConverters.asScalaIteratorConverter
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.Date


/**
 * 读取hbase数据工具类（通用）
 * */
object ReadHBase {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport() // 开启hive支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置分区的模式是非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在用引号引起来的列名称中使用正则表达式
      .getOrCreate()

    spark.sparkContext.setLogLevel("error")

    val ods_tables = Array("order_master", "order_detail", "product_info")
    val dwd_tables = Array("fact_order_master", "fact_order_detail", "dim_product_info")
    val date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    for (i <- 0 until (ods_tables.length)) {

      val ods_df = spark.sql(s"select * from 2023_ods1_ds_db01.${ods_tables(i)} where etl_date='20230427' ")
        .drop("etl_date")

      val dwd_df = readHbaseDf(spark, ods_df, ods_tables(i))

      var final_df = ods_df.unionByName(dwd_df)
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(date).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(date).cast("timestamp"))

      if (ods_tables(i).equals("order_master")) {

        final_df = final_df.withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
          .withColumn("shipping_time", to_timestamp(col("shipping_time"), "yyyyMMddHHmmss"))
          .withColumn("pay_time", to_timestamp(col("pay_time"), "yyyyMMddHHmmss"))
          .withColumn("receive_time", to_timestamp(col("receive_time"), "yyyyMMddHHmmss"))

      } else if (ods_tables(i).equals("order_detail")) {
        final_df = final_df.withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))

      }

      final_df.createOrReplaceTempView("newtable")
      println("ods数据" + ods_df.count())
      println("dwd数据" + dwd_df.count())
      println("合并后数据" + final_df.count())
      spark.sql(s"insert overwrite 2023_dwd1_ds_db01.${dwd_tables(i)} partition(etl_date='20230427') select * from newtable ")

      println(dwd_tables(i) + "插入成功")
    }

    spark.stop()
  }

  def readHbaseDf(sparkSession: SparkSession, df: DataFrame, name: String): DataFrame = {

    val fields = df.schema.fields
    val config = HBaseConfiguration.create()
    config.set("hbase.zookeeper.quorum", "master:2181,slave1:2181,slave2:2181")

    val conn = ConnectionFactory.createConnection(config)
    val table = conn.getTable(TableName.valueOf("hbase_" + name))

    val scan = new Scan()

    val list = table.getScanner(scan).iterator().asScala.filter(x => {

      val row = Bytes.toString(x.getRow)
      val str = row.substring(1, 9)
      str.contains("20221001")

    }).map(x => {

      val values = fields.map(filed => {

        val col = filed.name
        val value = filed.dataType.typeName match {
          case "long" => Bytes.toLong(x.getValue("info".getBytes(), col.getBytes()))
          case "double" => Bytes.toBigDecimal(x.getValue("info".getBytes(), col.getBytes())).doubleValue()
          case "integer" => Bytes.toInt(x.getValue("info".getBytes(), col.getBytes()))
          case "float" => Bytes.toFloat(x.getValue("info".getBytes(), col.getBytes()))
          case typestr if typestr.contains("decimal") => Bytes.toBigDecimal(x.getValue("info".getBytes(), col.getBytes()))
          case "timestamp" => Timestamp.valueOf(Bytes.toString(x.getValue("info".getBytes(), col.getBytes())))
          case _ => Bytes.toString(x.getValue("info".getBytes(), col.getBytes()))
        }

        value
      })

      Row(values: _*)

    }).toList

    val df2 = sparkSession.createDataFrame(sparkSession.sparkContext.makeRDD(list), df.schema)
    df2

  }
}
