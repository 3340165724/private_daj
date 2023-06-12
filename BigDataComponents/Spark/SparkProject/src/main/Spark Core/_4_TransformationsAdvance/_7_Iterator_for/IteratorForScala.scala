package _4_TransformationsAdvance._7_Iterator_for

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer


object  IteratorForScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-for")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    // numSlices : 指定分区数
    /*
    * TODO
    *
    * */
    val rdd_1 = sc.parallelize(1 to 10, 3)

    rdd_1.mapPartitions { iterator =>
      // 创建可变列表
      val list = new ListBuffer[String]()
      for (o <- iterator)
        list.+=(s"value: $o")

      list.toIterator
    }.foreach(println)


    spark.stop
    sc.stop
  }
}
