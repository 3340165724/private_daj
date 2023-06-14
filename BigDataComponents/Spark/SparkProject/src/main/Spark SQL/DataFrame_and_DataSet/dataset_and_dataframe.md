# DataFrame  类似数据库中的二维表格

--------

<br>


### DataFrame 与 RDD 的区别
- DataFrame
  - 有 schema 元信息（表示的二维表数据集的每一列都带有名称和类型）
  - 是懒执行的，但性能上比 RDD 要高

<br>

--------


### 创建 DataFrame 
- SparkSession 是创建 DataFrame 和执行 SQL 的入口
- 创建 DataFrame 有三种方式
  - 通过spark的数据源进行创建
  - 从一个存在的RDD进行转换
  - 从 Hive Table进行查询返回 

<br>

--------


### SQL 语法
- 创建df
- 创建临时表
  - 对 DataFrame 创建一个**临时表**
    - createOrReplaceTempView： spark.newSession().sql()会失效
  - 通过 SQL 语句实现查询全表
    ```
    spark.sql("SELECT * FROM 临时表名")
    ```
  - 对于 DataFrame 创建一个**全局表**
    ```aidl
    df.createGlobalTempView("临时表名")
    ```
  - 通过 SQL 语句实现查询全表
    ```aidl
    spark.sql("SELECT * FROM global_temp.临时表名").show()
    ```
  - 注意：普通临时表是 Session 范围内的，如果想应用范围内有效，可以使用全局临时表。使
      用全局临时表时需要全路径访问，如：global_temp.临时表名

<br>

- 结果展示
  ```
  sqlDF.show
  ```

<br>
<br>

### DSL 语法（了解）
- 使用 DSL 语法风格不必去创建临时视图了

<br>

- 创建一个 DataFrame
- 查看 DataFrame 的 Schema 信息
  ```
  df.printSchema
  ```
  
- 只查看某个字段的数据
  ```aidl
  df.select("username").show()
  ```
  
- 涉及到**运算**的时候, **每列都必须使用$**, 或者采用引号表达式：单引号+字段名
  ```aidl
  df.select($"username",$"age" + 1).show
  df.select('username, 'age + 1).show()
  ```
  
- 按照"age"分组，查看数据条数
  ```aidl
  df.groupBy("age").count.show
  ```
  


<br>
<br>
<br>
<br>

----------


# DataSet 分布式数据集

-------

<br>

###  DataSet解释
- DataSet 是具有强类型的数据集合，需要提供对应的类型信息
- DataFrame 是 DataSet 的特列，DataFrame=DataSet[Row] ，所以可以通过 as 方法将DataFrame 转换为 DataSet
  - Row 是一个类型
- SparkSession是SQLContext 和 HiveContext的组合

