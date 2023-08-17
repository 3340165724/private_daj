package UDF_and_UDAF

import org.apache.spark.sql.{DataFrame, SparkSession}

object UDF {
  def main(args: Array[String]): Unit = {
    // 创建 Spark SQL 的运行环境
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._


    // 创建 DataFrame
    val df:DataFrame = spark.read.json("src/main/resources/user.json")

    // 显示数据
    df.show()

    /*
    * TODO
    *  CASE1 在所有用户名前面加个前缀，例如：Name：张三
    * */
    df.createOrReplaceTempView("user")

    // 注册 UDF
    spark.udf.register("prefixName", (x:String) => "Name：" + x)

    // 应用 UDF 函数
    spark.sql("select prefixName(username) , age from user").show()

    // 关闭环境
    spark.stop()
  }
}
