# 1、根据dwd层表统计每人每天下单的数量和下单的总金额，
#    存入dws层的customer_consumption_day_aggr表（表结构如下）中，
#    然后在hive cli中按照cust_key，totalconsumption, totalorder三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#        字段	     类型	中文含义	备注
#       cust_key	int	客户key
#       cust_name	string	客户名称
#       totalconsumption	double	消费总额	当月消费订单总额
#       totalorder	int	订单总数	当月订单总额
#       year	int	年	订单产生的年
#       month	int	月	订单产生的月
#       day	int	日	订单产生的日
-- 统计每人每天下单的数量和下单的总金额
select c.CUSTKEY, c.NAME, sum(o.TOTALPRICE), count(*), year(o.ORDERDATE), month(o.ORDERDATE),day(o.ORDERDATE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE),day(o.ORDERDATE)
-- 存入dws层的customer_consumption_day_aggr表（表结构如下）中，然后在hive cli中按照cust_key，totalconsumption, totalorder三列均逆序排序的方式，查询出前5条
select customer_consumption_day_aggr.*
from (select c.CUSTKEY as ck, c.NAME, sum(o.TOTALPRICE) as s1, count(*) as c1, year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE)
      from customer as c
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE),day(o.ORDERDATE)) as customer_consumption_day_aggr
order by customer_consumption_day_aggr.ck desc, customer_consumption_day_aggr.s1 desc, customer_consumption_day_aggr.c1 desc
limit 5;


# 2、根据dws层表customer_consumption_day_aggr表，
#    再联合dwd.dim_region,dwd.dim_nation统计每人每个月下单的数量和下单的总金额，
#    并按照cust_key，totalconsumption，totalorder，month进行分组逆序排序（以cust_key为分组条件），
#    将计算结果存入MySQL数据库shtd_store的nationeverymonth表（表结构如下）中，
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、国家表主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#         字段	    类型	中文含义	备注
#       cust_key	int	客户key
#       cust_name	string	客户名称
#       nationkey	int	国家表主键
#       nationname	text	国家名称
#       regionkey	int	地区表主键
#       regionname	text	地区名称
#       totalconsumption	double	消费总额	当月消费订单总额
#       totalorder	int	订单总数	当月订单总额
#       year	int	年	订单产生的年
#       month	int	月	订单产生的月
#       sequence	Int	次序
-- 每人每个月下单的数量和下单的总金额
select c.CUSTKEY, c.NAME as cn, n.NATIONKEY, n.NAME as nn, r.REGIONKEY, r.NAME as rn,
       sum(o.TOTALPRICE) as s1, count(*) as c1,
       year(o.ORDERDATE) as y1, month(o.ORDERDATE) as m1,
       row_number() over (order by sum(o.TOTALPRICE) desc, count(*) desc, month(o.ORDERDATE) desc)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
inner join nation as n on n.NATIONKEY=c.NATIONKEY
inner join  region as r on r.REGIONKEY=n.REGIONKEY
group by c.CUSTKEY, c.NAME, n.NATIONKEY, n.NAME, r.REGIONKEY, r.NAME,  y1, m1
order by c.CUSTKEY, c1 desc, s1 desc, m1 desc;

# 方法二：
select t1.*, row_number() over (order by  s1 desc, c1 desc, m1 desc)
from (select c.CUSTKEY, c.NAME as cn, n.NATIONKEY, n.NAME as nn, r.REGIONKEY, r.NAME as rn,
             sum(o.TOTALPRICE) as s1, count(*) as c1,
             year(o.ORDERDATE), month(o.ORDERDATE) as m1
      from customer as c
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      inner join nation as n on n.NATIONKEY=c.NATIONKEY
      inner join  region as r on r.REGIONKEY=n.REGIONKEY
      group by c.CUSTKEY, c.NAME, n.NATIONKEY, n.NAME, r.REGIONKEY, r.NAME, year(o.ORDERDATE),month(o.ORDERDATE)) as t1





# 3、请根据dws层表fact_orders表，
#    再联合dwd.dim_nation计算出某年每个国家的消费额中中位数,存入MySQL数据库shtd_store的nationmedian表（表结构如下）中，
#    然后在Linux的MySQL命令行中根据订单总数、消费总额、国家表主键三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#        字段	    类型	中文含义	备注
#       nationkey	int	国家表主键
#       nationname	text	国家名称
#       nationmedianconsumption	double	该国家内客单价	该国家已购买产品的金额的中位数
#       allnationmedianconsumption	double	所有国家订单的中位数	所有国家已购买的产品的金额的中位数
-- 某年每个国家的消费额中中位数
select n.NATIONKEY, n.NAME, sum(o.TOTALPRICE) as s1,
from nation as n
inner join customer as c on n.NATIONKEY=c.CUSTKEY
inner join orders as o on c.CUSTKEY=o.CUSTKEY
where year(o.ORDERDATE)=1992
group by n.NATIONKEY, n.NAME






