package com.dsj.stage24

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object IncrementalExtraction02 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 连接MySQL
    val mysql_reader = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.20.37.78:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230912"
    /*
    * 1、	抽取ds_db01库中customer_inf的增量数据进入Hive的ods库中表customer_inf。
    *   根据ods.customer_inf表中modified_time作为增量字段，只将新增的数据抽入，字段名称、类型不变，
    *   同时添加静态最新，分区字段为etl_date，类型为String，且值为当前日期的前一天日期（分区字段格式为yyyyMMdd）。
    *   使用hive cli执行show partitions ods.customer_inf命令；
    * 2、	抽取ds_db01库中product_info的增量数据进入Hive的ods库中表product_info。
    *   根据ods.product_info表中modified_time作为增量字段，只将新增的数据抽入，字段名称、类型不变，
    *   同时添加静态分区，分区字段为etl_date，类型为String，且值为当前日期的前一天日期（分区字段格式为yyyyMMdd）。
    *   使用hive cli执行show partitions ods.product_info命令；
    *
    * 3、	抽取ds_db01库中order_master的增量数据进入Hive的ods库中表order_master，
    *   根据ods.order_master表中modified_time作为增量字段，只将新增的数据抽入，字段名称、类型不变，
    *   同时添加静态分区，分区字段为etl_date，类型为String，且值为当前日期的前一天日期（分区字段格式为yyyyMMdd）。
    *   使用hive cli执行show partitions ods.order_master命令；
    *
    * 4、	抽取ds_db01库中order_detail的增量数据进入Hive的ods库中表order_detail，
    *   根据ods.order_detail表中modified_time作为增量字段，只将新增的数据抽入，字段名称、类型不变，
    *   同时添加静态分区，分区字段为etl_date，类型为String，且值为当前比赛日的前一天日期（分区字段格式为yyyyMMdd）。
    *   使用hive cli执行show partitions ods.order_detail命令。
    * */
    // 要增量抽取的表
    val tables = Array("customer_inf", "product_info", "order_master", "order_detail")
    tables.foreach(table => {
      // 从hive的ods层表中拿出modified_time最大的值的数据
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from ods.${table}").first().getString(0)
      // 如果最大时间不为空，则作为增量抽取的条件
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where modified_time > '${ods_df}' "
      }
      // 从MySQL中拿出modified_time 大于hive的ods中的值,同时添加动态分区
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })
    // 关闭资源
    spark.stop()
  }
}
