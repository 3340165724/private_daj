package _4_TransformationsAdvance._9_mapPartitionsWithIndex

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ListBuffer

object QuizFor {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-for")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(10 to 20,3)
    rdd.mapPartitionsWithIndex{
      (index, element)=>
        // 创建可变列表
        val list = new ListBuffer[String]()
        for(o <- element){
          list.+=(s"[value=$o, partition_id=$index]")
        }
        list.toIterator
    }.foreach(println)

    sc.stop()
    spark.stop()
  }
}
