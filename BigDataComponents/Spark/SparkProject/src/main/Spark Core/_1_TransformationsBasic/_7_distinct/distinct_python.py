import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-distinct") \
    .getOrCreate()
sc = spark.sparkContext

data = [1, 1, 2, 3, 4, 5, 5, 5, 6, 7]
sc.parallelize(data).distinct().foreach(print)

sc.stop()
spark.stop()