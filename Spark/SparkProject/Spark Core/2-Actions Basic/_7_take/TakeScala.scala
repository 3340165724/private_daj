package _7_take

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object TakeScala {
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

    val result_1: Array[(Int, String)] = sc
      .parallelize(map.toSeq)
      .take(5)

    result_1.foreach(println)


    /**
     * 操作数值类型的RDD
     * */
    val seq: Seq[Double] = for (i <- 1 to 100) yield Math.random()
    val result_2: Array[Double] = sc.parallelize(seq).take(5)

    result_2.foreach(println)

    sc.stop
    spark.stop
  }

}
