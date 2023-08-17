import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext


# 创建一个包含数字的RDD
rdd = sc.parallelize([5, 3, 7, 2, 1, 6, 4])

# 使用take()函数获取RDD的前3个元素
result1 = rdd.take(3)
print(result1)  # [5, 3, 7]


sc.stop()
spark.stop