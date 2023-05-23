package _10_zip

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ZipScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-zip")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /*
    * TODO
    *  CASE1
    *   两个 RDD 做 zip
    * */
    val rdd_name = sc.parallelize(
      Seq(
        "Tom",
        "Jerry",
        "Marry",
        "Lily",
        "Matthew",
        "Nicholas",
        "Taylor",
        "Nathan",
        "Dave",
        "Judy",
        "Max",
        "Tez",
        "Vivian"
      )
    )

    val rdd_index = sc.range(1, 14)
    val rdd_name_index:RDD[(String, Long)] = rdd_name.zip(rdd_index)
    rdd_name_index foreach println
    /*
    * TODO 输出类型：
    *  (Tom,1)
    *  (Jerry,2)
    * */


    val rdd_index_name: RDD[(Long, String)] = rdd_index.zip(rdd_name)
    rdd_index_name foreach println
    /*
    * TODO 输出类型：
    *  (1,Tom)
    *  (2,Jerry)
    * */

    spark.stop
    sc.stop
  }
}
