package _5_mapPartitions

import org.apache.spark.sql.SparkSession

object MapPartitionsScala {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-mapPartitions")
      .getOrCreate()
    val sc = spark.sparkContext

    val rdd_1 = sc.parallelize(1 to 10, 4).map(_ * 2).collect().mkString


    sc.stop()
    spark.stop()
  }
}
