import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession

object map_scala {
  def main(args: Array[String]): Unit = {

    val spark: SparkSession = SparkSession
      .builder()
      .master("local[1]")
      .appName("main-spark-job")
      .getOrCreate()
    val sc: SparkContext = spark.sparkContext
  }
}
