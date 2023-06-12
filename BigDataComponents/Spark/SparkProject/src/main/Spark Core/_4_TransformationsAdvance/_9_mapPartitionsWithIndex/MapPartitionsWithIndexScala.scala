package _4_TransformationsAdvance._9_mapPartitionsWithIndex

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object MapPartitionsWithIndexScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(20 to 30, 3)

    /*
    * TODO
    *  case1
    *   按 [value=$value, partition_id=$partition_id] 格式
    * */
    rdd.mapPartitionsWithIndex {
      (index, element) =>
        element.map(x => s"[value=$x, partition_id=$index]")
    }.foreach(println)


    /*
    * TODO
    *  case2
    *   的带第二个分区的数据，按上面的格式输出
    * */
    rdd.mapPartitionsWithIndex(
      (index, iter) => {
        if (index == 1) {
          iter
        }else{
          Nil.iterator
        }
      }
    ).foreach(println)
    sc.stop()
    spark.stop()
  }
}
