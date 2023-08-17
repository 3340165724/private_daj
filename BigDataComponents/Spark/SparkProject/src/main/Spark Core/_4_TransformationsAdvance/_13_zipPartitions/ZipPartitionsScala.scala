package _4_TransformationsAdvance._13_zipPartitions

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object ZipPartitionsScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(10 to 20, 3)



    sc.stop()
    spark.stop()
  }
}
