package _16_subtract

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object Subtract {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val  rdd1 = sc.parallelize(1 to 10)
    val rdd2 = sc.parallelize(8 to 20)

    println(rdd1.subtract(rdd2).collect().mkString(", "))
    println(rdd2 .subtract(rdd1).collect().mkString(", "))

    sc.stop()
    spark.stop()
  }
}
