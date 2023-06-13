 # DataSet 分布式数据集
 
 -------

<br>

###  DataSet解释
- DataSet 是具有强类型的数据集合，需要提供对应的类型信息
- DataFrame 是 DataSet 的特列，DataFrame=DataSet[Row] ，所以可以通过 as 方法将DataFrame 转换为 DataSet
  - Row 是一个类型
- SparkSession是SQLContext 和 HiveContext的组合

