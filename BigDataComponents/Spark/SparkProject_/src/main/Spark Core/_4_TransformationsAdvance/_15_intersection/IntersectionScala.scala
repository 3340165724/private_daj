package _4_TransformationsAdvance._15_intersection

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object IntersectionScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-intersection")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

   val  rdd1 = sc.parallelize(1 to 10)
   val rdd2 = sc.parallelize(8 to 20)
    rdd1.intersection(rdd2).foreach(println)
    rdd2.intersection(rdd1).foreach(println)

    println("-------------------------------")
    println(rdd1.intersection(rdd2).collect.mkString(", "))
    println(rdd2.intersection(rdd1).collect.mkString(", "))

    sc.stop()
    spark.stop()
  }
}
