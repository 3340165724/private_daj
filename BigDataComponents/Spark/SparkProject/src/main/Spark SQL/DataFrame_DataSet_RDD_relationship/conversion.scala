package DataFrame_DataSet_RDD_relationship

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object conversion {
  def main(args: Array[String]): Unit = {
    // 创建 Spark SQL 的运行环境
    val spark = SparkSession.builder() //创建SparkSession
      .master("local[*]")
      .appName("spark-example")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._

    // 创建 SparkContext
    val sc = spark.sparkContext

    // 创建 RDD
    val rdd = sc.makeRDD(List((1, "zhangsan", 30), (2, "lisi", 28), (3, "wangwu", 20)))

    /*
    * TODO
    *  CASE1  RDD ====> DataFrame
    *        数据 ====> 把结构添加上
    * */
    val df = rdd.toDF("id", "name", "age")


    /*
     * TODO
     *  CASE2  DataFrame ===> RDD
     * */
    val df_rdd = df.rdd

    /*
    * TODO
    *  CASE3  DataFrame ===> DataSet
    * */
    case class User(id: Int, name: String, age: Int)
    val ds = df.as[User]

    /*
    * TODO
    *  CASE4  DataSet ===> DataFrame
    * */
    val ds_df = ds.toDF()

    /*
    * TODO
    *  CASE5 RDD ====> DataSet
    * */
    val rdd_ds = ds.toDF()
    rdd.map {
      case (id, name, age) =>
        User(id, name, age)
    }.toDS()


    /*
    * TODO
    *  CASE6  DataSet ===> RDD
    * */
    val ds_rdd = ds.rdd

    // 关闭
    spark.stop()
  }
}
