import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

/**
 * @author Matthew Liu 老刘
 * @see 教程全网ID：@老刘大数据 | 源码下载公众号：@架构师老刘 | 课程源码辅导陪练微信: architect-liu-cn
 * @since 2021
 * @mail matthewliu.cn@outlook.com
 */
object Main {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("main-spark-job")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 1
    //  /   统计以下列表中姓名重复的(k -> v)对，
    //  /   打印有重复的人名和次数。
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val map_1 = Map(
      1 -> "Tom",
      2 -> "Jerry",
      3 -> "Marry",
      4 -> "Lily",
      5 -> "Matthew",
      6 -> "Nicholas",
      7 -> "Lily",
      8 -> "Lily",
      9 -> "Nicholas",
      10 -> "Lily",
      11 -> "Marry",
      12 -> "Lily"
    )

    val result = sc
      .parallelize(map_1.toSeq)
      .values
      .map((_, 1))
      .reduceByKey(_ + _)
      .filter(_._2 > 1)
      .collect

    result.foreach(println)
    // TODO
    //  (Marry,2)
    //  (Lily,5)
    //  (Nicholas,2)

    sc.stop
    spark.stop
  }
}
