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
      //  /  CASE1 一个RDD包含若干个正数，使每个元素变为负数
      //  /  map 用于数理计算的场景，输入输出类型相同
      //  /  输入类型：Int
      //  /  输出类型：Int
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val sample_1 = 1 to 10

      sc.parallelize(sample_1)
        .map(_ * -1)
        .foreach(println)

      // TODO 输出
      //   -1
      //   -2
      //   -3
      //   -4
      //   -5
      //   -6
      //   -7
      //   -8
      //   -9
      //   -10



      // TODO
      //  / ------------------------------------------------------------------------------------------------------------
      //  /
      //  /  CASE2 一个RDD包含若干字符串，每一个字符串标记1次出现
      //  /  map 用于打标记的场景，输入输出类型不同
      //  /  输入类型：String
      //  /  输出类型：Tuple[(String, Int)]
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val sample_2 = Seq("C", "H", "I", "N", "A", "!")

      sc.parallelize(sample_2)
        .map((_, 1))
        .foreach(println)

      // TODO 输出
      //   (C,1)
      //   (H,1)
      //   (I,1)
      //   (N,1)
      //   (A,1)
      //   (!,1)

      sc.stop
      spark.stop
   }
}