package com.dsj

import org.apache.spark.sql.SparkSession
import java.util.Properties

object _1_HiveTableAndQueryMySQL {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("HiveTableAndQueryMySQL")
      .master("local")
      .getOrCreate()

    // todo 从MySQL中拿数据
    val url = "jdbc:mysql://172.20.37.230:3306/shtd_student?useSSL=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")

    // 查询数据得到DataFrame
    val df = spark.read.jdbc(url, "tb_class", prop)
    // val df = spark.read.jdbc(url, "(select * from tb_class where school='云南职业技术学院') as tb", prop)


    // 在hive中创建 hive表
    spark.sql(
      """
        |create table tb_class(cid int, cname string, specialty string, school string)
        |partitioned by(etldata string)
        |row format delimited
        |fields terminated by ','
        |lines terminated by '\n';
        |""".stripMargin
    )

    spark.sql(
      """
        |create table tb_score(id int, sid int, course string,score int,test_time timestamp)
        |partitioned by(test_month int)
        |row format delimited
        |fields terminated by ','
        |lines terminated by '\n';
        |""".stripMargin
    )

    spark.sql(
      """
        |create table tb_student(sid int,sname string,sex int,birthday date,phone string,address string, scid int, reg_date date)
        |partitioned by(reg_month string)
        |row format delimited
        |fields terminated by ','
        |lines terminated by '\n';
        |""".stripMargin
    )

    df.show()
    spark.stop()
  }
}

























