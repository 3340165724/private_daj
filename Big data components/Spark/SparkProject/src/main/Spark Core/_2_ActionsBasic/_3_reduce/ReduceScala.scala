package _2_ActionsBasic._3_reduce

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ReduceScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-reduce_scala")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val data: Seq[Integer] = Seq(1, 2, 3, 4, 5)
    val rdd = sc.parallelize(data).reduce(_ + _)
    println(s"sum: $rdd")

    sc.stop
    spark.stop
  }
}
