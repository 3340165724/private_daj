package _2_map

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object Quiz {
    def main(args: Array[String]): Unit = {

      val spark: SparkSession = SparkSession
        .builder()
        .master("local[1]")
        .appName("main-spark-job")
        .getOrCreate()
      val sc: SparkContext = spark.sparkContext

      val data = Seq("1", "2", "3", "4")
      // TODO 将data中的元素转化为整形数字
      data.foreach(print)
      data.map(_.toInt).foreach(print)

      sc.stop
      spark.stop
  }

}
