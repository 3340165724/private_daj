package _7_Iterator_while

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object IteraorWhile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-map")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    sc.stop()
    spark.stop()
  }
}
