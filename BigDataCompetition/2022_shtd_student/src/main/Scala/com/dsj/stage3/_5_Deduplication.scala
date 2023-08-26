package com.dsj.stage3

import org.apache.spark.sql.SparkSession

object _5_Deduplication {
  def main(args: Array[String]): Unit = {
    // 创建spark对象
    val spark = SparkSession.builder().master("local[*]").getOrCreate()
    val sc = spark.sparkContext

    //todo rdd方式： 读取数据文件
    /*val rddFile = sc.textFile("data/student.txt")
      // rddFile.foreach(println)
    // 去掉表头
    val rdd_ = rddFile.filter(!_.startsWith("name"))
    val rdd = rdd_.distinct()
    println("去重的行数是：" + (rddFile.count() - rdd.count()))*/

    // todo DataFrame方式：读取文件
    val df = spark.read.csv("data/student.txt")
    df.show()
    val df2 = df.distinct()
    println("去重的行数是：" + (df.count() - df2.count()))

    //关闭
    spark.stop()
  }
}
