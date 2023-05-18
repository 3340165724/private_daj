package _7_Iterator_while

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

object IteraorWhile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark_while")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(1 to 10, 3)
    rdd.mapPartitions {
      iterator =>
        val list = new ListBuffer[String]()

        while (iterator.hasNext)
          list.+=(s"value: ${iterator.next()}")

        list.toIterator
    }
      .foreach(println)
    /*
    * TODO
    *  输出的顺序可能不同
    * */

    sc.stop()
    spark.stop()
  }
}
