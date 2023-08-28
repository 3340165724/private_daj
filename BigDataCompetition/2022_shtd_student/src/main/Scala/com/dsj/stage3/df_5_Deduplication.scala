package com.dsj.stage3

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object df_5_Deduplication {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().master("local[*]").getOrCreate()

    //
    import spark.implicits._
    // 读取数据
    val df = spark.read.option("header", true).csv("data/student.txt")
    // 创建临时表
    df.createOrReplaceTempView("student")
    // 算出平均值
    val age_avg = spark.sql("select avg(age) from student age is not null").collect()(0).getDouble(0)

    // 填充姓名为null的匿名
    val df_add_name = df.withColumn("name", when(col("name").isNull, lit("匿名")).otherwise(col("name")))
      .withColumn("birthday",col("birthday").cast("timestamp"))
      .withColumn("age",when(col("age").isNull,age_avg).otherwise(col("age")))

    //关闭
    spark.stop()
  }
}
