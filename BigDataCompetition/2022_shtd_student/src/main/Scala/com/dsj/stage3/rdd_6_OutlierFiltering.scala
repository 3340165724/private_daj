package com.dsj.stage3

import org.apache.spark.sql.SparkSession

object rdd_6_OutlierFiltering {
  def main(args: Array[String]): Unit = {
    // 创建spark对象
    val spark = SparkSession.builder().master("local[*]").getOrCreate()

    val sc = spark.sparkContext

    // TODO df方式：读取文件，并处理异常值
    val df = spark.read.csv("data/student.txt")
    df.show()
    // 创建累加器
    val countName = sc.longAccumulator("countName")
    val countSex = sc.longAccumulator("countSex")
    val df2 = df.filter({ x =>
      val name = x.getString(0)
      val sex = x.getString(2)
      if (name == null) {
        countName.add(1)
        false
      }
      else if (name.equals("name")) {
        false
      }
      else if (sex.equals("妖")) {
        countSex.add(1)
        false
      }
      else {
        true
      }
    })
    df2.count()
    println("过来的姓名为空的是：" + countName.value)
    println("过来的性别为空的是：" + countSex.value)

    // 文件写入


    // todo 使用RDD的方式过滤异常值
    /*//读取文件
    val rdd = sc.textFile("data/student.txt")
    // 去除表头
    val rdd2 = rdd.filter(!_.startsWith("name"))
    // 创建累加器
    val countName_ = sc.longAccumulator("countName_")
    val countSex_ = sc.longAccumulator("countSex_")
    rdd2.filter(x => {
      // 将一行数据分隔成数组
      val arr = x.split(",")
      val name = arr(0)
      val sex = arr(2)
      if (name.equals("")) {
        countName_.add(1)
        false
      }
      else if (sex.equals("妖")) {
        countSex_.add(1)
        false
      }
      else {
        true
      }
    })*/
    // 关闭资源
    spark.stop()
  }
}
