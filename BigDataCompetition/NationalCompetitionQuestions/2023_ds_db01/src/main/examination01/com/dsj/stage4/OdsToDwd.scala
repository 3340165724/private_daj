package com.dsj.stage4

import org.apache.spark.sql.SparkSession

object OdsToDwd {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .getOrCreate()

    // 定义数组
   val tables = Array("customer_balance_log","customer_login_log","customer_point_log")
    tables.foreach(table =>{

    })


    // 关闭
    spark.stop()
  }
}
