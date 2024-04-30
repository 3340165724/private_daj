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
      //  /  CASE1 使用同样的数据集对比 map / flatMap 的输出结果
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val line_1 = "I love China! I love China!"
      val line_2 = ""
      val line_3 = "Taiwan is a part of China!"

      val rdd = sc.parallelize(Seq(line_1, line_2, line_3))

      rdd.flatMap(_.split(" ")).foreach(println)

      // TODO 输出，摊平后的结果，内部元素
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

      rdd.map(_.split(" ")).foreach(println)

      // TODO 输出，未摊平的内层容器是一个集合，需要再次拆封内层容器才能得到元素
      //   [Ljava.lang.String;@2f2e18e3
      //   [Ljava.lang.String;@37ef47f7
      //   [Ljava.lang.String;@55e918ad

      rdd.map(_.split(" ")).foreach(_.foreach(println))

      // TODO 输出，两次拆封的结果与flatMap相同
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