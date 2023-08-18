package _1_TransformationsBasic._2_map

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession


object map_scala {
  def main(args: Array[String]): Unit = {
    // 创建一个sparksession
    val spark: SparkSession = SparkSession.builder()
      .master("local[1]")
      .appName("spark-map_scala")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext

    val sample_1 = 1 to 10
    sc.parallelize(sample_1)
      .map(_ * -1)
      .foreach(println)

    val sample_2 = Seq(1, 3, 5, 8)
    sc.parallelize(sample_2)
      .map(_ * 3)
      .foreach(println)

    sample_2.map(_ * 2)
      .foreach(println)


    val list1 = ("1","zhangsan","F","20")
    val list2 = ("2","lisi","F","20")

    sc.parallelize(Seq(list1,list2)).map(x => (x._2,x._3)).foreach(println)


    sc.stop
    spark.stop
  }
}
