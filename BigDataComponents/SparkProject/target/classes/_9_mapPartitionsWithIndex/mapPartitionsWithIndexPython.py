import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

rdd = sc.parallelize([20, 30, 40, 50, 60], 3)

# rdd.map(lambda index,element:"[value=%s, partition_id=%d]" % (element,index)).foreach(print)

list = []


def fun(index, element):
    for x in element:
        list.append(x)
        yield "[value=%s, partition_id=%d]" % (x, index)

rdd.mapPartitionsWithIndex(fun).foreach(print)

sc.stop()
spark.stop
