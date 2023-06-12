import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark") \
    .getOrCreate()
sc = spark.sparkContext

rdd1 = sc.parallelize([("zhangsan", 2), ("lisi", 1), ("tom", 5), ("zhangsan", 6)])
rdd2 = sc.parallelize([("wulan", 3), ("lisi", 5), ("sunfeng", 2), ("tom", 6)])

rdd3 = rdd1.cogroup(rdd2).collect()

# print([(x, tuple(map(list, y))) for x, y in sorted(list(rdd1.cogroup(rdd2).collect()))])


sc.stop()
spark.stop
