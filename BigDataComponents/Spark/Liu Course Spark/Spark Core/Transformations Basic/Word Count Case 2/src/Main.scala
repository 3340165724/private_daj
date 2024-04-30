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
    //  /  CASE 1
    //  /  拆解链式写法的中间步骤，请补充以下rdd_[2~n]，打印每一步的中间输出结果，分析整个计算过程
    //  /  请补充以下输出的中间结果并完善程序
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val rdd_2: RDD[String] = rdd.flatMap(_.split(" "))

    rdd_2.foreach(println)

    // TODO 中间结果 rdd_2
    //  TaiWan
    //  is
    //  a
    //  part
    //  of
    //  CHINA!
    //    TaiWan
    //  I
    //  always
    //  ...

    val rdd_3: RDD[String] = rdd_2.map(_.replace("!", "").trim)

    rdd_3.foreach(println)

    // TODO 中间结果 rdd_3
    //  I
    //  always
    //  love
    //  CHINA
    //  I
    //  Always
    //  Love
    //  CHINA
    //  TaiWan
    //  is
    //  a

    // TODO 请自主完成以下关键步骤的输入输出结果

    val rdd_4: RDD[(String, Int)] = rdd_3.map((_, 1))

    // TODO 中间结果 rdd_4

    val rdd_5 = ???

    // TODO 中间结果 rdd_5

    // TODO 目标输出结果，实现单词词频统计
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

    sc.stop
    spark.stop
  }
}
