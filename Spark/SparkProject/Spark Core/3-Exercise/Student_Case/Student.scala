package Student_Case

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

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

    //TODO
    // 分别统计男、女生人数


    sc.stop()
    spark.stop()
  }
}
