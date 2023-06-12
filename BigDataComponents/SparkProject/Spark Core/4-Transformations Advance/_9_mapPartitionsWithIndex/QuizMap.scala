package _9_mapPartitionsWithIndex

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object QuizMap {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(20 to 30, 3)

    rdd.mapPartitionsWithIndex(
      (index,element) =>
        element.map(x => s"[value=$x, partition_id=$index]")
    ).foreach(println)

    sc.stop()
    spark.stop()
  }
}
