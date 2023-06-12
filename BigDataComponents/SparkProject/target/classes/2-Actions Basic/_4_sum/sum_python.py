import random

import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

# 使用random模块。该模块提供了多种用于生成随机数的函数，包括整数、浮点数、随机序列等
data = random.random()
# 四舍五入
data_ =round( data * 1000,2)

print(data_)


sc.stop()
spark.stop()