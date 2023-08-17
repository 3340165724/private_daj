package _4_TransformationsAdvance._6_partitions

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object PartitionsScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[3]")
      .appName("spark-partitions")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /*
    * TODO
    *  对比预设分区数与手动设置分区数的情况
    * */
    println(s"defaultParallelism/默认的并行度: ${sc.defaultParallelism}")
   /*
   * TODO
   *  defaultParallelism / 默认的并行度: 3
   * */

    val partitions_1 = sc.parallelize(1 to 100).partitions
    println(s"partition's count of rdd_1: ${partitions_1.length}")
    /*
    * TODO
    *  partition's count of rdd_1: 3
    * */

    partitions_1.foreach(partition =>
      println(s"partition of rdd_1: ${partition.index}")
    )
    /* TODO 未设置分区数，默认与 defaultParallelism 值 3 相等
    *  partition of rdd_1: 0
    *  partition of rdd_1: 1
    *  partition of rdd_1: 2
     */

    val partitions_2 = sc.parallelize(1 to 100, 5).partitions
    println(s"partition's count of rdd_2: ${partitions_2.length}")
    /*
    * TODO
    *  partition's count of rdd_2: 5
    * */


    sc.stop()
    spark.stop()
  }
}