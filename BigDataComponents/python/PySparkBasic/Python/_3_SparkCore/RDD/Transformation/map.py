import findspark
findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession.builder.master("local[*]").getOrCreate()
sc = spark.sparkContext

rdd = sc.parallelize(["1", "2", "3", "4"])
result = rdd.map(lambda word: int(word)).foreach(print)

sc.stop()
spark.stop()
