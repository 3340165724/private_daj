package Student_Case

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

import java.text.SimpleDateFormat
import java.util.Date

object Student {
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

    /*
    *
    * TODO
    *  CASE1 : 分别统计男、女生人数
    *
    *  */
    val f = rdd_student.map(_._4).filter(_.equals("F")).count()
    val m = rdd_student.map(_._4).filter(_.equals("M")).count()
    println(f)
    println(m)


    /*
    *
    * TODO
    *  CASE2 : 根据当前日期计算每个人的年龄，按"(Tom, $age)"格式输出
    *  三种方式
    *
    * */

    // 得到系统当前时间
    val currentDate = new Date()
    // 格式化
    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    // Date格式转化为String对象
    val data = sdf.format(currentDate)
    // String对象转化为ate格式,并将日期以毫秒为单位显示
    val time = sdf.parse(data).getTime


    /*
    // TODO case1
    val rdd_name_age_ = rdd_student.map(x =>
      (x._2, ((time - sdf.parse(x._3).getTime) / 1000 / 60 / 60 / 24 / 365))
    ).foreach(println)
    */


    // TODO case2
    val rdd_student_ = rdd_student.map {
      x =>
        // String对象转化为ate格式,并将日期以毫秒为单位显示
        val timestamp = sdf.parse(x._3).getTime
        val years = (time - timestamp) / 1000 / 60 / 60 / 24 / 365
        // 返回新的rdd
        (x._1, x._2, x._3, x._4, years)
    }
    val rdd_name_age = rdd_student_.map(o => (o._2, o._5)).foreach(println)


    // TODO case2
    /*
        val rdd_student_ = rdd_student
          .map { o =>
            val birthday = o._3
            // String对象转化为ate格式,并将日期以毫秒为单位显示
            val timestamp = new SimpleDateFormat("yyyy-MM-dd").parse(birthday).getTime
            // 当前系统时间，以毫秒为单位
            val currentTime = System.currentTimeMillis()
            val years = (currentTime - timestamp) / 1000 / 60 / 60 / 24 / 365

            (o._1, o._2, o._3, o._4, years,currentTime)
          }
        val rdd_name_age = rdd_student_.map(o => (o._2, o._5,o._6))
        rdd_name_age.foreach(println)
     */


    /*
    *
    * TODO
    *  CASE3 : 找出男、女生年龄最大和最小的的人，按"(Tom, $age)"格式输出。
    *
    * */
    val rdd_name_age_F = rdd_student_.filter(_._4.equals("F")).map(o => (o._2, o._5))

    sc.stop()
    spark.stop()
  }
}
