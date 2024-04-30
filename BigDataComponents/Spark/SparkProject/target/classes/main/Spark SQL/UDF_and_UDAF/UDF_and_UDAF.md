# UDF 和 UDAF 了解
# UDF

------------

<br>

### 作用
- 添加功能

### 使用
- 创建DataFrame
- 创建临时表
- 注册 UDF
  - spark.udf.register("UDF名称", 功能实现)
- 应用 UDF
  - spark.sql("select UDF名称(功能改变前的字段名) , age from user").show()



<br>
<br>
<br>
<br>

# UDAF

---------

