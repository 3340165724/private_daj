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
      //  /  CASE1 筛选出负数
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val data_1 = Seq(1, -2, -21, 1, 3, -5)

      sc.parallelize(data_1)
        .filter(_ < 0)
        .foreach(println)

      // TODO 输出
      //   -2
      //   -21
      //   -5

      sc.stop
      spark.stop
   }
}