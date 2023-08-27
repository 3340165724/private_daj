package com.dsj.stage3

import org.apache.spark.sql.SparkSession

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

object rdd_9_AddAdditionalColumns {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().master("local[*]").getOrCreate()

    val sc = spark.sparkContext

    // 日期格式化
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val sdf_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    // 定义数组
    val weeks = Array("星期天", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")

    // 读取文件
    val rdd = sc.textFile("data/student.txt").filter(!_.startsWith("name"))
    rdd.map(x => {
      val arr = x.split(",")
      val dateStr = arr(1)
      val date = sdf.parse(dateStr)
      val newDate = sdf_.format(date)
      val cal = Calendar.getInstance()
      cal.setTime(date)
      // 获取星期几
      val week = cal.get(Calendar.DAY_OF_WEEK)
      val weekStr = weeks(week - 1) //根据星期值下标对应
      arr(0) + newDate + arr(2) + weekStr
    }).foreach(println)

    // 关闭
    spark.stop()
  }
}
