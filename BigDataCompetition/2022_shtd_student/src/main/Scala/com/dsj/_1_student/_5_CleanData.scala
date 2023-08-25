package com.dsj._1_student

import org.apache.spark.sql.SparkSession

object _5_CleanData {
  def main(args: Array[String]): Unit = {
    // 创建spark对象
    val spark = SparkSession.builder().enableHiveSupport().getOrCreate()

    //
  }
}
