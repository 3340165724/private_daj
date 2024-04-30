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
    //  /   判断不同情形下RDD是否为空
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val seq_1: Seq[String] = Seq("1")
    val seq_2: Seq[String] = Seq()
    val seq_3: Seq[String] = null
    val seq_4: Seq[String] = Seq(null)

    val rdd_1 = sc.parallelize(seq_1)
    val rdd_2 = sc.parallelize(seq_2)
    val rdd_3 = sc.parallelize(seq_3)
    val rdd_4 = sc.parallelize(seq_4)

    println(rdd_1.isEmpty())
    // TODO
    //  false

    println(rdd_2.isEmpty())
    // TODO
    //  ture

    println(rdd_3.isEmpty())
    // TODO
    //  java.lang.NullPointerException

    println(rdd_4.isEmpty())
    // TODO
    //  false

    sc.stop
    spark.stop
  }
}
