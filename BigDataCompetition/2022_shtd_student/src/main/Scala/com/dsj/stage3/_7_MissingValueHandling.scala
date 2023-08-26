package com.dsj.stage3

import org.apache.spark.sql.SparkSession

object _7_MissingValueHandling {
  def main(args: Array[String]): Unit = {
    // 创建spark对象
    val spark = SparkSession.builder().master("local[*]").getOrCreate()

    val sc = spark.sparkContext

    // todo 使用rdd的方式：处理缺失值
    // 读取数据,并去除数据的表头
    val rddFile = sc.textFile("data/student.txt").filter(!_.startsWith("name"))
    // 先计算出年龄的均值，然后在map的时候填充进去
    val ave_age = rddFile.filter(x => {
      val arr = x.split(",")
      if (arr.length == 3) {
        false
      }
      else {
        true
      }
    }).map(x => {
      val arr = x.split(",")
      val sex = arr(2) //性别
      val age = arr(3).toInt
      (sex, age)
    }).groupByKey().map(x => {
      val sum = x._2.sum.toDouble
      val age = sum / x._2.size
      (x._1,age) // 返回性别和年龄
//    }).collect()(0) //collect()(0)，收集整个DataFrame的内容，并从中取出第一个元素
    }).collectAsMap()
    println("男的平均年龄是：" + ave_age.get("男").get)
    println("女的平均年龄是：" + ave_age.get("女").get)

    rddFile.map(x => {
      val arr = x.split(",")
      val name = arr(0)
      val sex = arr(2) // 性别
      var newName = name
      var newAge = 0.0
      if (name.equals("")) {
        newName = "匿名"
      }
      if (arr.length == 3) {
        newAge = ave_age.get(sex).get
      }
      else {
        val age = arr(3)
        newAge = age.toInt
      }
      newName + "," + arr(1) + "," + arr(2) + "," + newAge
    }).foreach(println)
    spark.stop()
  }
}
