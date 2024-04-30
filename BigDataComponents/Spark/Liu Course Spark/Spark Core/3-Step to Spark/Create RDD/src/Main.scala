import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
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
    //  /  创建RDD的几种方式，注意[]中的泛型
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val seq: Seq[Integer] = Seq(1, 2, 3, 4, 5)
    val rdd_1: RDD[Integer] = sc.parallelize(seq)

    val rdd_2: RDD[Long] = sc.range(1, 100)

    val rdd_3: RDD[String] = sc.textFile("./resources/source-word-count.txt")


    // TODO
    //  以上创建RDD的操作并不会立即被执行，后面课程讲解

    sc.stop
    spark.stop
  }
}