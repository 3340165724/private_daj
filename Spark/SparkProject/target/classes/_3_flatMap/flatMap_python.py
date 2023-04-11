from pyspark.sql import SparkSession

spark = SparkSession \
    .builder \
    .master("local[1]") \
    .appName("spark-map_scala") \
    .getOrCreate()
sc = spark.sparkContext

rdd = sc.parallelize([2, 3, 4, 5])

sorted(rdd.flatMap(lambda x: range(1, x)).collect())
print("dczxc")


sc.stop()
