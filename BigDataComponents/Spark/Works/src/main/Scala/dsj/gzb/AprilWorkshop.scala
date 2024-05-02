package dsj.gzb

import org.apache.spark.sql.SparkSession

object AprilWorkshop {

  // 四月份车间小考请
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
      .option("inferSchema", "true") // 用于告诉 Spark 从数据源中推断列的数据类型，并将其作为 DataFrame 中的模式（schema）应用的选项
      .load("D:\\Warehouse\\private_daj\\BigDataComponents\\Spark\\Works\\src\\main\\resources\\gzb.xlsx")

    // 打印DataFrame的结构
    df.printSchema

    // 展示前几行数据
    //    df.show()

    // 将DataFrame注册为一个临时视图
    df.createOrReplaceTempView("people")

    val result = spark.sql("SELECT name, SUM(jiaban) , SUM(kaoqin) FROM people GROUP BY name")
    //    result.show(100)

    // 将数据帧写入Excel文件
    result.write
      .format("com.crealytics.spark.excel")
      .option("header", "true")
      .save("D:\\Warehouse\\private_daj\\BigDataComponents\\Spark\\Works\\src\\main\\resources\\out\\gzb1.xlsx")

    // 关闭资源
    spark.close()
  }
}
