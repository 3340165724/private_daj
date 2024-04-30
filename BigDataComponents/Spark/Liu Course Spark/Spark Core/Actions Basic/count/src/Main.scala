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
    //  /   统计一个随机RDD的元素个数。
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val num = (Math.random() * 1000).toInt
    val seq: Seq[Int] = for (i <- 1 to num) yield Math.random().toInt
    val count: Long = sc.parallelize(seq).count

    println(s"count: $count")
    // TODO 随机生成的RDD元素个数不同
    //  count: 565
    //  count: 989
    //  count: 880

    sc.stop
    spark.stop
  }
}
