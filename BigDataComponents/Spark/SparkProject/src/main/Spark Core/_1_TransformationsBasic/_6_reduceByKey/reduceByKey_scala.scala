package _1_TransformationsBasic._6_reduceByKey

import org.apache.spark.sql.SparkSession

object reduceByKey_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-reduceByKey")
      .getOrCreate()

    val sc = spark.sparkContext

    val data= List(("a", 1), ("a", 1), ("b", 2), ("b", 3))

    sc.parallelize(data).reduceByKey(_ + _).foreach(println)
    // 输出
    // (a,2)
    // (b,5)

    sc.stop()
    spark.stop()
  }
}
