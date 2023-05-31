package _18_join

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object JoinScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd1 = sc.parallelize(Seq(("zhangsan", 2), ("lisi", 1), ("tom", 5), ("zhangsan", 6)))
    val rdd2 = sc.parallelize(Seq(("wulan", 3), ("lisi", 5), ("sunfeng", 2), ("tom", 6)))

    println(rdd1.join(rdd2).collect().mkString(", "))

    sc.stop()
    spark.stop()
  }
}
