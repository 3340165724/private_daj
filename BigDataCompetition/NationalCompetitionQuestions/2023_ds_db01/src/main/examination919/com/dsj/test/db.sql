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
select  customer_id, customer_name, sum(order_money) as total_amount, count(*) as total_count, year, month, day
from (select distinct order_sn, o.customer_id, c.customer_name,order_money, year(create_time) as year, month(create_time) as month, day(create_time) as day
      from dwd.fact_order_master as o
      inner join dwd.dim_customer_inf as c  on o.customer_id = c.customer_id
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8 ) as t1
group by  customer_id, customer_name, year, month, day



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
select city_name, province_name, cast(total_amount as decimal(25,2)) , total_count,
       row_number() over (partition by province_name, year, month order by  total_amount) as sequence, year, month
from (select  city as city_name, province as province_name, sum(order_money) as total_amount, count(*) as total_count, year, month
      from (select distinct order_sn, city, province, year(create_time) as year, month(create_time) as month, order_money
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
      group by city, province,  year, month)

# 3、请根据dwd或者dws层表计算出每个城市月平均订单金额和该城市所在省份月平均订单金额相比较结果（“高/低/相同”）,
#   存入ClickHouse数据库shtd_result的cityavgcmpprovince表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据城市平均订单金额、省份平均订单金额均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        cityname	text	城市份名称
#        cityavgconsumption	double	该城市平均订单金额
#        provincename	text	省份名称
#        provinceavgconsumption	double	该省平均订单金额
#        comparison	text	比较结果	城市平均订单金额和该省平均订单金额比较结果，值为：高/低/相同
select  cityname,cityavgconsumption,provincename,provinceavgconsumption,
       case
           when cityavgconsumption > provinceavgconsumption then "高"
           when cityavgconsumption < provinceavgconsumption then "低"
           when cityavgconsumption = provinceavgconsumption then "相同"
           end as comparison
from (select distinct city as cityname, province as provincename,
             avg(order_money) over(partition by province, city, year, month ) as cityavgconsumption,
             avg(order_money) over(partition by province, year, month) as provinceavgconsumption
      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status= "已退款") and length(city) <=8 ))


# 4、请根据dwd或者dws层表计算出每个城市每个月订单金额的中位数和该城市所在省份当月订单金额中位数相比较结果（“高/低/相同”）,
#   存入ClickHouse数据库shtd_result的citymidcmpprovince表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据城市订单金额中位数、省份订单金额中位数均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        cityname	text	城市份名称
#        citymidconsumption	double	该城市订单金额中位数
#        provincename	text	省份名称
#        provincemidconsumption	double	该省订单金额中位数
#        comparison	text	比较结果	城市订单金额中位数和该省订单金额中位数比较结果，值为：高/低/相同
select city as cityname, citymidconsumption, province as provincename, provincemidconsumption,
       case
           when citymidconsumption > provincemidconsumption then '高'
           when citymidconsumption < provincemidconsumption then '低'
           when citymidconsumption = provincemidconsumption then '相同' end as comparison
from (select distinct city, province,
                      percentile_approx(order_money,0.5) over(partition by province,city, year, month ) as citymidconsumption,
                      percentile_approx(order_money,0.5) over(partition by province, year, month) as provincemidconsumption
      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))


# 5、请根据dwd或者dws层表来计算每22年订单金额前3城市，
#   依次存入ClickHouse数据库sht个省份20d_result的regiontopthree表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据省份升序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        provincename	text	省份名称
#        citynames	text	城市名称	用,分割显示前三城市的name
#        cityamount	text	省份名称	用,分割显示前三城市的订单金额（需要去除小数部分，使用四舍五入）例如： 3	山东省	青岛市,潍坊市,济南市 	100000,100,10
select province as provincename,
       concat_ws(',',collect_list(substr(city,-3,3))) as citynames,
       concat_ws(',',collect_list(total)) as cityamount
from (select province, city, total, seq
      from (select province, city, total, row_number() over (partition by province order by total desc) as seq
            from (select province, city, cast(sum(order_money) as decimal(10,0) ) as total
                  from (select distinct order_sn, province, city, order_money
                        from dwd.fact_order_master
                        where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
                          and length(city) <= 8 and year(create_time) = 2022)
                  group by province, city) ) as t1
      where seq < 4)
group by province

# 方法二
select province,
       concat_ws(',',collect_set(substr(city,-3,3))) as citynames,
       concat_ws(',',collect_set(total)) as cityamount
from (select province, city, total, seq
      from (select province, city, total, row_number() over (partition by province order by total desc) as seq
            from (select province, city, cast(sum(order_money) as decimal(10,0) ) as total
                  from (select distinct order_sn, province, city, order_money
                        from dwd.fact_order_master
                        where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
                          and length(city) <= 8 and year(create_time) = 2022)
                  group by province, city) ) as t1
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
select topquantityid, topquantityname, topquantity, toppriceid, toppricename, topprice, seq1
from (select product_id as topquantityid, product_name as topquantityname, topquantity, seq1
      from (select *, row_number() over (order by topquantity desc ) as seq1
            from (select  product_id, product_name, sum(product_cnt) as topquantity
                  from dwd.fact_order_detail
                  group by product_id, product_name))
      where seq1 <= 10) as t1
inner join (select product_id as toppriceid, product_name as toppricename, topprice, seq2
            from (select *, row_number() over (order by topprice desc ) as seq2
                  from (select  product_id, product_name, sum(product_cnt * product_price) as topprice
                        from dwd.fact_order_detail
                        group by product_id, product_name))
            where seq2 <= 10) as t2
on t1.seq1 = t2.seq2






