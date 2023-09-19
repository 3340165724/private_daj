# 6、将dwd层dim_customer_inf、dim_customer_addr、dim_customer_level_inf三个表的数据进行组合，
#    抽离出关键数据保存到dwd.dim_customer_services表中，其中包括的列：
#    customer_id、customer_name、identity_card_no、gender、customer_point、register_time、
#    level_name、customer_money、province、city、address、modified_time，
#    其中对于性别根据表中数据处理为【(M)男、(W)女、未知】，客户地址和modified_time信息取客户地址中最新的一条。
#    执行查询客户表按id升序取10条截图。
select  customer_id, customer_name, identity_card_no,
       case
           when gender="M" then "男"
           when gender="W" then "女"
           else "未知"
           end,
       customer_point, register_time,level_name, customer_money, province, city, address, modified_time
from (select *, row_number() over (partition by customer_id order by modified_time) as seq
      from (select ci.customer_id, customer_name, identity_card_no, gender, customer_point, register_time,
                   level_name, customer_money, province, city, address, ca.modified_time
            from dwd.dim_customer_inf as ci
            inner join dwd.dim_customer_addr as ca on ci.customer_id = ca.customer_id
            inner join dwd.dim_customer_level_inf as cli on ci.customer_level = cli.customer_level))
where seq=1
# 执行查询客户表按id升序取10条截图
select * from dwd.dim_customer_services
order by customer_id
limit 10;


# 注意：指标计算需要考虑订单为已退款和订单表中重复数据问题；
# 需注意dwd所有的维表取最新的分区。
#
# 1、根据dwd中表计算各个城市下的平均订单总额，并且计算各个城市在该省份下的排名，
#    将结果存入ClickHouse数据库shtd_result的tb_province_city_avg表中（表结构如下），
#    然后在Linux的ClickHouse命令行中根据省份、排名均为降序排序，查询出前10条;结构如下：（3分）
#        字段	类型	中文含义	备注
#        province_name	text	省份名
#        city_name	text	城市名
#        city_avg	Double	城市平均额
#        city_seqence	Int	排名	该城市平均额在当前省份下的排名
# 查出已退款的订单
select order_sn from dwd.fact_order_master where order_status="已退款"
# 过滤掉已退款的订单
select distinct  province, city
from dwd.fact_order_master
where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8
# 计算各个城市下的平均订单总额
select province as province_name, city as city_name, avg(order_money) as city_avg
from (select distinct  province, city, order_money
      from dwd.fact_order_master
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8)
group by province_name, city_name
# 该城市平均额在当前省份下的排名
select *, row_number() over (partition by province_name order by city_avg)
from (select province as province_name, city as city_name, avg(order_money) as city_avg
      from (select distinct  province, city, order_money
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8)
      group by province_name, city_name)


# 2、请根据dwd中表计算各个省份下被卖出商品最多的top3，
#    依次存入ClickHouse数据库shtd_result的tb_province_product_top3表中（表结构如下），
#    然后在Linux的ClickHouse命令行中根据省份、排名升序排序，查询出前5条；结构如下：（3分）
#        字段	类型	中文含义	备注
#        provincename	text	省份名称
#        product_id	text	商品id
#        product_name	text	商品名称
#        sell_num	Int	售出数量
#        sell_seqence	int	排名	省份内的排名
# 查看已退款的订单
select order_sn from dwd.fact_order_master where order_status="已退款"
# 过滤掉已退款的订单
select province, customer_id
from dwd.fact_order_master
where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
# 计算各个省份下被卖出商品最多的top3
select *
from dwd.fact_order_master as m
inner join dwd.fact_order_detail as d on m.









# 3、请根据dwd或者dws层的数据，计算连续两个月下单且保持增长的用户，
#    将结果存入ClickHouse数据库shtd_result的tb_buymonths_infos表中(表结构如下)，
#    然后在Linux的ClickHouse命令行中根据客户id、月份升序查询结果数据；结构如下：（3分）
#        字段	类型	中文含义	备注
#        customer_id	int	用户id
#        customer_name	Text	用户名
#        continuous_months	text	连续月份组合	连续月份组合，如202203_202204如果连续三个月则计算两次，如5、6、7三月都购买了，则5、6组合一次，6、7组合一次
#        continuous_count	int	购买总次数	两个月的购买次数
#        continuous_sum	Double	购买总额	两个月的购买总额
# 查出已退款的订单
select order_sn from dwd.fact_order_master where order_status="已退款";
# 过滤掉已退款的订单
select distinct o.customer_id, c.customer_name,order_money
from dwd.fact_order_master as o
inner join dwd.dim_customer_inf  as c on o.customer_id=c.customer_id
where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
# 连续两个月下单
select  customer_id, customer_name
from (select distinct o.customer_id, c.customer_name, order_money, date_format(create_time,"yyyy-MM") as date
      from dwd.fact_order_master as o
      inner join dwd.dim_customer_inf  as c on o.customer_id=c.customer_id
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
# 一个月下单的
select customer_id, customer_name, count(*) as c1, sum(order_money) as s1, y1,m1
from (select distinct o.customer_id, c.customer_name, order_money, year(create_time) y1, month(create_time) m1
      from dwd.fact_order_master as o
      inner join dwd.dim_customer_inf  as c on o.customer_id=c.customer_id
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
group by customer_id, customer_name, y1,m1

# 连续两月
select customer_id, customer_name,
       concat(concat(y1,if(m1<10,concat("0",m1),m1)), "_",concat(y2,if(m2<10,concat("0",m2),m2))) as continuous_months,
       c1 + c2 as continuous_count, s1 + s2 as continuous_sum
from (select customer_id, customer_name, count(*) as c1, sum(order_money) as s1, y1,m1
      from (select distinct o.customer_id, c.customer_name, order_money, year(create_time) y1, month(create_time) m1
            from dwd.fact_order_master as o
                     inner join dwd.dim_customer_inf  as c on o.customer_id=c.customer_id
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
      group by customer_id, customer_name, y1,m1) as t1
inner join (select customer_id, customer_name, count(*) as c2, sum(order_money) as s2, y2,m2
            from (select distinct o.customer_id, c.customer_name, order_money, year(create_time) y2, month(create_time) m2
                  from dwd.fact_order_master as o
                           inner join dwd.dim_customer_inf  as c on o.customer_id=c.customer_id
                  where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
            group by customer_id, customer_name, y2,m2) as t2
on t1.customer_id = t2.customer_id and (y1=y2 and m1=m2-1) or (y1=y2-1 and m1=12 and m2=01)




# 4、请根据dwd层dim_customer_login_log表统计2022-08-10开始往前三周连续登录的活跃用户个数（同一个用户只计算一次），
#    将结果存入clickhouse的active_users表中，并且将结果截图到报告中，结构如下：
#        字段	类型	中文含义	备注
#        end_date	String	统计接收时间	2022/8/10
#        active_count	Int	活跃用户个数
#        active_interval	String	连续周区间	PS：如果统计时间是2022-09-07，则连续区间值是2022-08-22_2022-09-11
#        （因为不知道具体的意图，这题后续考虑做两个版本）：
#        1）只计算三周
#        2）计算往前每三周计算一次(后期扩展练习考虑，本次不做)