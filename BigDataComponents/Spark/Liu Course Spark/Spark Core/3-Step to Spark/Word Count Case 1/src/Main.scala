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

      val rdd: RDD[String] = sc.textFile("./resources/source-word-count.txt")

      // TODO 输入文本
      //  I always love CHINA!
      //  I Always Love CHINA!
      //  TaiWan is a part of China!
      //  TaiWan is a part of CHINA!
      //  TaiWan is a Part Of china!

      // TODO
      //  / ------------------------------------------------------------------------------------------------------------
      //  /
      //  /                         计算WordCount的链式写法
      //  /
      //  / ------------------------------------------------------------------------------------------------------------

      rdd
        .flatMap(_.split(" "))
        .map(_.replace("!", ""))
        .map((_, 1))
        .reduceByKey(_ + _)
        .map(o => (o._2, o._1))
        .foreach(println)

      // TODO 输出结果
      //  (3,is)
      //  (3,CHINA)
      //  (1,love)
      //  (1,China)
      //  (1,Love)
      //  (3,a)
      //  (3,TaiWan)
      //  (2,I)
      //  (2,of)
      //  (1,Always)
      //  (1,china)
      //  (1,always)
      //  (1,Part)
      //  (1,Of)

      // TODO WordCount 关键步骤的拆解，学完算子之后继续讲解

      sc.stop
      spark.stop
   }
}