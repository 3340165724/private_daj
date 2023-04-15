package _6_reduceByKey

import org.apache.spark.sql.SparkSession

object reduceByKey_scala {
  val spark: SparkSession = SparkSession
    .builder()
    .master("local[1]")
    .appName("spark-reduceByKey")
    .getOrCreate()

  val sc = spark.sparkContext
  

}
