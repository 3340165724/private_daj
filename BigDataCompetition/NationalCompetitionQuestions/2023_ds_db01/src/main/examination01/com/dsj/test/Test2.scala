package com.dsj.test

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.Date
import scala.collection.convert.ImplicitConversions.`iterator asScala`


object Test2 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 隐式转换
    import spark.implicits._

    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    // 获取hbase连接
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "172.20.37.85,172.20.37.78,172.20.37.237")
    // zookeeper的端口
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    // 获取连接
    val connection = ConnectionFactory.createConnection(hbaseConf)

    // 拿出ods中最新分区的数据
    val ods_df = spark.sql(s"select * from ods.product_browse where etl_date='20230911'")
    // 从hbase中拿出数据
    val table = connection.getTable(TableName.valueOf("dsj:product_browse"))
    // 创建查询方式
    val scan = new Scan()
    // 执行查询
    val resultScanner = table.getScanner(scan)
    val hbase_df = resultScanner.iterator().map(result =>{
      val log_id = Bytes.toInt(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("log_id")))
      val product_id = Bytes.toInt(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("product_id")))
      val customer_id = Bytes.toInt(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("customer_id")))
      val gen_order = Bytes.toInt(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("gen_order")))
      val order_sn = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("order_sn")))
      val modified_time = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("modified_time")))
      (log_id,product_id,customer_id,gen_order,order_sn,modified_time)
    }).toList.toDF("log_id","product_id","customer_id","gen_order","order_sn","modified_time")
      .withColumn("etl_date",lit("20230911"))
      .withColumn("modified_time",col("modified_time").cast("timestamp"))
    // 合并
    val all_df = ods_df.union(hbase_df)
      .withColumn("dwd_insert_user",lit("user1"))
      .withColumn("dwd_insert_time",lit(currDate).cast("timestamp"))
      .withColumn("dwd_modify_user",lit("user1"))
      .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))
    // 追加到dwd层
    all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_product_browse")

    // 关闭环境
    spark.stop()
  }
}
