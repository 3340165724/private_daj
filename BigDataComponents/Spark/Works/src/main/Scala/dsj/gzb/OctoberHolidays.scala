package dsj.gzb

import org.apache.spark.sql.SparkSession

object OctoberHolidays {

  // 23年10月休假补助第二次
  def main(args: Array[String]): Unit = {
    // 创建SparkSession对象
    val spark = SparkSession.builder()
      .master("local")
      .appName("spark-example")
      .getOrCreate()

    // 读取Excel文件"2023休假补助10月11月出勤"中的"10月考勤"工作表并转换为DataFrame
    val october = spark.read
      .format("com.crealytics.spark.excel")
      .option("header", true) // 是否要表头
      .option("dataAddress", "0!A1:B43") // 第几个 sheet 页 ! 第几行第几列，例如：0!A1:B1
      .load("file:/D:/Warehouse/private_daj/BigDataComponents/Spark/Works/src/main/resources/furlough.xlsx") // 指定 Excel 文件路径

    // 读取Excel文件"2023休假补助10月11月出勤"中的"3月出勤"工作表并转换为DataFrame
    val march = spark.read
      .format("com.crealytics.spark.excel")
      .option("header", true) // 是否要表头
      .option("dataAddress", "2!A1:B164") // 第几个 sheet 页 ! 第几行第几列，例如：0!A1:B1
      .load("file:/D:/Warehouse/private_daj/BigDataComponents/Spark/Works/src/main/resources/furlough.xlsx") // 指定 Excel 文件路径

    // 读取Excel文件"2023休假补助10月11月出勤"中的"10休补"工作表并转换为DataFrame
    val furlough = spark.read
      .format("com.crealytics.spark.excel")
      .option("header", true) // 是否要表头
      .option("dataAddress", "3!A1:A46") // 第几个 sheet 页 ! 第几行第几列，例如：0!A1:B1
      .load("file:/D:/Warehouse/private_daj/BigDataComponents/Spark/Works/src/main/resources/furlough.xlsx") // 指定 Excel 文件路径

    // 查看表结构
    //    october.printSchema()
    //    march.printSchema()
    //    furlough.printSchema()

    // 选取表中的某一列
    val name1 = october.select("name1")
    val name2 = march.select("name2")
    val name = furlough.select("name")
    //    name1.show()
    //    name2.show()
    //    name.show()

    // 23年10月还在上班的人
    val result1 = name.join(name1, name("name") contains name1("name1"), "inner")
    result1.show()

    // 24年3月份依旧在岗
    val result2 = name.join(name2, name("name") contains name2("name2"), "inner")
    result2.show(100)

    // 关闭资源
    spark.close()
  }
}
