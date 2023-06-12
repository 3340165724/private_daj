package _2_ActionsBasic._5_count

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object CountScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-count")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val num = (Math.random() * 100).toInt
    // 迭代num次，把随机生成的数逐个放到集合中
    val seq: Seq[Int] = for (i <- 1 to num) yield (Math.random() * 100).toInt
    println(num)
    println(seq)
    val count: Long = sc.parallelize(seq).count

    println(s"count: $count")

    sc.stop
    spark.stop
  }
}
