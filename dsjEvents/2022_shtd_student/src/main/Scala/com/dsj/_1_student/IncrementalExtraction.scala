package com.dsj._1_student

import org.apache.spark.sql.SparkSession

object IncrementalExtraction {
  def main(args: Array[String]): Unit = {
    // 用 spark-shell 输入多行命令
    // scla-shell里直接输入  :paste 命令，黏贴后结束按ctrl+D

    val spark = SparkSession.builder()
      .master("local[1]")
      .enableHiveSupport()
      .getOrCreate()

    val sc = spark.sparkContext

    import spark.sql
    import spark.implicits._

    // 创建数据库
    sql("create database ods1_student")

    // 进入数据库
    sql("use ods1_student")

    // 创建分区表

    // etldate string：静态分区列
    sql(
      """
        |create table tb_class(cid int, cname string, specialty string, school string)
        | partitioned by(etldate string)
        | row format delimited
        | fields terminated by ','
        | lines terminated by '\n' ;
        |""".stripMargin
    )

    // reg_month string：动态分区列
    sql(
      """
        |create table tb_student(sid int, sname string, sex int, birthday date, phone string, address string, scid int, reg_date date)
        | partitioned by(reg_month string)
        | row format delimited
        | fields terminated by ','
        | lines terminated by '\n'
        |""".stripMargin
    )


    sql(
      """
        | create table  tb_score(id int, sid int, course string, score int, test_time timestamp)
        | partitioned by(test_month int)
        | row format delimited
        | fields terminated by ','
        | lines terminated by '\n'
        |""".stripMargin
    )


    spark.stop()
  }
}
