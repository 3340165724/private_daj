package _8_map_vs_mapPartitions

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object DistinguishScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark_distinguish")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    sc.stop()
    spark.stop()
  }
}
