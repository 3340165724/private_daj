hive 测试

1、本地创建文件

2、在 hdfs 中创建目录

```
hadoop fs -mkdir -p /opt/data
```

3、将文件上传到 hdfs

```
hadoop fs -put /home/hadoop/student.csv /opt/data
```

4、进入hive

5、创建数据库

```
create database educational;
```

6、进入到 educational 数据库中

```
use educational;
```

7、创建表

```
create table student(id int,name string , age int,gender string , major string)
row format delimited
fields terminated by ','
lines terminated by '\n';


create table courses(id int, name string,credit int,department string)
row format delimited
fields terminated by ','
lines terminated by '\n';
```

8、从 hdfs 导入数据到 hive表

```
load data inpath '/opt/data/student.csv' overwrite into table student;
load data inpath '/opt/data/Courses.csv' overwrite into table courses;
```



9 、查询

- 查询年龄在21至23岁之间（包含21和23岁）的所有学生的姓名、年龄和专业。

```
select name ,age , major from student where age >= 21 and age <= 23 ;
```



- 查询所有专业为“计算机科学”的课程名称和学分。

```
select name , credit from courses where department='计算机科学';
```



- 查询每个专业的学生数量，结果按学生数量降序排列。

````
select major, count(*) as num  from student group by major order by num desc;
````



- 将上述查询结果（每个专业的学生数量）导出到一个新的CSV文件中\1) 输出路径为：“/home/hadoop/data/major_student_counts.csv”

```
insert overwrite local directory '/home/hadoop/data/major_student_counts.csv'
row format delimited
fields terminated by ','
select major, count(*) from student group by major;
```



- 查询每个专业的平均年龄，并按平均年龄升序排列。

```
select major , avg(age) as avg_age  from student group by major order by avg_age asc;
```



- 查询每个专业至少有2名学生的专业名称及对应的学生数量。

```
select  major , count(*) as num  from student group by major having num >= 2;
```



- 在students表中增加一列名为class的STRING类型字段，表示班级，并将所有计算机科学专业的学生的班级设置为"CS1"，其他专业的学生班级设置为"OTHERS"，请创建一个新的“students_with_class”表并转移数据

```
create table students_with_class(id int, name string,major string , class string);

alter table student add columns (class string);

insert into table students_with_class
select id, name, major ,
 case when major='计算机科学' then 'CS1' else 'OTHERS' end as class from student;

```



- 请自行设计并创建选课表：enrollments，要求该表可记录每个学生的选课情况，已知张三选课：统计学、微积分、大数据；请在enrollments表中添加张三的选课记录

```
```



















