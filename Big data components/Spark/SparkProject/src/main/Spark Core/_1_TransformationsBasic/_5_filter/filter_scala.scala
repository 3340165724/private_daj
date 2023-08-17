package _1_TransformationsBasic._5_filter

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object filter_scala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-filter_scala")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val data_1 = Seq(1, -2, -21, 1, 3, -5)
    sc.parallelize(data_1)
      .filter(_ < 0).foreach(println)
    //   -2
    //   -21
    //   -5

    /*
    * TODO
    *   CASE2
    *     筛选出基数
    * */

    val data_2 = 1 to 100
    val ints = sc.parallelize(data_2).map(_ * -1).filter(x => x % 2 == -1).foreach(println)

    sc.stop
    spark.stop
  }
}
