# hive 测试2



1、本地创建文件并添加数据

2、上传文件到 hdfs

```
hadoop fs -put /home/hadoop/employee.csv /opt/data
hadoop fs -put /home/hadoop/department.csv /opt/data
```

3、进入hive

4、创建数据库

```
create database empdb;
```

5、进入数据库

```
use empdb；
```

6、创建表

```
create table employee(id int, name string, age int, gender string, job string, d_id int)
row format delimited
fields terminated by ','
lines terminated by '\n';


create table department(id int, name string)
row format delimited
fields terminated by ','
lines terminated by '\n';
```

7、将hdfs数据导入到hive

```
load data inpath '/opt/data/employee.csv' overwrite into table employee;
load data inpath '/opt/data/department.csv' overwrite into table department;
```

8、查询

- 查询年龄在26至28岁之间（包含26和28岁）的所有员工的姓名、年龄和岗位

```
select name ,age , job from employee where age >= 26 and age <= 28;
```



- 查询所有部门为“设计部”的员工信息。

```
select * from employee as e ,department as d  where e.d_id = d.id and  d.name='设计部';
```



- 查询每个部门的员工数量，并打印出部门名称对应的员工数量。

```
select name , count(*) from employee as e , department as d where e.d_id = d.id group by d.name;
```



- 将上述查询结果（每个部门的员工数量）导出到一个新的CSV文件中，输出路径为：“/home/hadoop/data/employee_department.csv”

```
insert overwrite local directory '/home/hadoop/data/employee_department.csv'
row format delimited
fields terminated by ','
select name , count(*) from employee as e , department as d where e.d_id = d.id group by d.name;
```



- 查询部门员工的平均年龄，并按平均年龄升序排列。

```
select d.name, avg(age) as avg_age  from employee as e , department as d  where e.d_id = d.id group by d.name order by avg_age;
```



- 查询“张三”员工在那个部门。

```
select  d.name from employee as e , department as d  where e.d_id = d.id and e.name = "张三";
```



- 在employee表中增加一列名为address的STRING类型字段，表示地址，并将所有工作为美工

  的员工的地址设置为"WUHAN"，其他工作的员工部门设置为"NONE"，请创建一个新的“employee_with”表并转移数据

```
create table employee_with(id int, name string , job string, address string) ;

alter table employee add columns(address string) ;

insert into table employee_with
select id , name ,job ,
case when job = '美工'  then 'WUHAN' else 'NONE' end as address from employee;
```

