package _14_union

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object UnionScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext


     /*
     *
     * TODO
     *  CASE 1
     *  两个RDD[Int] union
     * */
    val data_1 = 1 to 5
    val data_2 = 6 to 10

    val rdd_1: RDD[Int] = sc.parallelize(data_1)
    val rdd_2: RDD[Int] = sc.parallelize(data_2)

    println(rdd_1.union(rdd_2).collect.mkString(", "))
    // TODO Result
    //  1, 2, 3, 4, 5, 6, 7, 8, 9, 10

    println(rdd_2.union(rdd_1).collect.mkString(", "))
    // TODO Result
    //  6, 7, 8, 9, 10, 1, 2, 3, 4, 5



     /*
     *
     * TODO
     *  CASE 2
     *  两个RDD[Tuple[String, Int]] union
     * */
    val pair_1 = ("A", 1)
    val pair_2 = ("B", 1)
    val pair_3 = ("C", 1)
    val pair_4 = ("B", 1)
    val pair_5 = ("D", 1)
    val pair_6 = ("D", 1)
    val pair_7 = ("C", 1)
    val pair_8 = ("D", 1)

    val pairs_1 = Seq(
      pair_1,
      pair_2,
      pair_3,
      pair_4
    )

    val pairs_2 = Seq(
      pair_5,
      pair_6,
      pair_7,
      pair_8
    )

    val rdd_3 = sc.parallelize(pairs_1)
    val rdd_4 = sc.parallelize(pairs_2)

    println(rdd_4.union(rdd_3).distinct.collect.mkString(", "))
    // TODO Result
    //  (D,1), (A,1), (B,1), (C,1)


    sc.stop()
    spark.stop()
  }
}
