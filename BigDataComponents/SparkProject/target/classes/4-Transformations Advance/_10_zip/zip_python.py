import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-zip") \
    .getOrCreate()
sc = spark.sparkContext

rdd1 = sc.parallelize([20, 30, 40, 50, 60], 3)
rdd2 = sc.parallelize(["zhangsan", "lisi", "wangwu", "sunfeng", "wulan"], 3)

rdd = rdd2.zip(rdd1).foreach(print)

sc.stop()
spark.stop
