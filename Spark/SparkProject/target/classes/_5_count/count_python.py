import findspark

findspark.init()

from pyspark.sql import SparkSession
from pyspark.sql.functions import count
from pyspark.sql.types import IntegerType, StructType, StructField

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

# 创建一个包含整数值的RDD
rdd = sc.parallelize([1, 2, 3, 4, 5])

# 将RDD转换为DataFrame
schema = StructType([StructField("value", IntegerType(), True)])
df = spark.createDataFrame(rdd.map(lambda x: (x, )), schema)

# 使用count函数聚合并返回结果
count_result = df.agg(count("*")).collect()[0][0]
print("RDD中元素数量:", count_result)

sc.stop()
spark.stop()