package _2_foreach

import org.apache.spark.sql.SparkSession


object foreach_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder().master("local[1]")
      .appName("spark-foreach_scala")
      .getOrCreate()
    val sc = spark.sparkContext

    val list = 1 to 10
    sc.parallelize(list).foreach(println)

    sc.stop
    spark.stop
  }
}
