# hive

- 编辑数据
  - vim 文件名
  
- 查看数据绝对路径
  - pwd
  
- 进入hive
  - hive
  
- 创建数据库
  - show databases;
  
- 创建数据库
  - create database 数据库名; 或
  -  create database if not exists 数据库名;

- 删除数据库
  - drop database 数据库名;

- 进入数据库
  - use 数据库名;
  
- 创建表

  -  create table 表名(列名 类型,列名 类型,列名 类型)
      row format delimited
      fields terminated by '\t'
      lines terminated by '\n'

      stored as textfile

       ;

    

    -  row format delimited : 行的样式
    -  fields  terminated by '\t' ：文件中每行数据字段通过"\t"分隔
    -  lines terminated by '\n'  ：文件中行与行之间通过"\n"换行
    - stored as textfile : 存储方式（可要可不要）

- 数据导入表中
  - 将hdfs中的数据文件导入hive中
    - load data inpath 'hdfs路径' overwrite into table hive表名;
      - load data inpath '/test/hive-data' overwrite into table user_info;
  - 将虚拟机中的数据文件导入hive中
    - load data local inpath '虚拟机中的数据文件路径' overwrite into table hive表名;
      - load data local inpath '/home/hadoop/hive-data' overwrite into table user_info;

- 查看表的列信息
  - desc table 表名；