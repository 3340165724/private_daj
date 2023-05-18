package _3_reduce

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object Quiz {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-reduce")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val list_1 = Seq(1 to 5 toString)
    val list_2 = Seq(6 to 10 toString)
    val list_3 = Seq(11 to 15 toString)
    val list_4 = Seq(list_1, list_2, list_3)

    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /  使用reduce算子将RDD内部元素合并，输出目标格式
    //  /  List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15)
    //  /
    //  / ------------------------------------------------------------------------------------------------------------


    val data = list_4.reduce(_ ++ _)
    println(data)


    sc.stop
    spark.stop
  }
}
