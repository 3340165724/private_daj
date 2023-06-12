package _2_ActionsBasic._10_isEmpty

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object isEmptyScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("main-spark-job")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /**
     * 判断不同情形下RDD是否为空
     * */

    val seq_1: Seq[String] = Seq("1")
    val seq_2: Seq[String] = Seq()
    val seq_3: Seq[String] = null
    val seq_4: Seq[String] = Seq(null)

    val rdd_1 = sc.parallelize(seq_1)
    val rdd_2 = sc.parallelize(seq_2)
    val rdd_3 = sc.parallelize(seq_3)
    val rdd_4 = sc.parallelize(seq_4)

    println("rdd_1" + rdd_1.isEmpty())
    //  false

    println("rdd_2" + rdd_2.isEmpty())
    //  ture

    println("rdd_3" + rdd_3.isEmpty())
    //  java.lang.NullPointerException

    println("rdd_4" + rdd_4.isEmpty())
    //  false

    sc.stop
    spark.stop
  }
}
