# 6、将dwd层dim_customer_inf、dim_customer_addr、dim_customer_level_inf三个表的数据进行组合，
#   抽离出关键数据保存到dwd.dim_customer_services表中，其中包括的列：
#   customer_id、customer_name、identity_card_no、
#   gender、customer_point、register_time、level_name、customer_money、province、city、address、modified_time，
#   其中对于性别根据表中数据处理为【(M)男、(W)女、未知】，客户地址和modified_time信息取客户地址中最新的一条。执行查询客户表按id升序取10条截图。
select customer_id, customer_name, identity_card_no,
       case
           when gender="M" then "男"
           when gender="W" then "女"
           else "未知"
           end as gender,
       customer_point, register_time, level_name, customer_money, province, city, address, modified_time
from (select *, row_number() over (partition by customer_id order by modified_time desc) as seq
      from (select dci.customer_id, customer_name, identity_card_no,
                   gender, customer_point, register_time, level_name, customer_money, province, city, address, dca.modified_time
            from dwd.dim_customer_inf as dci
            inner join dwd.dim_customer_addr as dca on dci.customer_id = dca.customer_id
            inner join dwd.dim_customer_level_inf as cl on dci.customer_level = cl.customer_level))
where seq = 1




# 1、根据dwd或者dws层表统计每人每天下单的数量和下单的总金额，
#   存入dws层（需自建）的user_consumption_day_aggr表中（表结构如下），
#   然后使用hive cli按照客户主键、订单总金额均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        customer_id	int	客户主键	customer_id
#        customer_name	string	客户名称	customer_name
#        total_amount	double	订单总金额	当天订单总金额
#        total_count	int	订单总数	当天订单总数
#        year	int	年	订单产生的年,为动态分区字段
#        month	int	月	订单产生的月,为动态分区字段
#        day	int	日	订单产生的日,为动态分区字段
select customer_id, customer_name, sum(order_money) as total_amount, count(*) as total_count, year, month, day
from (select distinct order_sn , o.customer_id, customer_name, order_money, year(create_time) as year, month(create_time) as month, day(create_time) as day
      from dwd.fact_order_master as o
               inner join dwd.dim_customer_inf as c on o.customer_id = c.customer_id
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
group by customer_id, customer_name, year, month, day


# 2、根据dwd或者dws层表统计每个城市每月下单的数量和每月下单的总金额（以order_master中的地址为判断依据），
#   并按照province_name，year，month进行分组,按照total_amount逆序排序，形成sequence值，
#   将计算结果存入Hive的dws数据库city_consumption_day_aggr表中（表结构如下），
#   然后使用hive cli根据订单总数、订单总金额均为降序排序，查询出前5条，
#   在查询时对于订单总金额字段将其转为bigint类型（避免用科学计数法展示）；
#        字段	类型	中文含义	备注
#        city_name	string	城市名称
#        province_name	string	省份名称
#        total_amount	double	订单总金额	当月订单总金额
#        total_count	int	订单总数	当月订单总数
#        sequence	int	次序	即当月中该城市消费额在该省中的排名（分组排序）
#        year	int	年	订单产生的年,为动态分区字段
#        month	int	月	订单产生的月,为动态分区字段
select  city as city_name, province as province_name, total_amount, total_count,
       row_number() over (partition by province, year, month order by total_amount desc) as sequence,
       year, month
from (select city, province, sum(order_money) as total_amount, count(*) as total_count, year, month
      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status = "已退款" ) and length(city) <= 8)
      group by city, province, year, month)


# 3、请根据dwd或者dws层表计算出每个城市月平均订单金额和该城市所在省份月平均订单金额相比较结果（“高/低/相同”）,
#   存入ClickHouse数据库shtd_result的cityavgcmpprovince表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据城市平均订单金额、省份平均订单金额均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        cityname	text	城市份名称
#        cityavgconsumption	double	该城市平均订单金额
#        provincename	text	省份名称
#        provinceavgconsumption	double	该省平均订单金额
#        comparison	text	比较结果	城市平均订单金额和该省平均订单金额比较结果，值为：高/低/相同
select city as cityname, cityavgconsumption, province as provincename, provinceavgconsumption,
       case
           when cityavgconsumption > provinceavgconsumption then "高"
           when cityavgconsumption < provinceavgconsumption then "低"
           when cityavgconsumption = provinceavgconsumption then "相同"
           end as comparison
from (select distinct city, avg(order_money) over(partition by province, city, year, month ) as cityavgconsumption ,
             province, avg(order_money) over(partition by province, year, month ) as provinceavgconsumption
      from (select distinct order_sn, city, order_money, province, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))



# 4、请根据dwd或者dws层表计算出每个城市每个月订单金额的中位数和该城市所在省份当月订单金额中位数相比较结果（“高/低/相同”）,
#   存入ClickHouse数据库shtd_result的citymidcmpprovince表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据城市订单金额中位数、省份订单金额中位数均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        cityname	text	城市份名称
#        citymidconsumption	double	该城市订单金额中位数
#        provincename	text	省份名称
#        provincemidconsumption	double	该省订单金额中位数
#        comparison	text	比较结果	城市订单金额中位数和该省订单金额中位数比较结果，值为：高/低/相同
select city as cityname, citymidonsumption, province as provincename, provincemidconsumption,
       case
           when citymidonsumption > provincemidconsumption then "高"
           when citymidonsumption < provincemidconsumption then "低"
           when citymidonsumption = provincemidconsumption then "相同"
           end
from (select distinct  city, percentile_approx(order_money, 0.5) over(partition by province, city, year, month) as citymidonsumption,
             province, percentile_approx(order_money, 0.5) over(partition by province, year, month) as provincemidconsumption
      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))

# 5、请根据dwd或者dws层表来计算每个省份2022年订单金额前3城市，
#   依次存入ClickHouse数据库shtd_result的regiontopthree表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据省份升序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        provincename	text	省份名称
#        citynames	text	城市名称	用,分割显示前三城市的name
#        cityamount	text	省份名称	用,分割显示前三城市的订单金额（需要去除小数部分，使用四舍五入）例如： 3	山东省	青岛市,潍坊市,济南市 	100000,100,10
select  province as provincename,
       concat_ws(',', collect_list(substr(city, -3 ,3))) as citynames,
       concat_ws(',',collect_list(total)) as cityamount
from (select  province, city, total, seq
      from (select province, city, total, row_number() over (partition by province order by total desc) as seq
            from (select province, city, cast(sum(order_money) as decimal(10,0)) as total
                  from (select distinct order_sn, province, city, order_money
                        from dwd.fact_order_master
                        where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
                          and length(city) <= 8 and year(create_time)=2022)
                  group by province, city)) as t1
      where seq < 4)
group by province



# 6、请根据dwd或者dws层的相关表，计算销售量前10的商品，销售额前10的商品，
#   存入ClickHouse数据库shtd_result的topten表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据排名升序排序，查询出前5条;
#        字段	类型	中文含义	备注
#        topquantityid	int	商品id	销售量前10的商品
#        topquantityname	text	商品名称	销售量前10的商品
#        topquantity	int	该商品销售量	销售量前10的商品
#        toppriceid	text	商品id	销售额前10的商品
#        toppricename	text	商品名称	销售额前10的商品
#        topprice	decimal	该商品销售额	销售额前10的商品
#        sequence	int	排名	所属排名，从1到10
select t1.product_id as topquantityid, t1.product_name as topquantityname, topquantity,
       t2.product_id as toppriceid, t2.product_name as toppricename, topprice,
       seq1 as sequence
from (select product_id, product_name, topquantity, seq1
      from (select *, row_number() over (order by topquantity desc) as seq1
            from (select product_id, product_name, sum(product_cnt) as topquantity
                  from dwd.fact_order_detail
                  group by product_id, product_name))
      where seq1 <= 10) as t1
inner join (select product_id, product_name, topprice, seq2
            from (select *, row_number() over (order by topprice desc) as seq2
                  from (select product_id, product_name, sum(product_cnt * product_price) as topprice
                        from dwd.fact_order_detail
                        group by product_id, product_name))
            where seq2 <= 10) as t2
on t1.seq1=t2.seq2


# 7、请根据dwd或者dws层的数据，请计算连续两天下单的用户与已下单用户的占比，
#   将结果存入ClickHouse数据库shtd_result的userrepurchasedrate表中(表结构如下)，
#   然后在Linux的ClickHouse命令行中查询结果数据；
#        字段	类型	中文含义	备注
#        purchaseduser	int	下单人数	已下单人数
#        repurchaseduser	int	连续下单人数	连续两天下单的人数
#        repurchaserate	text	百占比	连续两天下单人数/已下单人数百分比（保留1位小数，四舍五入，不足的补0）例如21.1%，或者32.0%
select(
select count(*)
from (select distinct customer_id, datediff(date, lag(date,1) over (partition by customer_id order by date)) as up
      from (select distinct customer_id, date_format(create_time,'yyyy-MM-dd') as date
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8 )) as t1
where up = 1)
/
(select count(*)
from (select distinct customer_id
      from dwd.fact_order_master
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))



# 8、根据dwd或者dws层的数据，请计算每个省份累计订单量，然后根据每个省份订单量从高到低排列，
#   将结果打印到控制台（使用spark中的show算子，同时需要显示列名）；
#   例如：可以考虑首先生成类似的临时表A：province_name	Amount（订单量）
#        A省	10122
#        B省	301
#        C省	2333333
#   然后生成结果类似如下：其中C省销量最高，排在第一列，A省次之，以此类推。
#        C省	A省	B省
#        23333331	10122	301
select province,count(*) as c
from (select distinct order_sn, province
      from dwd.fact_order_master
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
group by province
order by c desc



# 9、根据dwd或者dws层的相关表，请计算2022年4月26日凌晨0点0分0秒到早上9点59分59秒为止，该时间段每小时的新增订单金额与当天订单总金额累加值，
#   存入ClickHouse数据库shtd_result的accumulateconsumption表中，
#   然后在Linux的ClickHouse命令行中根据订单时间段升序排序，查询出前5条;
#        假如数据为：
#        用户	订单时间	订单金额
#        张三1号	2020-04-26 00:00:10	10
#        李四1号	2020-04-26 00:20:10	5
#        李四2号	2020-04-26 01:21:10	10
#        王五1号	2020-04-26 03:20:10	50
#        计算结果则为：
#        订单时间段	订单新增金额	累加总金额
#        2020-04-26 00	15	15
#        2020-04-26 01	10	25
#        2020-04-26 02	0	25
#        2020-04-26 03	50	75
#        accumulateconsumption表结构如下：
#        字段	类型	中文含义	备注
#        consumptiontime	varchar	消费时间段
#        consumptionadd	double	订单新增金额
#        consumptionacc	double	累加总金额
select date, consumptionadd, sum(consumptionadd) over(order by date) as consumptionacc
from (select date, sum(order_money) as consumptionadd
      from (select distinct order_sn, order_money, date_format(to_timestamp(create_time,"yyyyMMddHHmmss"), "yyyy-MM-dd HH") as date
            from ods.order_master
            where order_sn not in(select order_sn from ods.order_master where order_status="已退款") and length(city) <= 8
              and to_timestamp(create_time,"yyyyMMddHHmmss") >= "2022-04-26 00:00:00"
              and to_timestamp(create_time,"yyyyMMddHHmmss") <= "2022-04-26 09:59:59")
      group by date
      order by date)


# 10、根据dwd层或dws层的相关表，请计算2022年4月26日凌晨0点0分0秒到早上9点59分59秒为止的数据，
#   以5个小时为时间窗口，滑动的步长为1小时，做滑动窗口计算该窗口内订单总金额和订单总量，
#   时间不满5小时不触发计算（即从凌晨5点0分0秒开始触发计算），
#   存入ClickHouse数据库shtd_result的slidewindowconsumption表中，
#   然后在Linux的ClickHouse命令行中根据订单时间段升序排序，查询出前5条，将核心业务代码中的开窗相关代码与MySQL查询结果展示出来。
#        假如数据为：
#        用户	订单时间	订单金额
#        张三1号	2020-04-26 00:00:10	10
#        李四1号	2020-04-26 00:20:10	25
#        李四2号	2020-04-26 01:21:10	10
#        李四2号	2020-04-26 02:21:10	5
#        王五1号	2020-04-26 03:20:10	20
#        李四2号	2020-04-26 04:20:10	10
#        王五2号	2020-04-26 05:10:10	10
#        李四2号	2020-04-26 06:20:10	10
#        赵六2号	2020-04-26 07:10:10	10
#        赵六2号	2020-04-26 08:10:10	10
#        王五2号	2020-04-26 09:11:10	10
#        王五4号	2020-04-26 09:32:10	30
#        计算结果则为：
#        订单时间段	该窗口内订单金额	订单总量	平均每单价格
#        2020-04-26 04	80	6	13.33
#        2020-04-26 05	55	5	11
#        2020-04-26 06	55	5	11
#        2020-04-26 07	60	5	12
#        2020-04-26 08	50	5	10
#        2020-04-26 09	80	6	13.33
#        slidewindowconsumption表结构如下：
#        字段	类型	中文含义	备注
#        consumptiontime	varchar	订单时间段
#        consumptionsum	double	该窗口内的订单总金额
#        consumptioncount	double	订单总数量
#        consumptionavg	double	平均每单价格	上面两个字段相除，四舍五入保留两位小数
select date as consumptiontime,
       sum(zje) over (order by date rows between 4 preceding and 0 following) as  consumptionsum,
       sum(zs) over (order by date rows between 4 preceding and 0 following) as consumptioncount
from (select date, round(sum(order_money),2) as zje, count(*) as zs
      from (select distinct order_sn, order_money, date_format(to_timestamp(create_time,"yyyyMMddHHmmss"), "yyyy-MM-dd HH") as date
            from ods.order_master
            where order_sn not in(select order_sn from ods.order_master where order_status="已退款") and length(city) <= 8
              and to_timestamp(create_time,"yyyyMMddHHmmss") >= "2022-04-26 00:00:00"
              and to_timestamp(create_time,"yyyyMMddHHmmss") <= "2022-04-26 09:59:59")
      group by date
      order by date)


# 11、根据dwd中表计算各个城市下的平均订单总额，并且计算各个城市在该省份下的排名，
#    将结果存入ClickHouse数据库shtd_result的tb_province_city_avg表中（表结构如下），
#    然后在Linux的ClickHouse命令行中根据省份、排名均为降序排序，查询出前10条;结构如下：（3分）
#        字段	类型	中文含义	备注
#        province_name	text	省份名
#        city_name	text	城市名
#        city_avg	Double	城市平均额
#        city_seqence	Int	排名	该城市平均额在当前省份下的排名




# 14、请根据dwd层dim_customer_login_log表统计2022-08-10开始往前三周连续登录的活跃用户个数（同一个用户只计算一次），
#    将结果存入clickhouse的active_users表中，并且将结果截图到报告中，结构如下：
#        字段	类型	中文含义	备注
#        end_date	String	统计接收时间	2022/8/10
#        active_count	Int	活跃用户个数
#        active_interval	String	连续周区间	PS：如果统计时间是2022-09-07，则连续区间值是2022-08-22_2022-09-11
#        （因为不知道具体的意图，这题后续考虑做两个版本）：
#        1）只计算三周
#        2）计算往前每三周计算一次(后期扩展练习考虑，本次不做)





# 2022-03-21之后，连续三周下订单的客户和订单个数，总金额


