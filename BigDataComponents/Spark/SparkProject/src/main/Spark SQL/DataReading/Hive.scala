package DataReading

import org.apache.spark.sql.SparkSession

object Hive {
  def main(args: Array[String]): Unit = {
    // 创建 Spark SQL 的运行环境
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._


    
    spark.stop()
  }
}
