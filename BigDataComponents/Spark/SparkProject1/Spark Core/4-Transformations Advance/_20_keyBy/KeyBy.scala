package _20_keyBy

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object KeyBy {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext


    sc.stop()
    spark.stop()
  }
}
