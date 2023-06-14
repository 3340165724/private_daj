# RDD、DataFrame 和 DataSet 的关系

-------------

<br>


### 三者的互相转换
![RDD_DF_DS](../../../../../../../Image/RDD_DF_DS.png "RDD_DF_DS")


### 三者的共性
- 三者都有惰性机制
- 在对 DataFrame 和 Dataset 进行操作许多操作都需要这个包:import spark.implicits._（在创建好 SparkSession 对象后尽量直接导入）
- DataFrame 和 DataSet 均可使用模式匹配获取各个字段的值和类型


### 三者的区别
- RDD 不支持 sparksql 操作
- 与 RDD 和 Dataset 不同，DataFrame 每一行的类型固定为 Row，每一列的值没法直接访问，只有通过解析才能获取各个字段的值
- DataFrame 与 DataSet 均支持 SparkSQL 的操作，比如 select，groupby 之类，还能注册临时表/视窗，进行 sql 语句操作
- DataFrame 其实就是 DataSet 的一个特例 type DataFrame = Dataset[Row]

