# from pyspark.sql import SparkSession
#
# spark = SparkSession \
#     .builder \
#     .master("local[1]") \
#     .appName("spark-map_scala") \
#     .getOrCreate()
# sc = spark.sparkContext
import findspark
findspark.init()

from pyspark import SparkConf, SparkContext

conf = SparkConf().setAppName("spark").setMaster("local[1]")
sc = SparkContext(conf=conf)

data = [1, 2, 3, 4, 5]
distData = sc.parallelize(data).map(lambda x: (x, "a" * x)).foreach(print)

# sorted(rdd.flatMap(lambda x: range(1, x)).collect())
print("dczxc")
print("s" * 2)

sc.stop()
