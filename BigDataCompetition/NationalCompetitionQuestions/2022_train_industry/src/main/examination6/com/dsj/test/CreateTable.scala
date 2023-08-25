package com.dsj.test

import org.apache.spark.sql.SparkSession

object CreateTable {
  def main(args: Array[String]): Unit = {
    // 用spark-shell输入多行命令
    // Scala-shell里直接输入 :paste 命令，粘贴后结束按 ctrl+D
    val spark =SparkSession
      .builder()
      .enableHiveSupport()
      .getOrCreate()

    import spark.sql

    // 创建数据库
    sql("create databases ods6_train_industry")
    // 进入数据库
    sql("use ods6_train_industry")

    // 创建分区表

    //

  }
}
