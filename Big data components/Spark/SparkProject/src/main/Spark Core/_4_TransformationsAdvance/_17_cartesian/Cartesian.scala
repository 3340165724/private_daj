package _4_TransformationsAdvance._17_cartesian

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object Cartesian {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext


    val  rdd1 = sc.parallelize(1 to 3)
    val rdd2 = sc.parallelize(8 to 10)

    println(rdd1.cartesian(rdd2).collect().mkString(","))
    println(rdd2.cartesian(rdd1).collect().mkString(","))

    sc.stop()
    spark.stop()
  }
}
