import findspark
from pyspark.sql import SparkSession

from pyspark.sql import SparkSession

spark = SparkSession.builder.master("local[*]").getOrCreate()

sc = spark.sparkContext

rdd1 = sc.parallelize(list(range(0,10,2))
rdd1.map(lambda x: x * 10).foreach(print)

sc.stop()
spark.stop()
