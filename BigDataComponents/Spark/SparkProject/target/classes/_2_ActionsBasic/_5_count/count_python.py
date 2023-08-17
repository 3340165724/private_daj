import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

# 创建 RDD
data = [1, 2, 3, 4, 5]
rdd = sc.parallelize(data)

# 使用 count() 方法计算 RDD 中元素的数量
count = rdd.count()
print("RDD中元素的数量为:", count)

sc.stop()
spark.stop()