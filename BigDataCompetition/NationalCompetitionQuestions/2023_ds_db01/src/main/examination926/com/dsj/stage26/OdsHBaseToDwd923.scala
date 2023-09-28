package com.dsj.stage26

import org.apache.hadoop.hbase.{CompareOperator, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.filter.{RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import scala.jdk.CollectionConverters.asScalaIteratorConverter

object OdsHBaseToDwd923 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "true")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    // todo ods数据和hbase数据合并存入dwd
    val ods_tables = Array()
    val dwd_tables = Array()
    val hbase_tables = Array()
    for(i <- 0 until ods_tables.length) {
      val ods_table = ods_tables(i)
      val dwd_table = dwd_tables(i)
      val hbase_table = hbase_tables(i)

      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230925'")
        .drop("etl_date")

      val hbase_df = readHbaseDf(hbase_table, ods_df, spark)

      var all_df = ods_df.unionByName(hbase_df)
        .withColumn("dwd_insert_user",lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("string"))
        .withColumn("dwd_modify_user",lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("string"))
        .withColumn("etl_date",lit("20230926"))
      if(ods_table.equals("order_master")){
        all_df = all_df
          .withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
          .withColumn("shipping_time",to_timestamp(col("shipping_time"),"yyyyMMddHHmmss"))
          .withColumn("pay_time",to_timestamp(col("pay_time"),"yyyyMMddHHmmss"))
          .withColumn("receive_time", to_timestamp(col("receive_time"),"yyyyMMddHHmmss"))
      }else if(ods_table.equals("order_detail")){
        all_df = all_df
          .withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
      }
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
    }


    // 关闭资源
    spark.stop()
  }

  def readHbaseDf(hbaseName: String, odsDf: DataFrame, spark: SparkSession): DataFrame = {
    val feilds = odsDf.schema.fields

    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "172.20.37.85,172.20.37.237,172.20.37.7")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "8121")
    val connection = ConnectionFactory.createConnection(hbaseConf)

    val table = connection.getTable(TableName.valueOf(hbaseName))

    val scan = new Scan()
    scan.setFilter(new RowFilter(CompareOperator.EQUAL, new SubstringComparator("")))

    val resultScanner = table.getScanner(scan)
    val list = resultScanner.iterator().asScala.map(x => {
      val values = feilds.map(feild => {
        val name = feild.name
        val tp = feild.dataType.typeName
        val value = tp match {
          case "long" => Bytes.toLong(x.getValue("info".getBytes(), name.getBytes()))
          case "double" => Bytes.toBigDecimal(x.getValue("info".getBytes(), name.getBytes())).doubleValue()
          case "float" => Bytes.toFloat(x.getValue("info".getBytes(), name.getBytes()))
          case typ if typ.contains("decimal") => Bytes.toBigDecimal(x.getValue("info".getBytes(), name.getBytes()))
          case "timestamp" => Timestamp.valueOf(Bytes.toString(x.getValue("info".getBytes(), name.getBytes())))
          case _ => Bytes.toString(x.getValue("info".getBytes(), name.getBytes()))
        }
        value
      })
      Row(values: _*)
    }).toList
    val hbase_df = spark.createDataFrame(spark.sparkContext.makeRDD(list), odsDf.schema)
    hbase_df
  }
}
