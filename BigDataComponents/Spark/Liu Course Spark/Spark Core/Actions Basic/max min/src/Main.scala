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
    //  /   统计随机数RDD的最大、最小值
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val num = (Math.random() * 1000).toInt
    val seq: Seq[Double] = for (i <- 1 to num) yield Math.random()

    val max: Double = sc.parallelize(seq).max()

    println(s"max: $max")
    // TODO
    //  max: 0.9957399702224957

    val min: Double = sc.parallelize(seq).min()

    println(s"min: $min")
    // TODO
    //  min: 0.0019231448020478048

    sc.stop
    spark.stop
  }
}
