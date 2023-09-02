package com.dsj.stage4

import org.apache.spark.sql.{SaveMode, SparkSession}

// dim_开头的表叫维度表（数据会发生更新）
// Fact_开头的表叫事实表（数据不会被修改，只会新增）

object OdsToDwd {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置分区的模式是非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .getOrCreate()

    // 定义数组
    val tables_1 = Array("customer_balance_log", "customer_login_log", "customer_point_log")
    tables_1.foreach(table => {
      // 从ods层拿出分区数据
      val df = spark.sql(s"select * from 2023_ods1_ds_db01.${table} where etl_date='20230828'")
      // 将数据写入dwd对应表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_dwd1_ds_db01.fact_${table}")

    })


    // 关闭
    spark.stop()
  }
}
