# 指标计算

use shtd_store;

# 1、根据dwd层表统计每人每天下单的数量和下单的总金额，
#    存入dws层的customer_consumption_day_aggr表（表结构如下）中，
#    然后在hive cli中按照cust_key，totalconsumption, totalorder三列均逆序排序的方式，查询出前5条，
#    将SQL语句与执行结果截图粘贴至对应报告中;
#        字段	    类型	 中文含义	备注
#       cust_key	int	客户key
#       cust_name	string	客户名称
#       totalconsumption	double	消费总额	当月消费订单总额
#       totalorder	int	订单总数	当月订单总额select c.CUSTKEY, c.NAME, sum(o.TOTALPRICE), count(o.TOTALPRICE),
#        year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE)
# from customer as c
# inner join orders o on c.CUSTKEY=o.CUSTKEY
# group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE)
#       year	int	年	订单产生的年
#       month	int	月	订单产生的月
#       day	int	日	订单产生的日
select c.CUSTKEY, c.NAME, sum(o.TOTALPRICE), count(o.TOTALPRICE),
       year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE)
from customer as c
         inner join orders o on c.CUSTKEY=o.CUSTKEY
group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE), day(o.ORDERDATE)



# 2、根据dws层表customer_consumption_day_aggr表，
# 再联合dwd.dim_region,dwd.dim_nation统计每人每个月下单的数量和下单的总金额，
# 并按照cust_key，totalconsumption，totalorder，month进行分组逆序排序（以cust_key为分组条件），
# 将计算结果存入MySQL数据库shtd_store的nationeverymonth表（表结构如下）中，
# 然后在Linux的MySQL命令行中根据订单总数、消费总额、国家表主键三列均逆序排序的方式，查询出前5条，
# 将SQL语句与执行结果截图粘贴至对应报告中;
#       字段	       类型	    中文含义	备注
#       cust_key	int 	客户key
#       cust_name	string	客户名称
#       nationkey	int 	国家表主键
#       nationname	text	国家名称
#       regionkey	int	    地区表主键
#       regionname	text	地区名称
#       totalconsumption double	消费总额	当月消费订单总额
#       totalorder	int 	订单总数	当月订单总额
#       year	    int	年	订单产生的年
#       month	   int	月	订单产生的月
#       sequence	Int	次序
select c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE),sum(o.TOTALPRICE), count(o.TOTALPRICE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
inner join nation as n on c.NATIONKEY=n.NATIONKEY
inner join region as r on n.REGIONKEY=r.REGIONKEY
group by c.CUSTKEY, c.NAME, year(o.ORDERDATE), month(o.ORDERDATE)

select t1.* , ()
from (select c.CUSTKEY as ck, c.NAME as cn, n.NATIONKEY as nk, n.NAME as nn, r.REGIONKEY as rk, r.NAME as rn,
             sum(o.TOTALPRICE) as s1, count(o.TOTALPRICE) as c1,
             year(o.ORDERDATE) as y1, month(o.ORDERDATE) as m1
      from customer as c
      inner join orders as o on c.CUSTKEY=o.CUSTKEY
      inner join nation as n on c.NATIONKEY=n.NATIONKEY
      inner join region as r on n.REGIONKEY=r.REGIONKEY
      group by c.CUSTKEY, c.NAME,  n.NATIONKEY, n.NAME, r.REGIONKEY, r.NAME, year(o.ORDERDATE), month(o.ORDERDATE)) as t1





# 3、请根据dws层表customer_consumption_day_aggr表，
# 再联合dwd.dim_region,dwd.dim_nation计算出某年每个国家的平均消费额和所有国家平均消费额相比较结果（“高/低/相同”）,
# 存入MySQL数据库shtd_store的nationavgcmp表（表结构如下）中，
# 然后在Linux的MySQL命令行中根据国家表主键升序、人均消费额升序排序的方式，查询出前5条，将SQL语句与执行结果截图粘贴至对应报告中;
#       字段	        类型	中文含义	备注
#       nationkey	int	国家表主键
#       nationname	text	国家名称
#       nationavgconsumption	double	该国家内客单价	该国家已购买产品的人均消费额
#       allnationavgconsumption	double	所有国家内客单价	所有国家已购买的产品的人均消费额
#       comparison	string	比较结果	国家内人均和所有国家人均相比结果有：高/低/相同