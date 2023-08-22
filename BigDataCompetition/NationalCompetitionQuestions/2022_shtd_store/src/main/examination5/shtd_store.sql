# 1、根据dwd层表统计每人每天下单的数量和下单的总金额，
#    存入dws层的customer_consumption_day_aggr表（表结构如下）中，
#    然后在hive cli中按照cust_key，totalconsumption, totalorder三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#       字段	        类型	中文含义	备注
#       cust_key	int	客户key
#       cust_name	string	客户名称
#       totalconsumption	double	消费总额	当月消费订单总额
#       totalorder	int	订单总数	当月订单总额
#       year	int	年	订单产生的年
#       month	int	月	订单产生的月
#       day	int	日	订单产生的日
select c.CUSTKEY, c.NAME, sum(o.TOTALPRICE), count(*), year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE);



# 2、根据dws层表customer_consumption_day_aggr统计连续两个月下单并且下单金额保持增长的用户，于某年订单发生时间限制为大于等，
#    存入MySQL数据库shtd_store的usercontinueorder表(表结构如下)中。
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、客户主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中。
#       字段	    类型	中文含义	备注
#       custkey	int	客户主键
#       custname	text	客户名称
#       month	text	月	记录当前月和下月，用下划线‘_’相连例如： 202201_202202表示2022年1月到2月用户连续下单。
#       totalconsumption	double	消费总额	连续两月的订单总额
#       totalorder	int	订单总数	连续两月的订单总数
-- 一月下单并且下单的用户
select c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE), sum(o.TOTALPRICE), count(*)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE);
-- 连续两个月下单并且下单金额保持增长的用户
select t1.ck, t1.cn,
       concat(concat(t1.y1,if(t1.m1<10,concat("0",t1.m1),t1.m1)),"_",concat(t2.y2,if(t2.m2<10,concat("0",t2.m2),t2.m2))) as month,
       s1+s2, c1+c2
from (select c.CUSTKEY as ck, c.NAME as cn, year(o.ORDERDATE) as y1, month(o.ORDERDATE) as m1, sum(o.TOTALPRICE) as s1, count(*) as c1
      from customer as c
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t1
inner join (select c.CUSTKEY, c.NAME, year(o.ORDERDATE) as y2, month(o.ORDERDATE) as m2, sum(o.TOTALPRICE) as s2, count(*) as c2
            from customer as c
            inner join orders as o on c.CUSTKEY=o.CUSTKEY
            group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t2
on t1.ck=t2.CUSTKEY and((y1=y2 and t1.m1=t2.m2-1) or (y1=y2-1 and t1.m1=12 and t2.m2=01))
where s1<s2 and y1>= 1997;


# 3、根据任务2的结果并结合表dim_nation与表customer_consumption_day_aggr，
#    请计算每个国家连续两个月下单并且下单金额保持增长与该国已下单用户的占比，
#    将结果存入MySQL数据库shtd_store的userrepurchased表(表结构如下)中。
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、客户主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中。
#       字段	        类型	 中文含义	备注
#       nationkey	int	国家主键
#       nationname	string	国家名称
#       purchaseduser	int	下单人数	该国家内已下单人数
#       repurchaseduser	double	连续下单人数	该国家内连续两个月下单并且下单金额保持增长人数
#       repurchaserate	int	占比	下单人数/连续下单人数（保留3位小数）
-- 每个国家一个月下单
select n.NATIONKEY, n.NAME, count(*), year(o.ORDERDATE), month(o.ORDERDATE)
from customer as c
inner join nation as n on c.NATIONKEY=n.NATIONKEY
inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by n.NATIONKEY, n.NAME, year(o.ORDERDATE), month(o.ORDERDATE);
-- 每个国家连续两个月下单并且下单金额保持增长
select t1.nk, t1.nn, c1, c2, round(c1/c2,3)
from (select n.NATIONKEY as nk, n.NAME as nn, count(o.CUSTKEY) as c1, sum(o.TOTALPRICE) as s1,
             year(o.ORDERDATE) as y1, month(o.ORDERDATE) as m1
      from customer as c
      inner join nation as n on c.NATIONKEY=n.NATIONKEY
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      group by n.NATIONKEY, n.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t1
inner join (select n.NATIONKEY, n.NAME, count(o.CUSTKEY) as c2, sum(o.TOTALPRICE) as s2,
                   year(o.ORDERDATE) as y2, month(o.ORDERDATE) as m2
            from customer as c
            inner join nation as n on c.NATIONKEY=n.NATIONKEY
            inner join orders as o on c.CUSTKEY=o.CUSTKEY
            group by n.NATIONKEY, n.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t2
on t1.nk=t2.NATIONKEY and ((t1.y1=t2.y2 and t1.m1=t2.m2-1) or (t1.y1=t2.y2-1 and t1.m1=12 and t2.m2=01))
where s1<s2;

