package com.dsj._1_student

import org.apache.spark.sql.SparkSession

import java.util.Properties

object IncrementalExtraction {
  def main(args: Array[String]): Unit = {
    // 用 spark-shell 输入多行命令
    // scla-shell里直接输入  :paste 命令，黏贴后结束按ctrl+D

    val spark = SparkSession.builder()
      .appName("HiveTableAndQueryMySQL")
      .master("local")
      .getOrCreate()

    val sc = spark.sparkContext

    // 隐式转换
    import spark.implicits._

    // 利用SparkSession对象从MySQL中拿出需要的数据
    val url = "jdbc:mysql://172.20.37.230:3306/shtd_student?useSSL=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")

    // 查询数据得到DataFrame
    val df = spark.read.jdbc(url, "tb_class", prop)
    // val df = spark.read.jdbc(url, "(select * from tb_class where school='云南职业技术学院') as tb", prop)

    df.show()

    //

    spark.stop()
  }
}
