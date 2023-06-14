# 读取不同类型的数据

-----------

<br>

### 交互式查看有什么数据格式
 ```
 spark.read.  +    Tab
  ```
- 注意：如果从内存中获取数据，spark 可以知道数据类型具体是什么。如果是数字，默认作为 Int 处理；但是从文件中读取的数字，不能确定是什么类型，所以用 bigint 接收，可以和Long 类型转换，但是和 Int 不能进行转换

### 数据格式
csv   format   jdbc   json   
load   option   options   orc  
parquet   schema   table   text   textFile




