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
    //  /   CASE 1
    //  /   reduce实现求和
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val seq_1: Seq[Integer] = Seq(1, 2, 3, 4, 5)
    val rdd_1: RDD[Integer] = sc.parallelize(seq_1)
    val result_1 = rdd_1.reduce(_ + _/* 可以是任意符合该类型的运算 */)
    println(s"sum: $result_1")

    // TODO Result
    //  sum: 15



    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 2
    //  /   reduce实现字符串拼接
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val seq_2: Seq[String] = Seq("1", "2", "3", "4", "5")
    val rdd_2: RDD[String] = sc.parallelize(seq_2)
    val result_2 = rdd_2.reduce(_ + _)
    println(s"concat: $result_2")

    // TODO Result
    //  concat执行结果不一致，说明并行化处理RDD元素不是顺序的
    //  concat: 34521
    //  concat: 32145
    //  concat: 14532

    sc.stop
    spark.stop
  }
}
