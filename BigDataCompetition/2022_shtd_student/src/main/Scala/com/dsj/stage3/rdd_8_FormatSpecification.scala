package com.dsj.stage3

import org.apache.spark.sql.SparkSession

import java.text.SimpleDateFormat


object rdd_8_FormatSpecification {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().master("local[*]").getOrCreate()
    val sc = spark.sparkContext

    // 日期格式化
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val sdf_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    // 读取数据,并去除数据的表头
    val rddFile = sc.textFile("data/student.txt").filter(!_.startsWith("name"))
    rddFile.map(x => {
      val arr = x.split(",")
      val dateStr = arr(1)
      val date = sdf.parse(dateStr)
      val newDate = sdf_.format(date)
      if (arr.length != 3) {
        arr(0) + "," + newDate + "," + arr(2) + "," + arr(3)
      }
    }).foreach(println)
    spark.stop()
  }
}
