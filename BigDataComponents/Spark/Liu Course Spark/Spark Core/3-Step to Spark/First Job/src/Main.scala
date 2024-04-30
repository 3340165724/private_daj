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

      // TODO
      //  / ------------------------------------------------------------------------------------------------------------
      //  /
      //  /                         Step 1 - 准备 SparkSession SparkContext 对象
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val spark: SparkSession = SparkSession
        .builder()
        .master("local[1]")
        .appName("main-spark-job")
        .getOrCreate()
      val sc: SparkContext = spark.sparkContext

      // TODO
      //  / ------------------------------------------------------------------------------------------------------------
      //  /
      //  /                         Step 2 - 执行计算过程 Transformations Basic & Action
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      val seq_1: Seq[Int] = 1 to 100

      val rdd_seq_1: RDD[Int] = sc.parallelize(seq_1)

      // 菜鸟写法
      val rdd_seq_2: RDD[Int] = rdd_seq_1.map(o => o * -1)

      rdd_seq_2.foreach(o => println(o))

      // 一般写法
      rdd_seq_1.map(o => o * -1).foreach(println)

      // 进阶写法
      rdd_seq_1.map(_ * -1).foreach(println)

      // 装X写法
      rdd_seq_1 map(_ * -1) foreach println

      // TODO
      //  / ------------------------------------------------------------------------------------------------------------
      //  /
      //  /                         Step 3 - 关闭Spark，释放资源
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      sc.stop
      spark.stop
   }
}