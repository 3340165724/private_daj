package com.dsj.test

import org.apache.spark.sql.{SaveMode,SparkSession}
import org.apache.spark.sql.functions._


object Test {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("Test")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 连接MySQL
    val mysql_reader = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://192.168.66.130:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230828"

    /*
    * todo CASE2  以create_time为增量的操作
    *   customer_balance_log、customer_point_log表使用create_time作增量字段
    * */
    // 要增量抽取的表
    val tables_2 = Array("customer_balance_log", "customer_point_log")
    tables_2.foreach(table => {
      // 从hive中
      val max_create_time = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from 2023_ods1_ds_db01.${table}").first().getString(0)
      // 如果hive对应的表取出最大时间，则MySQL查询根据最大时间作为增量条件
      var sql = s"select * from ${table}"
      if (!max_create_time.equals("")) {
        sql += " where create_time > max_create_time"

        // 从MySQL中拿出数据,并添加一个字段符合hive的格式
        val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
        df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_ods1_ds_db01.${table}")
      }
    })
  }
}
