package _19_cogroup

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object Cogroup {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd1 = sc.parallelize(Seq(("zhangsan",15),
      ("lisi",20),
      ("wangwu",28),
      ("sunfeng",19),
      ("lisi",23),
      ("Lily",22),
      ("zhangsan",50)))
    val rdd2 = sc.parallelize(Seq(("wulan", 3), ("lisi", 5), ("sunfeng", 2), ("tom", 6), ("zhangsan", 12)))

    rdd1.cogroup(rdd2).foreach(println)

    println("-----------------------------------------")
    println(rdd1.cogroup(rdd2).collect().mkString(","))

    sc.stop()
    spark.stop()
  }
}
