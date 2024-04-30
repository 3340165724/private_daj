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
      //  /  CASE1 集合元素去重
      //  /  distinct
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val data = Seq(1, 1, 2, 3, 4, 5, 5, 5, 6, 7)

      sc.parallelize(data)
        .distinct
        .foreach(println)

      // TODO
      //   6
      //   2
      //   3
      //   7
      //   1

      sc.stop
      spark.stop

   }
}