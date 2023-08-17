package com.dsj

import org.apache.spark.sql.SparkSession

import java.util.Properties

object _2_FullyExtractStaticPartitions {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("FullyExtractStaticPartitions")
      .enableHiveSupport() //开启对hive的支持
      .getOrCreate()

    // todo 从MySQL中拿数据
    val url = "jdbc:mysql://192.168.66.130:3306/shtd_student?useSSl=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")

    // 查询数据得到DataFrame
    val df = spark.read.jdbc(url, "tb_class", prop)

    // 创建临时视图
    df.createOrReplaceTempView("class")

    // 进入hive的否个数据库
    // spark.sql("use ods1")

    // todo 静态分区中添加数据
    spark.sql("insert into ods1.tb_class partition(etldata='20230623') select * from class where cid > 15")



    // TODO 循环在多个表中插入数据
    /*
    val tables = Array("tb_class","tb_score","tb_student")
    for(tableName <- tables){
      // 从MySQL中拿数据
      val url = "jdbc:mysql://192.168.66.130:3306/shtd_student?useSSl=false&characterEncoding=UTF-8"
      val prop = new Properties()
      prop.setProperty("user", "root")
      prop.setProperty("password", "123456")
      // 查询数据得到DataFrame
      val df = spark.read.jdbc(url, tableName, prop)
      // 创建临时视图
      df.createOrReplaceTempView("table")
      // todo 静态分区中添加数据
      spark.sql(s"insert into ods1.${tableName} partition(etldata='20230623') select * from table where cid > 15")
    }
*/
    // 关闭环境
    spark.stop()
  }
}










