package _5_filter

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object filter_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-filter_scala")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val data_1 = Seq(1, -2, -21, 1, 3, -5)
    sc.parallelize(data_1)
      .filter(_ < 0).foreach(println)
    //   -2
    //   -21
    //   -5

    sc.stop
    spark.stop
  }
}
