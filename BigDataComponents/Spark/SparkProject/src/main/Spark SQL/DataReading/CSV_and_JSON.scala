package DataReading

import org.apache.spark.sql.SparkSession

object CSV_and_JSON {
  def main(args: Array[String]): Unit = {
    // 创建 Spark SQL 的运行环境
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._

    /*
    * TODO
    *  JSON 数据格式
    * */
    //  val df_json = spark.read.format("json").load("src/main/resources/user.json")
    val df_json = spark.read.json("src/main/resources/user.json")
    df_json.show()

    /*
    * TODO
    *  csv 数据格式
    * */
    val df_csv = spark.read.format("csv") // CSV格式
      .option("sep", ";") // 数据分割符
      .option("inferSchema", "true") //
      .option("header", "true") // 有表头
      .load("src/main/resources/people.csv")     // 路径

    df_csv.show()

    spark.stop()
  }
}
