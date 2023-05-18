package _4_sum

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object SumScala {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-sum")
      .getOrCreate()
    val sc = spark.sparkContext

    // 用于生成一个[0,  1)范围内的随机数
    val num = (Math.random() * 1000).toInt
    val seq: Seq[Double] = for (i <- 1 to num) yield Math.random().toDouble
    val sum: Double = sc.parallelize(seq).sum()
    println(s"sum: $sum")

    sc.stop()
    spark.stop()
  }
}
