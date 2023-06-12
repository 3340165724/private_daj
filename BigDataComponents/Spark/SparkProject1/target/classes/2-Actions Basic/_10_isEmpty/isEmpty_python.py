import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

data1 = [1, 2, 3]
data2 = []
data3 = ""
data4 = [None]

rdd1 = sc.parallelize(data1).isEmpty()
print("rdd1",rdd1)      # rdd1 False

rdd2 = sc.parallelize(data2).isEmpty()
print("rdd2",rdd2)      # rdd2 True

rdd3 = sc.parallelize(data3).isEmpty()
print("rdd3",rdd3)      # rdd3 True

rdd4 = sc.parallelize(data4).isEmpty()
print("rdd4",rdd4)      # rdd4 False


sc.stop()
spark.stop()