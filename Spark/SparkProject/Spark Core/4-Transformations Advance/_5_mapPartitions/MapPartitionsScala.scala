package _5_mapPartitions

import org.apache.spark.sql.SparkSession

object MapPartitionsScala {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-mapPartitions")
      .getOrCreate()
    val sc = spark.sparkContext

    /*
    * TODO
    *  将数据1-10分为4个分区
    * */
    val rdd_1 = sc.parallelize(1 to 10, 4)

    /*
    * TODO
    *  map指定输出格式
    *  mkString：将数组中所有元素转换成字符串
    * */
    val rdd_pair_1 = rdd_1.map(o => (o, o * 2))
    println(s"rdd_pair_1: ${rdd_pair_1.collect().mkString}")


    /*
    * TODO
    *  第三个元素是每个分区的元素个数，说明数据是在分区内部被处理
    * */
    val rdd_pair_2 = rdd_1.mapPartitions { elements =>
      val list = elements.toList
      val size = list.size

      list.map(o => (o, o * 2, size)).toIterator
    }
    println(s"rdd_pair_2: ${rdd_pair_2.collect().mkString}")

    sc.stop()
    spark.stop()
  }
}
