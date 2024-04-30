import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

# 创建一个RDD
rdd = sc.parallelize(range(10))

# 获取第一个元素
first_element = rdd.first()
print(first_element)        # 0

sc.stop()
spark.stop
