import os
# import findspark
from pyspark.sql import SparkSession
# from pyspark import SparkConf, SparkContext

# 解决Python worker failed to connect back问题的两种方式
# findspark.init()
os.environ['PYSPARK_PYTHON'] = "D:\Python\Python37\python.exe"


# 创建一个SparkContext对象,它告诉Spark如何访问集群 的两种方式
spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-map_scala") \
    .getOrCreate()
sc = spark.sparkContext

# conf = SparkConf().setAppName("spark").setMaster("local[1]")
# sc = SparkContext(conf=conf)

# 创建rdd
# rdd = sc.textFile("../../Resource/data.txt")
# rdd.flatMap(lambda x:x.split(' ')).foreach(print)

# 处理数据
rdd1 = sc.parallelize([2, 3, 4])
rdd1.flatMap(lambda x: range(1, x)).foreach(print)

# sorted(rdd.flatMap(lambda x: range(1, x)).collect())
print("dczxc")
print("s" * 2)

spark.stop()
sc.stop()
