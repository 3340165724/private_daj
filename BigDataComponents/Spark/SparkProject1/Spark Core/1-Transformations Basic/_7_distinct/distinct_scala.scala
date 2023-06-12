package _7_distinct

import org.apache.spark.sql.SparkSession

object distinct_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-distinct")
      .getOrCreate()

    val sc = spark.sparkContext

    val data = Seq(1, 1, 2, 3, 4, 5, 5, 5, 6, 7)

    sc.parallelize(data).distinct().foreach(println)
    //  输出
    //   6
    //   2
    //   3
    //   7
    //   1
    sc.stop()
    spark.stop()

  }
}
