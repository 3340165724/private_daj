import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark") \
    .getOrCreate()
sc = spark.sparkContext

rdd1 = sc.parallelize([20, 30, 40, 50, 60])
rdd2 = sc.parallelize(["zhangsan", "lisi", "wangwu", "sunfeng", "wulan",20,30])

print(rdd1.cartesian(rdd2).collect())
print(rdd2.cartesian(rdd1).collect())


sc.stop()
spark.stop
