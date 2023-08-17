package _4_TransformationsAdvance._7_Iterator_map

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object IteratorMapScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-map")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(1 to 10, 3)
    rdd.mapPartitions(_.map(x => (s"value:$x"))).foreach(println)

    sc.stop()
    spark.stop()
  }
}
