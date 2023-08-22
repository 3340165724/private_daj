# 1、统计各个优先级别（字段ORDERPRIORITY）下有效（状态为O的订单）的订单总额，总个数，平均金额，按总额降序排列输出。
select sum(TOTALPRICE) as "订单总额", count(*) as "总个数", avg(TOTALPRICE) as "平均金额"
from orders
where ORDERSTATUS="O"
group by ORDERPRIORITY
order by sum(TOTALPRICE) desc;


# 2、统计下单总金额最多的前三名客户（客户id、客户名、订单总额、订单个数），按订单总额降序显示
select  c.CUSTKEY, c.NAME, sum(o.TOTALPRICE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by  c.CUSTKEY, c.NAME
order by  sum(o.TOTALPRICE) desc
limit 3;


# 3、统计各个国家下的订单总额，按降序排序显示（国家id、国家名、订单总额）
select n.NATIONKEY, n.NAME, sum(o.TOTALPRICE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
inner join nation as n on c.NATIONKEY=n.NATIONKEY
group by n.NATIONKEY, n.NAME
order by sum(o.TOTALPRICE) desc;


# 4、统计各个地区下的国家个数，按个数降序显示（地区id、地区名称、个数）
select n.NATIONKEY, n.NAME, count(*)
from nation as n
inner join region as r on r.REGIONKEY=n.REGIONKEY
group by n.NATIONKEY, n.NAME
order by count(*) desc;


# 5、统计各个地区在各个年份订单的总数、总金额，按地区下订单总数降序显示（地区id、地区名、年份、订单个数、订单总额）
select r.REGIONKEY, r.NAME, year(o.ORDERDATE), count(*) , sum(o.TOTALPRICE)
from customer as c
inner join orders as o on c.CUSTKEY=o.CUSTKEY
inner join nation as n on c.NATIONKEY=n.NATIONKEY
inner join region as r on n.REGIONKEY=r.REGIONKEY
group by r.REGIONKEY, r.NAME, year(o.ORDERDATE)
order by count(*) desc;


# 6、统计各个国家在每年各个月份下单的总数和总金额
select n.NATIONKEY, n.NAME, year(o.ORDERDATE), month(o.ORDERDATE), count(*), sum(o.TOTALPRICE)
from nation as n
inner join customer as c on n.NATIONKEY=c.NATIONKEY
inner join orders as o on c.CUSTKEY=o.CUSTKEY
group by  n.NATIONKEY, n.NAME, year(o.ORDERDATE), month(o.ORDERDATE)


# 7、统计连续两个月下订单的客户和连续两个月下订单的总个数、总金额，按个数降序显示（客户id、客户名、年、月、总个数、总金额）
-- 统计各个客户在每月下单的总个数、总金额
select c.CUSTKEY, c.NAME, YEAR(o.ORDERDATE), MONTH(o.ORDERDATE), COUNT(*), SUM(o.TOTALPRICE)
from customer as c
inner join  orders as o  on o.CUSTKEY = c.CUSTKEY
group by  c.CUSTKEY, c.NAME, YEAR(o.ORDERDATE) , MONTH(o.ORDERDATE)
order by c.CUSTKEY, c.NAME
-- 续两个月下订单的客户和连续两个月下订单的总个数、总金额
select  t1.CUSTKEY, t1.NAME,
        concat(concat(t1.y1,if(t1.m1<10,concat('0',t1.m1),t1.m1)),"_",concat(t2.y2,if(t2.m2<10,concat('0',t2.m2),t2.m2))),
       t1.c1+t2.c2, round(t1.s1+t2.s2,2)
from(select c.CUSTKEY, c.NAME, YEAR(o.ORDERDATE) as y1, MONTH(o.ORDERDATE) as m1, COUNT(*) as c1, SUM(o.TOTALPRICE) as s1
     from customer as c
     inner join  orders as o  on o.CUSTKEY = c.CUSTKEY
     group by  c.CUSTKEY, c.NAME, YEAR(o.ORDERDATE) , MONTH(o.ORDERDATE)) as t1
inner join (select c.CUSTKEY, c.NAME, YEAR(o.ORDERDATE) as y2, MONTH(o.ORDERDATE) as m2, COUNT(*) as c2, SUM(o.TOTALPRICE) as s2
            from customer as c
            inner join  orders as o  on o.CUSTKEY = c.CUSTKEY
            group by  c.CUSTKEY, c.NAME, YEAR(o.ORDERDATE) , MONTH(o.ORDERDATE)) as t2
on t1.CUSTKEY=t2.CUSTKEY and ((y1=y2 and m1 = m2-1) or (y1=y2-1 and t1.m1=12 and t2.m2=01))


