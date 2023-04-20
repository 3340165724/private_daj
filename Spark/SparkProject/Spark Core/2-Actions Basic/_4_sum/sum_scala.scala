package _4_sum

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object sum_scala {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-sum")
      .getOrCreate()
    val sc = spark.sparkContext

   val  randomData = Math.random()
    println(randomData)

    sc.stop()
    spark.stop()
  }
}
