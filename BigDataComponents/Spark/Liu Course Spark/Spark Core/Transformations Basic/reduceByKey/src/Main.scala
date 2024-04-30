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
      //  /  CASE1
      //  /  reduceByKey 用于Key类型相同的累"加"场景
      //  /  输入类型：(String, Int)
      //  /  输出类型：(String, Int)
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val pair_1 = ("A", 1)
      val pair_2 = ("B", 1)
      val pair_3 = ("C", 1)
      val pair_4 = ("B", 1)
      val pair_5 = ("D", 1)
      val pair_6 = ("D", 1)
      val pair_7 = ("C", 1)
      val pair_8 = ("D", 1)
      val pairs = Seq(
         pair_1, pair_2, pair_3, pair_4,
         pair_5, pair_6, pair_7, pair_8
      )

      sc.parallelize(pairs)
        .reduceByKey(_ + _)
        .foreach(println)

      // TODO 输出
      //   (B,2)
      //   (C,2)
      //   (A,1)
      //   (D,3)

      sc.stop
      spark.stop
   }
}