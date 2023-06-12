package _2_ActionsBasic._9_max_min

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object MaxMinScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("main-spark-job")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /**
     * 统计随机数RDD的最大、最小值
     * */
    val num = (Math.random() * 1000).toInt
    val seq: Seq[Double] = for (i <- 1 to num) yield Math.random()

    val max: Double = sc.parallelize(seq).max()

    println(s"max: $max")
    //  max: 0.9957399702224957

    val min: Double = sc.parallelize(seq).min()

    println(s"min: $min")
    //  min: 0.0019231448020478048

    sc.stop
    spark.stop
  }
}
