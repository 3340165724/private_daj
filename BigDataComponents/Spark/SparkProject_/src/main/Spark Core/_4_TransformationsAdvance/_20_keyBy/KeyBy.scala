package _4_TransformationsAdvance._20_keyBy

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object KeyBy {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .master("local[1]")
      .config("spark.sql.warehouse.dir","hdfs://192.168.66.130:9000/user/hive/warehouse")
      .enableHiveSupport()
      .getOrCreate()



    spark.stop()
  }
}
