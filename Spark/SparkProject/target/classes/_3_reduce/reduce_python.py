import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

list = [1, 0, 8, 5, 6, 4, 5, 2, 457, 7]

rdd = sc.parallelize(list).reduce(lambda x, y: x + y)
print("sum:%s" % rdd)

sc.stop()
spark.stop()
