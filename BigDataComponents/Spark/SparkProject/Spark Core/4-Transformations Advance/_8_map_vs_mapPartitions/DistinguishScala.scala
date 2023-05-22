package _8_map_vs_mapPartitions

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object DistinguishScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark_distinguish")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /*
    * TODO
    *  CASE 1 一个RDD包含若干个正数，使每个元素变为负数；
    *  对比map和mapPartitions在处理同一批数据性能的情况
    * */
    val sample_1 = 1 to 1000000

    val rdd_1 = sc.parallelize(sample_1, 3)

    def now: Long = System.currentTimeMillis()

    // TODO ------------------------------------------------------------------------------------------------------------
    //  map 性能测试
    val now_1 = now

    rdd_1.map(_ * -1).collect()

    println(s"duration: ${now - now_1}")
    println(s"now=$now")
    println(s"now_1=$now_1")
    /*
    * TODO
    *  该统计受限于环境配置、计算性能等影响，结果有不确定性。
    * */


    /* TODO
     *   mapPartitions 性能测试
    */
    val now_2 = now
    rdd_1.mapPartitions(iterator => iterator.map(_ * -1)).collect()
    println(s"duration: ${now - now_2}")
    println(s"now=$now")
    println(s"now_2=$now_2")
    /*
    * TODO
    *   该统计受限于环境配置、计算性能等影响，结果有不确定性。
    * */


    sc.stop()
    spark.stop()
  }
}
