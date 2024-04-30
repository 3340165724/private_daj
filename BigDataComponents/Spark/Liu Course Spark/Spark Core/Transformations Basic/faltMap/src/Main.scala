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
      //  /  flatMap 用于按空格分词的场景
      //  /  输入类型：String
      //  /  输出类型：String
      //  /  *注意，输出类型摊平了一层中间容器Array[String]
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val line_1 = "I love China! I love China!"
      val line_2 = ""
      val line_3 = "Taiwan is a part of China!"

      sc.parallelize(Seq(line_1, line_2, line_3))
        .flatMap(_.split(" "))
        .foreach(println)

      // TODO 输出
      //   Taiwan
      //   is
      //   a
      //   part
      //   of
      //   China!
      //   I
      //   love
      //   China!
      //   I
      //   love
      //   China!

      sc.stop
      spark.stop

   }
}