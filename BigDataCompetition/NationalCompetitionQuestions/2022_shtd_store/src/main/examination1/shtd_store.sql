# 1、编写Scala工程代码，根据dwd层表统计每个地区、每个国家、每个月下单的数量和下单的总金额，
#    存入MySQL数据库shtd_store的nationeverymonth表（表结构如下）中，
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、国家表主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#       字段	        类型	中文含义	备注
#       nationkey	int	国家表主键
#       nationname	text	国家名称
#       regionkey	int	地区表主键
#       regionname	text	地区名称
#       totalconsumption	double	消费总额	当月消费订单总额
#       totalorder	int	订单总数	当月订单总额
#       year	int	年	订单产生的年
#       month	int	月	订单产生的月
-- 统计每个地区、每个国家、每个月下单的数量和下单的总金额
select r.REGIONKEY, r.NAME, n.NATIONKEY, n.NAME, year (o.ORDERDATE), month(o.ORDERDATE), count(*), sum(o.TOTALPRICE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
inner join nation as n on n.NATIONKEY=c.NATIONKEY
inner join region as r on r.REGIONKEY=n.REGIONKEY
group by r.REGIONKEY, r.NAME, n.NATIONKEY, n.NAME, year (o.ORDERDATE), month(o.ORDERDATE);
-- 存入MySQL数据库shtd_store的nationeverymonth表（表结构如下）中，然后据订单总数、消费总额、国家表主键三列均逆序排序的方式，查询出前5条
select *
from (select r.REGIONKEY as rk , r.`NAME` as rn,
             n.NATIONKEY as nk, n.`NAME` as nn,
             year (o.ORDERDATE) as y1, month(o.ORDERDATE) as m1,
             count(*) as c1, sum(o.TOTALPRICE) as s1
      from customer as c
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      inner join nation as n on n.NATIONKEY=c.NATIONKEY
      inner join region as r on r.REGIONKEY=n.REGIONKEY
      group by r.REGIONKEY, r.NAME, n.NATIONKEY, n.NAME, year (o.ORDERDATE), month(o.ORDERDATE)) as nationeverymonth
order by c1 desc, s1 desc, nk desc
limit 5;



# 2、请根据dwd层表计算出某年每个国家的平均消费额和所有国家平均消费额相比较结果（“高/低/相同”）,
#    存入MySQL数据库shtd_store的nationavgcmp表（表结构如下）中，
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、国家表主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#    Select nation.nationkey,nation.name,avg(totalprice)
#    over(partition by nation.nationkey,substr(orderdate,1,4)) as nation_avg,avg(totalprice)
#    over() as all_avg from orders inner join customer on orders.custkey = customer.custkey
#    inner join nation on customer.nationkey = nation.nationkey出来的结果去重
#           字段	    类型	中文含义	备注
#       nationkey	int	国家表主键
#       nationname	text	国家名称
#       nationavgconsumption	double	该国家内客单价	该国家已购买产品的人均消费额
#       allnationavgconsumption	double	所有国家内客单价	所有国家已购买的产品的人均消费额
#       comparison	string	比较结果	国家内人均和所有国家人均相比结果有：高/低/相同

-- 某年每个国家的平均消费额
select n.NATIONKEY, n.NAME, avg(o.TOTALPRICE)
from customer as c
         inner join nation as n on c.NATIONKEY=n.NATIONKEY
         inner join orders as o on o.CUSTKEY=c.CUSTKEY
group by year(o.ORDERDATE), n.NATIONKEY, n.NAME;
-- 某年所有国家平均消费额
select year(o.ORDERDATE), avg(o.TOTALPRICE)
from customer as c
    inner join nation as n on c.NATIONKEY=n.NATIONKEY
    inner join orders as o on o.CUSTKEY=c.CUSTKEY
group by year(o.ORDERDATE);
-- 某年每个国家的平均消费额和所有国家平均消费额相比较结果（“高/低/相同”）
select t1.NATIONKEY, t1.NAME, avg1, avg2,
       case when avg1>avg2 then "高" when avg1<avg2 then "低"  when avg1=avg2 then "相同" end
from (select year(o.ORDERDATE) as y1, n.NATIONKEY, n.NAME, avg(o.TOTALPRICE) as avg1
      from customer as c
      inner join nation as n on c.NATIONKEY=n.NATIONKEY
      inner join orders as o on o.CUSTKEY=c.CUSTKEY
      group by year(o.ORDERDATE), n.NATIONKEY, n.NAME) as t1
inner join (select year(o.ORDERDATE) as y2, avg(o.TOTALPRICE) as avg2
            from customer as c
            inner join nation as n on c.NATIONKEY=n.NATIONKEY
            inner join orders as o on o.CUSTKEY=c.CUSTKEY
            group by year(o.ORDERDATE)) as t2
on t1.y1=t2.y2;


# 3、编写Scala工程代码，根据dwd层表统计连续两个月下单并且下单金额保持增长的用户，
#    订单发生时间限制为大于等于某年，存入MySQL数据库shtd_store的usercontinueorder表(表结构如下)中。
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、客户主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中。
#       字段	    类型	中文含义	备注
#       custkey	int	客户主键
#       custname	text	客户名称
#       month	text	月	记录当前月和下月，用下划线‘_’相连。例如： 202201_202202表示2022年1月到2月用户连续下单。
#       totalconsumption	double	消费总额	连续两月的订单总额
#       totalorder	int	订单总数	连续两月的订单总数
-- 某年某月下单的情况
select c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE), sum(o.TOTALPRICE)
from customer as c
         inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE);
-- 连续两个月下单并且下单金额保持增长的用户
select  t1.CUSTKEY, t1.NAME,
        concat(concat(t1.y1,if(t1.m1<10,concat("0",t1.m1),t1.m1)),"_",concat(t2.y2,if(t2.m2<10,concat("0",t2.m2),t2.m2))) as ti
from (select c.CUSTKEY, c.NAME, year(o.ORDERDATE) as y1, month(o.ORDERDATE) as m1, sum(o.TOTALPRICE) as s1
      from customer as c
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t1
inner join (select c.CUSTKEY, c.NAME, year(o.ORDERDATE) as y2, month(o.ORDERDATE) as m2, sum(o.TOTALPRICE) as s2
            from customer as c
            inner join orders as o on c.CUSTKEY=o.CUSTKEY
            group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t2
on t1.CUSTKEY=t2.CUSTKEY and ((t1.y1=t2.y2 and t1.m1=t2.m2-1 ) or (t1.y1=t2.y2-1 and t1.m1=12 and t2.m2=01))
where s1<s2 and y1>= 1997;


