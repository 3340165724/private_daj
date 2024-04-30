import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.text.SimpleDateFormat

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

    val list_student = Seq(
      (1, "Tom", "1992-01-21", "M"),
      (2, "Jerry", "1980-11-09", "M"),
      (3, "Marry", "1995-02-04", "F"),
      (4, "Lily", "1999-10-01", "F"),
      (5, "Matthew", "1991-12-05", "M"),
      (6, "Nicholas", "1993-10-08", "M"),
      (7, "Taylor", "1994-01-14", "M"),
      (8, "Nathan", "1997-10-11", "M"),
      (9, "Dave", "1992-01-14", "M"),
      (10, "Judy", "1992-01-21", "F"),
      (11, "Max", "1989-01-26", "M"),
      (12, "Tez", "1987-07-09", "M"),
      (13, "Vivian", "1999-10-09", "F")
    )

    val rdd_student = sc.parallelize(list_student)

    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 1
    //  /   分别统计男、女生人数
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val rdd_student_gender = rdd_student.map(_._4).cache()

    val rdd_count_M = rdd_student_gender.filter(_.equals("M")).count()

    println(s"rdd_count_M: $rdd_count_M")
    // TODO
    //  rdd_count_M: 9

    val rdd_count_F = /* TODO 补充代码统计女生人数 */
    println(s"rdd_count_F: $rdd_count_F")



    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 2
    //  /   根据当前日期计算每个人的年龄，按"(Tom, $age)"格式输出。
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val rdd_student_ = rdd_student
      .map { o =>
        val birthday = o._3
        val timestamp = new SimpleDateFormat("yyyy-MM-dd").parse(birthday).getTime
        val timestamp_ = System.currentTimeMillis()
        val years = (timestamp_ - timestamp) / 1000 / 60 / 60 / 24 / 365

        (o._1, o._2, o._3, o._4, years)
      }
    val rdd_name_age = rdd_student_.map(o => (o._2, o._5))

    rdd_name_age.foreach(println)
    // TODO
    //  (Taylor,29)
    //  (Judy,31)
    //  (Tom,31)
    //  (Lily,23)
    //  (Nathan,25)
    //  (Max,34)
    //  (Jerry,42)
    //  (Dave,31)
    //  (Matthew,31)
    //  (Marry,28)
    //  (Nicholas,29)
    //  (Tez,35)
    //  (Vivian,23)



    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 3
    //  /   找出男、女生年龄最大和最小的的人，按"(Tom, $age)"格式输出。
    //  /
    //  /   tips
    //  /   RDD处理原则：
    //  /   - 先做列降维map()处理，后续运算只带必须列
    //  /   - 避免多列数据全局排序，提升性能
    //  /   - 能filter，尽量先filter
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val rdd_name_age_F = rdd_student_.filter(_._4.equals("F")).map(o => (o._2, o._5))
    val age_max_F = rdd_name_age_F.values.max()
    val age_min_F = rdd_name_age_F.values.min()
    val rdd_age_max_F = rdd_name_age_F.filter(_._2 == age_max_F).collect()
    val rdd_age_min_F = rdd_name_age_F.filter(_._2 == age_min_F).collect()

    rdd_age_max_F foreach println
    // TODO
    //  (Judy,31)

    rdd_age_min_F foreach println
    // TODO
    //  (Lily,23)
    //  (Vivian,23)

    // TODO 参照以上代码，自主实现男生部分



    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 4
    //  /   计算男、女生平均年龄$age_average。
    //  /
    //  /   tips
    //  /   注意 sum_age_F 与 sum_age_F_ 的等价写法对比
    //  /
    //  / ------------------------------------------------------------------------------------------------------------

    val count_F = rdd_name_age_F.count()
    val sum_age_F = rdd_name_age_F.map(_._2).sum()
    val sum_age_F_ = rdd_name_age_F.map(_._2).reduce(_ + _)
    val age_average_F = sum_age_F / count_F

    println(s"age_average_F: $age_average_F")
    // TODO
    //  age_average_F: 26.25

    // TODO 参照以上代码，自主实现男生部分



    // TODO
    //  / ------------------------------------------------------------------------------------------------------------
    //  /
    //  /   CASE 5
    //  /   分别计算男、女生年龄总和，
    //  /      按
    //  /      "
    //  /      age_sum_M=$age_sum_M
    //  /      age_sum_F=$age_sum_F
    //  /      "
    //  /      格式输出。
    //  /
    //  /   tips
    //  /   使用reduce或reduceByKey算子
    //  /
    //  / ------------------------------------------------------------------------------------------------------------



    sc.stop
    spark.stop
  }
}
