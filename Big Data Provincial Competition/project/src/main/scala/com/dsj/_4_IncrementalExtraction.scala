package com.dsj

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import java.util.Properties

object _4_IncrementalExtraction {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("DynamicPartitioning")
      .enableHiveSupport()
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._

    /*
    *  todo 增量抽取
    *   思路：从hive和MySQL中分别查出数据，去重，再把没有重复的数据插入hive中
    * */

    // todo 查询出hive表中目前最大的时间
    //    val df_hive = spark.sql("select max(reg_date) from tb_student").collect()(0).getDate(0).toString
    val df_hive = spark.sql("select sid from tb_student").rdd.map(x => {
      val id = x.getInt(0)
      id
    }).collect()

    // todo 从MySQL中拿数据
    val url = "jdbc:mysql://192.168.66.130:3306/shtd_student?useSSL=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")
    // 查询数据得到DataFrame
    val df_mysql = spark.read.jdbc(url, "tb_student", prop)

    /*
    * todo 过滤，去重
    *  contains()：是否包含，判断数组中是否包含某个值
    * */
    val df = df_mysql.filter(x => {
      val id = x.getInt(0)
      if (df_hive.contains(id)) false else true
    })

    val df1 = df.withColumn("reg_month", regexp_replace(col("reg_date"), pattern = "-", replacement = ""))
    // 创建临时视图
    df1.createOrReplaceTempView("student")

    // 插入数据
    spark.sql("insert into ods1.tb_student partition(reg_month) select * from student")

    spark.stop()
  }
}












