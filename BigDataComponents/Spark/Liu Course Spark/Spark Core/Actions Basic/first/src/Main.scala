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
    //  /   操作Map类型的RDD
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val map = Map(
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

    val result_1: (Int, String) = sc
      .parallelize(map.toSeq)
      .first

    println(result_1)
    // TODO
    //  (5,Matthew)



    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 2
    //  /   操作数值类型的RDD
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val seq: Seq[Double] = for (i <- 1 to 100) yield Math.random()
    val result_2: Double = sc.parallelize(seq).first

    println(result_2)
    // TODO
    //  0.0983772639261613

    sc.stop
    spark.stop
  }
}
