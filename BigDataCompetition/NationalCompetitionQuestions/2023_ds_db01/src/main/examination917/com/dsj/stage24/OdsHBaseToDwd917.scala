package com.dsj.stage24

import org.apache.spark.sql.SparkSession

object OdsHBaseToDwd917 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition","true")
      .config("hive.exec.dynamic.partition.mode","nonstrict")
      .config("hive.exec.max.dynamic.partitions",2000)
      .config("spark.sql.parser.quotedRegexColumnNames","true")
      .getOrCreate()



    // 关闭资源
    spark.stop()
  }
}
