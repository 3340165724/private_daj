package _4_TransformationsAdvance._7_Iterator_for_yield

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object IteratorForYield {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark-for-yield")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val rdd = sc.parallelize(1 to 10, 3)

    rdd.mapPartitions {o =>
      for(i <- o)
        yield (s"value:$i")
    }.foreach(println)

    sc.stop()
    spark.stop()
  }
}
