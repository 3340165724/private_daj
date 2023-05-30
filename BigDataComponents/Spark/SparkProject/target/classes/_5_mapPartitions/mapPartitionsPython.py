import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

rdd = sc.parallelize([1, 2, 3, 4, 5], 2)


#
# new_rdd = rdd.mapPartitions(lambda partition: map(lambda x: x * 10, partition)).collect()
# result_map = new_rdd.map(lambda x: (x, x * 2)).collect()
# print(result_map)

def func(data):
    # data：接受一整个分区的数据，mapPartition将整个分区数据传递给data
    for i in data:
        yield (i, i * 2)


new = rdd.mapPartitions(func).collect()
print(new)

sc.stop()
spark.stop
