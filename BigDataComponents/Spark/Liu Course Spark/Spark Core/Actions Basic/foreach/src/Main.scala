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

    val seq_1 = 1 to 5

    seq_1.foreach(println)

    // TODO Scala 集合遍历输出
    //  1
    //  2
    //  3
    //  4
    //  5



    sc.parallelize(seq_1)
      .foreach(println)

    // TODO Spark RDD 遍历输出
    //  2
    //  4
    //  5
    //  1
    //  3


    sc.stop
    spark.stop
  }
}
