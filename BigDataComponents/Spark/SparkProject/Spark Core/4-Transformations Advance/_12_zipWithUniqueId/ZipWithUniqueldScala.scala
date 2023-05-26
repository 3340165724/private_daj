package _12_zipWithUniqueId

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object ZipWithUniqueldScala {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("spark")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /* TODO
      * CASE 1
      *  为分区的每个元素进行编码
      *  分区数   n
      *  分区索引 k
      *  元素索引 i
      *  UniqueId = i * n + k
      *
      */
    val data = Seq(
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

    val rdd_name = sc.parallelize(data, 3)

    /**
     * 查看各元素所在分区
     */
    rdd_name
      .mapPartitionsWithIndex((p, values) =>
        values.map(value => s"partition: $p, value: $value")
      )
      .foreach(println)
    /*
    *   TODO
    *   partition: 0, value: Tom
    *   partition: 0, value: Jerry
    * */

    /**
     * 计算规则
     * UniqueId = i * n + k
     */
    val rdd_name_unique_id: RDD[(String, Long)] = rdd_name.zipWithUniqueId()

    rdd_name_unique_id foreach println
    /* TODO Result
      * (Dave, 2)
      * (Judy, 5)
      * (Max, 8)
      * (Tez, 11)
      * (Vivian, 14)
      * (Matthew, 1)
      * (Nicholas, 4)
      * (Taylor, 7)
      * (Nathan, 10)
      * (Tom, 0)
      * (Jerry, 3)
      * (Marry, 6)
      * (Lily, 9)
      * */
    sc.stop()
    spark.stop()
  }
}
