# hive常用命令

## 一、[数据类型](#DataType)

## 二、[DDL](#DDL)

- ### 1、[数据库](#databases)

- ### 2、[表](#tables)

- ### 3、[分区表](#partition_table)

- ### 4、[更改表结构](#ChangeTableStructure)

- ### 5、[查看表结构](#ReviewTableStructure)

## 三、[DML](#DML)

- ### 1、[导入数据](#import)

- ### 2、[导出数据](#export)

- ### 3、[导入数据到指定分区](#partition_data)

## 四、[DQL](#DQL)





- 进入hive

  ```
  [root@bigdata1 ~]# hive
  ```



<br>

---

 

## 一、<a id="DataType">数据类型</a>

| 分类       | 类型      | 描述                       |
| ---------- | :-------- | :------------------------- |
| 整数类型   | tinyint   | 一个字节的有符号整数       |
|            | smallint  | 两个字节的有符号整数       |
|            | bigint    | 八个字节的有符号整数       |
|            | int       | 四个字节的有符号整数       |
| 浮点型     | float     | 单精度浮点数               |
|            | double    | 双精度浮点数               |
| 布尔类型   | boolean   | 表示真或假                 |
| 字符串类型 | string    | 字符串类型                 |
|            | char      | 固定长度的字符串类型       |
|            | varchar   | 可变长度的字符串类型       |
| 时间类型   | timestamp | 时间戳类型，表示日期和时间 |
|            | date      | 日期类型，表示年月日       |





<br>

---

 

## 二、<a id="DDL">DDL</a>

### 1、<a id="databases">数据库</a>

- 查看数据库

  ```
  show databases;
  ```

- 创建数据库

  ```
  create database 数据库名;
  或者
  create database if not exists 数据库名;
  ```

- 删除数据库

  ```
  drop database 数据库名;
  ```

- 进入数据库

  ```
  use 数据库名;
  ```



<br>



### 2、<a id="tables">表</a>

- 查看数据表

  ```
  show tables;
  ```

- 创建表

  ```
  create table 表名(列名 类型,列名 类型,列名 类型)
  row format delimited
  fields terminated by '\t'
  lines terminated by '\n'
  ```

    -  row format delimited : 行的样式
    -  fields  terminated by '\t' ：文件中每行数据字段通过"\t"分隔
    -  lines terminated by '\n'  ：文件中行与行之间通过"\n"换行

- 删除表

  ```
  drop table 表名
  ```

  

<br>



### 3、<a id="partition_table">分区表</a>

- 创建分区表

  ```
  create [external] table 表名(列名 类型,列名 类型,列名 类型)
  partitioned by(分区列名 类型)
  row format delimited 
  fields terminated by '\t' 
  lines terminated by '\n';
  【partitioned by 即是创建表的分区】
  ```


- 删除分区

  ```
  alter table 表名 drop partition(分区列=分区列的值)
  alter table 表名 drop partition(分区列 <= 2022)
         【删除分区后该分区的数据也会被同步删除】
  ```

- 查看表的分区信息

  ```
  show partitions 表名;
  show partitions 库名.表名;
  ```

  

<br>



### 4、<a id="ChangeTableStructure">更改表结构</a>

- 更改表名

  ```
  alter table 旧表名 rename to 新表名;
  ```

- 新增字段

  ````
  alter table 表名 add  columns(列名 数据类型);
  ````

- 新增字段和注解

  ````
  alter table 表名 add  columns(列名 数据类型 comment '注解');
  ````

- 替换所有列（将替换所有现有列，并且仅更改表的架构，而不更改数据）

  ```
  alter table 表名 replace columns(foo INT, bar STRING, baz INT comment '注解');
  ```



<br>



### 5、<a id="ReviewTableStructure">查看表结构</a>

- 查看表的列信息

  ```
  desc 表名;
  ```

- 查看详细的表信息

  ```
  desc extended 表名;0
  ```

- 查看建表语句

  ```
  show create table 表名;
  ```

- 查看 hive 中所有的函数

  ```
  show functions;
  ```



<br>

---



## 三、<a id="DML">DML</a>

### 1、<a id="import">导入数据</a>

- 从**hdfs**导入数据

  ```
  load data inpath 'hdfs路径' overwrite into table hive表名;
  ```

- 从**本地文件系统上**导入数据

  ```
  load data local inpath '本地数据文件路径' overwrite into table hive表名;
  ```

  - local：输入文件位于本地文件系统上，如果省略则是从 HDFS 中导入文件
  - overwrite：表示删除表中的现有数据，如果省略 overwrite 关键字数据文件将**附加到现有数据集**



<br>



### 2、<a id="export">导出数据</a>

- 查询的结果导出**到本地**

  ```
  insert overwrite local directory '/home/hadoop/data/major_student_counts.csv'
  row format delimited
  fields terminated by ','
  select  *  from student;
  ```

- 查询的结果导出**到HDFS**

  ```
  insert overwrite local directory '/home/hadoop/data/major_student_counts.csv'
  row format delimited
  fields terminated by ','
  select  *  from student;
  ```




<br>



### 3、<a id="partition_data">导入数据到指定分区中</a>

- 从**hdfs**导入数据

  ```
  load data inpath 'hdfs路径' overwrite into table hive表名 partition(分区列名=值);
  ```

- 从**本地文件系统上**导入数据

  ```
  load data local inpath '本地数据文件路径' overwrite into table hive表名 partition(分区列名=值);
  ```

  - 分区列为字符串的话，='值'
  - 为什么要分区：分区在hive中创建后，在hdfs中体现的就是一个目录，目的是按分区列将数据切分为小文件，在检索的时候如果使用到了分区列，则只会涉及到该分区下的文件数据，提升检索使用的效率





<br>

---



## 四、<a id="DQL">DQL</a>















