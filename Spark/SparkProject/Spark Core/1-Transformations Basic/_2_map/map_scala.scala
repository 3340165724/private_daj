package _2_map

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object map_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[*]")
      .appName("spark-map_scala")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val sample_1 = 1 to 10
    sc.parallelize(List(1,2,3,5,6,8))
      .map(_ * -1)
      .foreach(println)

    val sample_2 = Seq(1, 3, 5, 8)
    sc.parallelize(sample_2)
      .map(_ * 3)
      .foreach(println)

    sample_2.map(_ * 2)
      .foreach(println)

    sc.stop
    spark.stop
  }
}
