package _1_TransformationsBasic._4_map_vs_flatMap

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object map_vs_flatMap_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-map_vs_flatMap")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val line1 = "Return a new distributed dataset formed by passing each element of the source through a function func"
    val line2 = "Similar to map, but each input item can be mapped to 0 or more output items"
    val line3 = "so func should return a Seq rather than a single item"

    val rdd = sc.parallelize(Seq(line1, line2, line3))
    println(Seq(line1, line2, line3))
    println("-------------------1---------------")
    // 输出，摊平后的结果，内部元素
    rdd.flatMap(_.split(" ")).foreach(println)
    // Return
    // a
    // new
    // distributed
    // dataset
    // formed
    // by


    println("----------------2------------------")
    // 输出，未摊平的内层容器是一个集合，需要再次拆封内层容器才能得到元素
    rdd.map(_.split(" ")).foreach(println)
    // [Ljava.lang.String;@24e9fbe2
    // [Ljava.lang.String;@84c2834
    // [Ljava.lang.String;@1cf31f9

    println("----------------3------------------")
    // 输出，两次拆封的结果与flatMap相同
    rdd.map(_.split(" ")).foreach(_.foreach(println))
    // Return
    // a
    // new
    // distributed
    // dataset
    // formed
    // by

    sc.stop
    spark.stop
  }
}
