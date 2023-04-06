from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-map_scala") \
    .getOrCreate()

sc = spark.sparkContext

rdd = sc.parallelize([1, 2, 3, 4, 5])
#  使用map将每个元素乘以2
new_rdd = rdd.map(lambda x: x * 2)
#  输出新的RDD
print(new_rdd.collect())

sc.stop
spark.stop
