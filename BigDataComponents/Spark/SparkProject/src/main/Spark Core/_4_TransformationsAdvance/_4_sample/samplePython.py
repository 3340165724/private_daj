import findspark

findspark.init()

from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-foreach_python") \
    .getOrCreate()
sc = spark.sparkContext

s = sc.parallelize(range(100), 4).sample(False, 0.1, 81).count() <= 14
print(s)

sc.stop()
spark.stop