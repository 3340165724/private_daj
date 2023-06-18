# 读取不同类型的数据

-----------

<br>

### 交互式查看有什么数据格式
 ```
 spark.read.  +   Tab
  ```
- 注意：如果从内存中获取数据，spark 可以知道数据类型具体是什么。如果是数字，默认作为 Int 处理；但是从文件中读取的数字，不能确定是什么类型，所以用 bigint 接收，可以和Long 类型转换，但是和 Int 不能进行转换

### 数据格式
csv   format   jdbc   json   
load   option   options   orc  
parquet   schema   table   text   textFile


### 加载数据
- format("…")：指定加载的数据类型
- load("…")：数据的路径
- option("…")：格式参数

### 保存数据 
- df.write.save 是保存数据的通用方法
- df.write.format("…")[.option("…")].save("…")
  - format("…")：指定保存的数据类型
  - save ("…")：保存数据的路径
  - mode("…")：保存方式
    - "error"(default)：文件已经存在则抛出异常
    - "append"：追加
    - "overwrite"：覆盖
    - "ignore"：忽略

<br>
<br>
<br>
<br>

# hive

------

<br>

### 连接外部已经部署好的 hive 
- 拷贝hive配置文件到 spark/conf/目录
- 拷贝MySQL驱动到 spark/jars目录
- 如果访问不到 hdfs 拷贝core-site.xml 和 hdfs-site.xml到 spark/conf/目录



### 错误
- 问题
  - Caused by: java.lang.IllegalArgumentException: Unable to instantiate SparkSession with Hive support because Hive classes are not found. 	
- 解决
  - pom.xml文件检查是否指定版本
  - 找到spark-hive依赖去除<scope>provided</scope>