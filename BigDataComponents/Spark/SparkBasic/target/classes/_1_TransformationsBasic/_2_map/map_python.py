import findspark
findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-map_python") \
    .getOrCreate()
sc = spark.sparkContext


rdd1 = sc.parallelize([1, 2, 5, 8, 9])
rdd1.map(lambda x: x * 10).foreach(print)


sc.stop()
spark.stop()
