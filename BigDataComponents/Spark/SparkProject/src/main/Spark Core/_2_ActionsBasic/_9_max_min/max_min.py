import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext
# 创建一个 RDD
rdd = sc.parallelize([1, 2, 3, 4, 5, 6])

# 计算 RDD 中的最大值
max_val = rdd.max()
print(max_val)  # 输出结果：6


sc.stop()
spark.stop
