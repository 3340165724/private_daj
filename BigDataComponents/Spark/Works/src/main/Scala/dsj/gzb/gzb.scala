package dsj.gzb

import org.apache.spark.sql.SparkSession

object gzb {
  def main(args: Array[String]): Unit = {
    //创建SparkSession
    val spark = SparkSession.builder()
      .master("local")
      .appName("spark-example")
      .getOrCreate()

    // 读取Excel文件并转换为DataFrame
    val df = spark.read
      .format("com.crealytics.spark.excel")
      .option("header", "true")
      .option("inferSchema", "true")
      .load("D:\\Warehouse\\private_daj\\BigDataComponents\\Spark\\Works\\src\\main\\resources\\gzb.xlsx")

    // 打印DataFrame的结构// 打印DataFrame的结构
    df.printSchema

    // 展示前几行数据
    df.show()

    // 将DataFrame注册为一个临时视图
    df.createOrReplaceTempView("people")

    val result = spark.sql("SELECT name, COUNT(*) AS count FROM people GROUP BY name")
    result.show(100)

    // 关闭资源
    spark.close()
  }
}
