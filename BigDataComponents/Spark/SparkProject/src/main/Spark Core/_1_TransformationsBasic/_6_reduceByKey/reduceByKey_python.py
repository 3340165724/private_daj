import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-reduceByKey_python") \
    .getOrCreate()
sc = spark.sparkContext

data = [("a", 1), ("a", 1), ("b", 2), ("b", 3)]

rdd = sc.parallelize([("a", 5), ("b", 1), ("a", 1)]).reduceByKey(lambda x, y: x + y).foreach(print)

sc.stop()
spark.stop()
