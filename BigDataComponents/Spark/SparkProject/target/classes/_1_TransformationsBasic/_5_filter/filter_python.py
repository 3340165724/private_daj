import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-filter_python") \
    .getOrCreate()

sc = spark.sparkContext

data = [1, 2, 5, 10, 8, 9, 145, 45, 56, 1254, 2]
sc.parallelize(data).filter(lambda x: x % 2 == 0).foreach(print)
# 输出
# 2
# 10
# 8
# 56
# 1254
# 2

sc.stop()
spark.stop()
