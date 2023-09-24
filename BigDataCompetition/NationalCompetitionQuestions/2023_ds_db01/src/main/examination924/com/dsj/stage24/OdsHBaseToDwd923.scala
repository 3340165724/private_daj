package com.dsj.stage24

import org.apache.hadoop.hbase.{CompareOperator, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.filter.{RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.functions.{col, lit, to_timestamp}
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
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    val ods_tables = Array("order_master", "order_detail", "product_browse")
    val dwd_tables = Array("fact_order_master", "fact_order_detail", "fact_product_browse")
    val hbase_tables = Array("hbase_order_master", "hbase_order_detail", "hbase_product_browse")
    for (i <- 0 until ods_tables.length) {
      val ods_table = ods_tables(i)
      val dwd_table = dwd_tables(i)
      val hbase_table = hbase_tables(i)

      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230923'")
        .drop("etl_date")
      val hbase_df = readeHBaseDf(hbase_table, ods_df, spark)

      var all_df = ods_df.unionByName(hbase_df).coalesce(1)
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        .withColumn("etl_date", lit("20230923"))
      if (ods_table.equals("order_master")) {
        all_df = all_df
          .withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
          .withColumn("shipping_time", to_timestamp(col("shipping_time"), "yyyyMMddHHmmss"))
          .withColumn("pay_time", to_timestamp(col("pay_time"), "yyyyMMddHHmmss"))
          .withColumn("receive_time", to_timestamp(col("receive_time"), "yyyyMMddHHmmss"))
      } else if (ods_table.equals("order_detail")) {
        all_df = all_df
          .withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
      }
      all_df.printSchema()
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
    }

    // 关闭
    spark.stop()

  }

  def readeHBaseDf(hbaseName: String, odsDf: DataFrame, spark: SparkSession): DataFrame = {
    val fields = odsDf.schema.fields

    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "172.20.37.85,172.20.37.7,172.20.37.237")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    val connection = ConnectionFactory.createConnection(hbaseConf)

    val table = connection.getTable(TableName.valueOf(hbaseName))

    val scan = new Scan()
    scan.setFilter(new RowFilter(CompareOperator.EQUAL, new SubstringComparator("20220323")))

    val resultScanner = table.getScanner(scan)
    val list = resultScanner.iterator().asScala.map(x => {
      val values = fields.map(field => {
        val name = field.name
        val tp = field.dataType.typeName
        val value = tp match {
          case "long" => Bytes.toLong(x.getValue("info".getBytes(), name.getBytes()))
          case "integer" => Bytes.toInt(x.getValue("info".getBytes(), name.getBytes()))
          case "double" => Bytes.toBigDecimal(x.getValue("info".getBytes(), name.getBytes())).doubleValue()
          case "float" => Bytes.toFloat(x.getValue("info".getBytes(), name.getBytes()))
          case typ if typ.contains("decimal") => Bytes.toBigDecimal(x.getValue("info".getBytes(), name.getBytes())).doubleValue()
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
