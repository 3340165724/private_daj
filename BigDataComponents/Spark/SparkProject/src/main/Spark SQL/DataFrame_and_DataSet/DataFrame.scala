package DataFrame_and_DataSet

import org.apache.spark.sql.{DataFrame, SparkSession}

object DataFrame {
  def main(args: Array[String]): Unit = {

    // 创建 Spark SQL 的运行环境
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option","some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._

    // 创建 DataFrame
    val df:DataFrame = spark.read.json("src/main/resources/user.json")

    // 显示数据
    df.show()

    /*
    * TODO DataFrame 用 SQL方式处理数据
    *  必须要创建临时表
    * */

    // 创建临时表
    df.createOrReplaceTempView("user")
    // 访问视图
    spark.sql("select * from user")

    // 全局临时视图的查询
    // spark.sql("SELECT * FROM global_temp.user")

    /*
    * TODO DataFrame 用 DSL方式处理数据
    *  不需要创建临时表
    *   涉及运算时，每个字段都必须使用 $ 或 ‘（单引号）
    * */

    df.select("username","age").show()

    // 年龄 + 1
    df.select($"username", $"age" + 1).show()
    df.select('username, 'age + 1).show()

    // 关闭环境
    spark.stop()
  }
}
