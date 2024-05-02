package dsj.gzb

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, datediff, to_date}

object Introduce {

  // 介绍费
  def main(args: Array[String]): Unit = {
    // 创建SparkSession对象
    val spark = SparkSession.builder()
      .master("local")
      .appName("spark-example")
      .getOrCreate()

    //读取Excel文件"介绍费表"
    val df = spark.read
      .format("com.crealytics.spark.excel")
      .option("header", "true")
      .option("inferSchema", "true") // 用于告诉 Spark 从数据源中推断列的数据类型，并将其作为 DataFrame 中的模式（schema）应用的选项
      .load("D:\\Warehouse\\private_daj\\BigDataComponents\\Spark\\Works\\src\\main\\resources\\introduce.xlsx")

    // 打印DataFrame的结构
    df.printSchema()


    // 将数据的类型转换为日期类型
    val dfWithDates = df.withColumn("入职时间", to_date(col("入职时间")))
      .withColumn("离职时间", to_date(col("离职时间")))

    // 添加一个新列来计算日期差异
    val dfWithDays = dfWithDates.withColumn("days_difference", datediff(col("离职时间"), col("入职时间"))).show(50)



    // 关闭资源
    spark.close()

  }
}
