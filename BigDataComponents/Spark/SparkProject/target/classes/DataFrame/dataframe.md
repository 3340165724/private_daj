# DataFrame  类似于传统数据库中的二维表格

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
- 查看 Spark 支持创建文件的数据源格式（**查看数据格式**）
  ```
  spark.read.  +    Tab
  ```
- 读取 json 文件创建 DataFrame
  ```
  spark.read.json("json文件路径")
  ```
  - 注意：如果从内存中获取数据，spark 可以知道数据类型具体是什么。如果是数字，默认作为 Int 处理；但是从文件中读取的数字，不能确定是什么类型，所以用 bigint 接收，可以和Long 类型转换，但是和 Int 不能进行转换


- 创建临时表
  - 对 DataFrame 创建一个**临时表**
    - spark.newSession().sql()会失效
    ```
    df.createOrReplaceTempView("临时表名")
    ```
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
- 结果展示
  ```
  sqlDF.show
  ```

### DSL 语法
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

- 查看"age"大于"30"的数据
  ```aidl
  df.filter($"age">30).show
  ```
  
- 按照"age"分组，查看数据条数
  ```aidl
  df.groupBy("age").count.show
  ```

### RDD 转换为 DataFrame
- 在 IDEA 中开发程序时，如果需要 RDD 与 DF 或者 DS 之间互相操作，那么需要引入**import spark.implicits._**

### DataFrame 转换为 RDD