package DataReading

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

import java.util.Properties

object MySql {
  def main(args: Array[String]): Unit = {
    // 创建 Spark SQL 的运行环境
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._

    // todo 方式 1: 加载数据
    val df = spark.read.format("jdbc")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("url", "jdbc:mysql://localhost:3306/db_student")
      .option("user", "root")
      .option("password", "123456")
      .option("dbtable", "student")
      .load()

    // todo 方式 2:通用的 load 方法读取 参数另一种形式
//    spark.read.format("jdbc")
//      .options(Map("url" -> "jdbc:mysql://localhost:3306/db_student?user=root&password=123456",
//        "dbtable" -> "user", "driver" -> "com.mysql.jdbc.Driver"))
//      .load().show

    // todo 方式 3:使用 jdbc 方法读取
    val props: Properties = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "123456")
    props.setProperty("driver", "com.mysql.jdbc.Driver")
    val frame = spark.read.jdbc("jdbc:mysql://localhost:3306/db_student", "user", props)	//直接传入表名
    frame.show()


    // 保存数据
//    df.write.format("jdbc")
//      .option("driver", "com.mysql.cj.jdbc.Driver")
//      .option("url", "jdbc:mysql://localhost:3306/db_student")
//      .option("user", "root")
//      .option("password", "123456")
//      .option("dbtable", "student1")
//      .mode(SaveMode.Append)
//      .save()


    spark.stop()
  }
}
