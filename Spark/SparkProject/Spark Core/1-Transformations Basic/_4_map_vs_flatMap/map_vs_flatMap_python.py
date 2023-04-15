import findspark
findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession\
    .builder\
    .master("local[1]")\
    .appName("spark")\
    .getOrCreate()
sc = spark.sparkContext

line1 = "Return a new distributed dataset formed by passing each element of the source through a function func"
line2 = "Similar to map, but each input item can be mapped to 0 or more output items"
line3 = "so func should return a Seq rather than a single item"


rdd = sc.parallelize([line1, line2, line3])
rdd.flatMap(lambda x: x.split(" ")).foreach(print)

print("-----------------------------")
rdd.map(lambda x: x.split(" ")).foreach(print)


sc.stop()
spark.stop()
