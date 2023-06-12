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
rdd = sc.parallelize([1, 2, 3, 4, 5])

# 使用collect()将所有数字收集到driver节点中
result = rdd.collect()

# 将结果相加并输出
print(sum(result))

sc.stop()
spark.stop()