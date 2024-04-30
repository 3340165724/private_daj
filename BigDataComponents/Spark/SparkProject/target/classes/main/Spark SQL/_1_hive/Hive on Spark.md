# Hive on Spark  了解

----

<br>

### hive底层引擎
- hive：将sql转为mapreduce作业来执行
  - 缺点：效率不高
  - 解决：
    - hive on tez （）
    - shark（hive on spark（将 Spark 作为 Hive 的底层引擎））
- shark：基于spark、基于内存的列式存储、与hive兼容
- shark终止后，产生两个分支
  - hive on spark (不建议用)
  - **_Spark SQL_**


