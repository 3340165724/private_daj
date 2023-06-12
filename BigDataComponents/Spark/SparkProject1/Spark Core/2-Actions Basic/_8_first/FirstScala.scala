package _8_first

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object FirstScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("main-spark-job")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext


    /**
     * 操作Map类型的RDD
     * */

    val map = Map(
      1 -> "Tom",
      2 -> "Jerry",
      3 -> "Marry",
      4 -> "Lily",
      5 -> "Matthew",
      6 -> "Nicholas",
      7 -> "Lily",
      8 -> "Lily",
      9 -> "Nicholas",
      10 -> "Lily",
      11 -> "Marry",
      12 -> "Lily"
    )

    val result_1: (Int, String) = sc.parallelize(map.toSeq).first

    println(result_1)
    //  (5,Matthew)



    /**
     * 操作数值类型的RDD
     * */

    val seq: Seq[Double] = for (i <- 1 to 100) yield Math.random()
    val result_2: Double = sc.parallelize(seq).first
    println(result_2)
    //  0.0983772639261613

    sc.stop
    spark.stop
  }
}
