package _4_TransformationsAdvance._20_keyBy

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object KeyBy {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .config("spark.sql.warehouse.dir", "hdfs://192.168.66.130:9000/user/hive/warehouse")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    /*
    * TODO CASE 1
    *  拆分单项元素 RDD 为 RDD[Tuple[Int, String]]
    *  指定一个 key 。
    * */
    val list_student = Seq(
      "1_Tom",
      "2_Jerry",
      "3_Marry",
      "4_Lily",
      "5_Matthew",
      "6_Nicholas",
      "7_Taylor",
      "8_Nathan",
      "9_Dave",
      "11_Judy",
      "12_Max",
      "13_Tez",
      "14_Vivian"
    )

    val rdd_student = sc.parallelize(list_student)
    rdd_student.keyBy(_.split("_")(0).toInt).foreach(println)
    /*
    * (1,1_Tom)
      (2,2_Jerry)
      (3,3_Marry)
      (4,4_Lily)
      (5,5_Matthew)
      (6,6_Nicholas)
      (7,7_Taylor)
      (8,8_Nathan)
      (9,9_Dave)
      (11,11_Judy)
      (12,12_Max)
      (13,13_Tez)
      (14,14_Vivian)
      */

    sc.stop()
    spark.stop()
  }
}
