import org.apache.spark.sql.SparkSession

object sql {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option","some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._


    spark.stop()
  }
}
