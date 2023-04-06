package _3_flatMap

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object flatMap_scala {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("main-spark-job")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    sc.parallelize(Seq("hello world", "goodbye world"))
      .flatMap(_.split(" "))
      .foreach(println)

    sc.stop
    spark.stop
  }

}
