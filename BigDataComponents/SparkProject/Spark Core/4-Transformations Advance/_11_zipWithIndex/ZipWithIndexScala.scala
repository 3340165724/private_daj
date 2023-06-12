package _11_zipWithIndex

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object ZipWithIndexScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-zip")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd1 = sc.parallelize(Seq("zhansan", "lisi", "wangwu", "sunfeng"))
    val rdd2 = sc.parallelize(Seq(20, 30, 50, 60))

    rdd1.zip(rdd2).zipWithIndex().foreach(println)

    sc.stop()
    spark.stop()
  }
}
