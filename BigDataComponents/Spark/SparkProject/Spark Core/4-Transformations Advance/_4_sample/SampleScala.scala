package _4_sample

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object SampleScala {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-sample")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /*
    * TODO
    *  集合元素固定取样,
    *   sample 指定seed
    * */
    println(
      sc.range(1, 10000)
        .sample(false, 0.001, 20)
        .collect
        .mkString(",")
    )
    /*
    *TODO
    * 输出结果，15个数，不重复，指定seed每次取样结果稳定
    * 517,852,3056,3359,3524,3671,4633,5254,7452,7965,8132,8516,8673,8732,8776
    *
    * */


    /*
    *
    * TODO
    *  集合元素随机取样,放回
    *   sample 不指定seed
    *
    * */
    println(
      sc.range(1, 10000)
        .sample(true, 0.002)
        .collect
        .mkString(",")
    )
    /*
    *
    * TODO
    *  输出结果，接近20个数，可重复，未指定seed每次取样结果不同
    *   647,1588,1844,3460,4371,4482,4570,5847,6626,7302,7538,7660,7759,7923,9731,9961
    *   65,921,1291,1782,1882,2229,2269,2466,2525,3061,3493,3936,4097,5179,5425,5636,6039,6105,6356,7239,8025,8975,9252,9441
    * */


    /*
    *TODO
    * 不放回
    * */
    println(
      sc.range(1, 10000)
        .sample(false, 0.002)
        .collect
        .mkString(",")
    )
/*
*
* TODO
*  输出结果，接近20个数，不重复，未指定seed每次取样结果不同
*   574,919,1192,1663,2064,2329,2391,2564,2767,2876,4325,4493,4857,4936,5041,5096,5188,5264,5341,5939,6110,6113,6176,6378,7227,7363,8068,8696,8752,8896
*   558,666,1005,1862,2472,3593,4207,5069,5233,5241,5284,5457,5684,5750,6294,6424,6781,6879,7358,8089,8223,8307,9037,9150
*
* */

    sc.stop
    spark.stop
  }
}
