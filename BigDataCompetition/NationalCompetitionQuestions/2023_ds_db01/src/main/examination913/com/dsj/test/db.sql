# 注：与订单金额计算相关使用order_money字段，同一个订单无需多次重复计算，需要考虑退款或者取消的订单

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

# 查出退款或者取消的订单的订单编号
select order_sn from dwd.fact_order_master as o where o.order_status = '已退款';
# 过滤掉已退款的订单
select distinct o.customer_id, customer_name, order_money, year(create_time)  as year, month(create_time) as month, day(create_time)   as day
from dwd.fact_order_master as o
inner join dwd.dim_customer_inf as c on o.customer_id = c.customer_id
where order_sn not in (select order_sn from dwd.fact_order_master where order_status = '已退款');
# 每人每天下单的数量和下单的总金额
select customer_id,  customer_name , sum(order_money) total_amount, count(*) total_count, year, month, day
from (select distinct o.customer_id,customer_name, order_money,year(create_time) as year, month(create_time) as month, day(create_time) as day
      from dwd.fact_order_master as o
      inner join dwd.dim_customer_inf as c on o.customer_id = c.customer_id
      where order_sn not in (select order_sn from dwd.fact_order_master where order_status = '已退款')) as t1
group by customer_id, customer_name, year, month, day;
# 存入hive的ods  ===> db.scala
# 使用hive cli按照客户主键、订单总金额均为降序排序，查询出前5条
select * from dws.user_consumption_day_aggr
order by customer_id desc, total_amount desc
limit 5;

# 订单总金额保留两位小数
select customer_id,  customer_name , round(sum(order_money),2) total_amount, count(*) total_count, year, month, day
from (select distinct o.customer_id,customer_name, order_money,year(create_time) as year, month(create_time) as month, day(create_time) as day
      from dwd.fact_order_master as o
      inner join dwd.dim_customer_inf as c on o.customer_id = c.customer_id
      where order_sn not in (select order_sn from dwd.fact_order_master where order_status = '已退款')) as t1
group by customer_id, customer_name, year, month, day;



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

# 查出退款或者取消的订单
select * from dwd.fact_order_master where order_status = "已退款";
# 过滤掉已退款的订单
select distinct province, city, order_money, year(create_time) as year, month(create_time) as month
from dwd.fact_order_master
where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
#统计每个城市每月下单的数量和每月下单的总金额（以order_master中的地址为判断依据）
select
       city as city_name, province as province_name, sum(order_money) as total_amount, count(*) as total_count, year, month
from (select distinct province, city, order_money, year(create_time) as year, month(create_time) as month
      from dwd.fact_order_master
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
group by province, city,year, month
# 并按照province_name，year，month进行分组,按照total_amount逆序排序，形成sequence值
select city_name, province_name, total_amount, total_count,
       row_number() over(partition by province_name, year, month order by total_amount desc ) as sequencesequence,
       year, month
from (select city as city_name, province as province_name, sum(order_money) as total_amount, count(*) as total_count, year, month
      from (select distinct province, city, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where length(city)<=8 and order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
      group by province, city,year, month) as t1;

# 解决科学计数法的问题
select
       city_name, province_name,
       cast(total_amount as decimal(25,10)),
       total_count,
       row_number() over(partition by province_name, year, month order by total_amount desc ) as sequencesequence,
       year, month
from (select city as city_name, province as province_name, sum(order_money) as total_amount, count(*) as total_count, year, month
      from (select distinct province, city, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where length(city)<=8 and order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
      group by province, city,year, month) as t1;
# 结果存入Hive的dws数据库city_consumption_day_aggr表中 ===> db.scala
# 使用hive cli根据订单总数、订单总金额均为降序排序，查询出前5条
select * from dws.city_consumption_day_aggr order by total_count desc, total_amount desc limit 5;



# 3、请根据dwd或者dws层表计算出每个城市月平均订单金额和该城市所在省份月平均订单金额相比较结果（“高/低/相同”）,
#   存入ClickHouse数据库shtd_result的cityavgcmpprovince表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据城市平均订单金额、省份平均订单金额均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        cityname	text	城市份名称
#        cityavgconsumption	double	该城市平均订单金额
#        provincename	text	省份名称
#        provinceavgconsumption	double	该省平均订单金额
#        comparison	text	比较结果	城市平均订单金额和该省平均订单金额比较结果，值为：高/低/相同
# 查出退款或者取消的订单
select order_sn from dwd.fact_order_master where order_status="已退款"
# 过滤掉已退款的数据并去除重复的数据
select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
from dwd.fact_order_master as o
where length(city) <= 8 and order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
# 计算出每个城市月平均订单金额和该城市所在省份月平均订单金额相比较结果（“高/低/相同”）
select city, province,
       avg(order_money) over (partition by province, city,month) as cityavgconsumption,
       avg(order_money) over (partition by province, month)       as provinceavgconsumption
from (select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
      from dwd.fact_order_master as o
      where length(city) <= 8 and order_sn not in (select order_sn from dwd.fact_order_master where order_status = "已退款"))
# 比较结果
select city as cityname, cityavgconsumption, province as provincename, provinceavgconsumption,
       case
           when cityavgconsumption > provinceavgconsumption then '高'
           when cityavgconsumption < provinceavgconsumption then '低'
           when cityavgconsumption = provinceavgconsumption then '相同' end as comparison
from (select distinct  city, province,
             avg(order_money) over (partition by province, city, month) as cityavgconsumption,
             avg(order_money) over (partition by province, month)       as provinceavgconsumption
      from (select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master as o
            where length(city) <= 8 and order_sn not in (select order_sn from dwd.fact_order_master where order_status = "已退款"))) as t1




# 4、请根据dwd或者dws层表计算出每个城市每个月订单金额的中位数和该城市所在省份当月订单金额中位数相比较结果（“高/低/相同”）,
#   存入ClickHouse数据库shtd_result的citymidcmpprovince表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据城市订单金额中位数、省份订单金额中位数均为降序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        cityname	text	城市份名称
#        citymidconsumption	double	该城市订单金额中位数
#        provincename	text	省份名称
#        provincemidconsumption	double	该省订单金额中位数
#        comparison	text	比较结果	城市订单金额中位数和该省订单金额中位数比较结果，值为：高/低/相同
# 查出退款或者取消的订单
select order_sn from dwd.fact_odser_master where order_status="已退款"
# 过滤掉已退款的订单，并去重
select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
from dwd.fact_order_master as o
where length(city) <= 8 and order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
# 计算出每个城市每个月订单金额的中位数和该城市所在省份当月订单金额中位数相比较结果（“高/低/相同”）
select city, province,
       percentile_approx(order_money) over (partition by province, city,month) as cityavgconsumption,
       percentile_approx(order_money) over (partition by province, month)       as provinceavgconsumption
from (select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
      from dwd.fact_order_master as o
      where length(city) <= 8 and order_sn not in (select order_sn from dwd.fact_order_master where order_status = "已退款"))
# 比较结果
select city as cityname, cityavgconsumption, province as provincename, provinceavgconsumption,
       case
           when cityavgconsumption > provinceavgconsumption then '高'
           when cityavgconsumption < provinceavgconsumption then '低'
           when cityavgconsumption = provinceavgconsumption then '相同' end as comparison
from (select distinct  city, province,
                       percentile_approx(order_money) over (partition by province, city, month) as cityavgconsumption,
                       percentile_approx(order_money) over (partition by province, month)       as provinceavgconsumption
      from (select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master as o
            where length(city) <= 8 and order_sn not in (select order_sn from dwd.fact_order_master where order_status = "已退款"))) as t1


# 5、请根据dwd或者dws层表来计算每个省份2022年订单金额前3城市，
#   依次存入ClickHouse数据库shtd_result的regiontopthree表中（表结构如下），
#   然后在Linux的ClickHouse命令行中根据省份升序排序，查询出前5条；
#        字段	类型	中文含义	备注
#        provincename	text	省份名称
#        citynames	text	城市名称	用,分割显示前三城市的name
#        cityamount	text	省份名称	用,分割显示前三城市的订单金额（需要去除小数部分，使用四舍五入）例如： 3	山东省	青岛市,潍坊市,济南市 	100000,100,10
# 查出退款或者取消的订单
select order_sn from dwd.fact_order_master where order_status="已退款"
# 过滤掉已退款的订单并去重
select distinct  city, province, order_money, year(create_time) as year, month(create_time) as month
from dwd.fact_order_master
where  length(city) <= 8 and not order_sn in(select order_sn from dwd.fact_order_master where order_status="已退款")
#算每个省份2022年订单金额前3城市
select distinct province as provincename, city, round(sum(order_money)), row_number() over (partition by province order by round(sum(order_money)) desc ) as num
from (select distinct  city, province, order_money, year(create_time) as year, month(create_time) as month
      from dwd.fact_order_master
      where  length(city) <= 8 and not order_sn in(select order_sn from dwd.fact_order_master where order_status="已退款")) as t1
where year="2022"
group by province, city;

# 订单金额前3城市
# collect_set 函数，有两个作用，第一个是去重，去除group by后的重复元素
# 与contact_ws 结合使用就是将这些元素以逗号分隔形成字符串
select province, concat_ws(',' , collect_set(substr(city,-3,3))) as citynames,
       concat_ws(',' , collect_set(totl)) as cityamount
from (select distinct province , city, round(sum(order_money)) as totl, row_number() over (partition by province order by round(sum(order_money)) desc ) as num
      from (select distinct  city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where  length(city) <= 8 and not order_sn in(select order_sn from dwd.fact_order_master where order_status="已退款")) as t1
      where year="2022"
      group by province, city) as t2
where num<=3
group by province
# 解决科学计数法的问题
select province,
       concat_ws(',', collect_set(substr(city, -3, 3))) as citynames,
       concat_ws(',', collect_set(totl)) as cityamount
from (select distinct province, city,
                      cast(bround(sum(order_money)) as decimal(25, 0)) as totl,
                      row_number() over (partition by province order by round(sum(order_money)) desc ) as num
      from (select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
            from dwd.fact_order_master
            where length(city) <= 8 and not order_sn in (select order_sn from dwd.fact_order_master where order_status = "已退款")) as t1
      where year = "2022"
      group by province, city) as t2
where num <= 3
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
# 计算销售量前10的商品
select product_id, product_name,
       sum(product_cnt),
       row_number() over (partition by  product_name order by  sum(product_cnt) desc) num1,
       sum((product_price * product_cnt)) ,
       row_number() over (partition by  product_name order by sum((product_price * product_cnt)) desc) num2
from dwd.fact_order_detail
group by product_id, product_name
# 前10
select  product_id as topquantityid, product_name as topquantityname, topquantity,  topprice
from (select product_id, product_name,
             sum(product_cnt) as topquantity ,
             row_number() over (partition by  product_name order by  sum(product_cnt) desc) num1,
             sum((product_price * product_cnt)) as topprice ,
             row_number() over (partition by  product_name order by sum((product_price * product_cnt)) desc) num2
      from dwd.fact_order_detail
      group by product_id, product_name) as t1
where num1 >= 10 and num2 >= 10

# 计算销售量前10的商品，销售额前10的商品
select t2.topquantityid,  t2.topquantityname, topquantity, t3.toppriceid,  t3.toppricename, topprice, num1
from (select  product_id as topquantityid, product_name as topquantityname, topquantity,num1
      from (select product_id, product_name,
                   sum(product_cnt) as topquantity ,
                   row_number() over (partition by product_id, product_name order by  sum(product_cnt) desc) num1
            from dwd.fact_order_detail
            group by product_id, product_name) as t1
      where num1 <= 10) as t2
inner join (select  product_id as toppriceid, product_name as toppricename,  topprice,num2
            from (select product_id, product_name,
                         sum((product_price * product_cnt)) as topprice ,
                         row_number() over (partition by product_id, product_name order by sum((product_price * product_cnt)) desc) num2
                  from dwd.fact_order_detail
                  group by product_id, product_name) as t1
            where num2 <= 10) as t3
on t2.num1=t3.num2;




# 7、请根据dwd或者dws层的数据，请计算连续两天下单的用户与已下单用户的占比，
#   将结果存入ClickHouse数据库shtd_result的userrepurchasedrate表中(表结构如下)，
#   然后在Linux的ClickHouse命令行中查询结果数据；
#        字段	类型	中文含义	备注
#        purchaseduser	int	下单人数	已下单人数
#        repurchaseduser	int	连续下单人数	连续两天下单的人数
#        repurchaserate	text	百占比	连续两天下单人数/已下单人数百分比（保留1位小数，四舍五入，不足的补0）例如21.1%，或者32.0%
# 查出退款或者取消的订单的订单编号
select order_sn from dwd.fact_order_master as o where o.order_status = '已退款';
# 过滤掉已退款的订单，并查出一天下单的用户
select  order_id , order_sn, customer_id, year(create_time) year, month(create_time) as month, day(create_time) as day
from dwd.fact_order_master
where order_sn not in(select order_sn from dwd.fact_order_master where order_status = '已退款')
  and  order_sn in(select order_sn from dwd.fact_order_master where order_status = '已下单')
group by customer_id, year, month, day
# 计算连续两天下单的用户
select *
from (select  order_sn, customer_id, year(create_time) year, month(create_time) as month, day(create_time) as day
      from dwd.fact_order_master
      where order_sn not in(select order_sn from dwd.fact_order_master where order_status = '已退款')
        and  order_sn in(select order_sn from dwd.fact_order_master where order_status = '已下单')
      group by customer_id, year, month, day) as t1
inner join 






# 8、根据dwd或者dws层的数据，请计算每个省份累计订单量，然后根据每个省份订单量从高到低排列，
#   将结果打印到控制台（使用spark中的show算子，同时需要显示列名）；
#   例如：可以考虑首先生成类似的临时表A：province_name	Amount（订单量）
#        A省	10122
#        B省	301
#        C省	2333333
# 然后生成结果类似如下：其中C省销量最高，排在第一列，A省次之，以此类推。
# C省	A省	B省
# 23333331	10122	301


# 9、根据dwd或者dws层的相关表，请计算2022年4月26日凌晨0点0分0秒到早上9点59分59秒为止，
#   该时间段每小时的新增订单金额与当天订单总金额累加值，
#   存入ClickHouse数据库shtd_result的accumulateconsumption表中，
#   然后在Linux的ClickHouse命令行中根据订单时间段升序排序，查询出前5条;
# 假如数据为：
# 用户	订单时间	订单金额
# 张三1号	2020-04-26 00:00:10	10
# 李四1号	2020-04-26 00:20:10	5
# 李四2号	2020-04-26 01:21:10	10
# 王五1号	2020-04-26 03:20:10	50
# 计算结果则为：
# 订单时间段	订单新增金额	累加总金额
# 2020-04-26 00	15	15
# 2020-04-26 01	10	25
# 2020-04-26 02	0	25
# 2020-04-26 03	50	75
#
# accumulateconsumption表结构如下：
# 字段	类型	中文含义	备注
# consumptiontime	varchar	消费时间段
# consumptionadd	double	订单新增金额
# consumptionacc	double	累加总金额


# 10、根据dwd层或dws层的相关表，请计算2022年4月26日凌晨0点0分0秒到早上9点59分59秒为止的数据，
#   以5个小时为时间窗口，滑动的步长为1小时，做滑动窗口计算该窗口内订单总金额和订单总量，
#   时间不满5小时不触发计算（即从凌晨5点0分0秒开始触发计算），
#   存入ClickHouse数据库shtd_result的slidewindowconsumption表中，
#   然后在Linux的ClickHouse命令行中根据订单时间段升序排序，查询出前5条，将核心业务代码中的开窗相关代码与MySQL查询结果展示出来。
# 假如数据为：
# 用户	订单时间	订单金额
# 张三1号	2020-04-26 00:00:10	10
# 李四1号	2020-04-26 00:20:10	25
# 李四2号	2020-04-26 01:21:10	10
# 李四2号	2020-04-26 02:21:10	5
# 王五1号	2020-04-26 03:20:10	20
# 李四2号	2020-04-26 04:20:10	10
# 王五2号	2020-04-26 05:10:10	10
# 李四2号	2020-04-26 06:20:10	10
# 赵六2号	2020-04-26 07:10:10	10
# 赵六2号	2020-04-26 08:10:10	10
# 王五2号	2020-04-26 09:11:10	10
# 王五4号	2020-04-26 09:32:10	30
# 计算结果则为：
# 订单时间段	该窗口内订单金额	订单总量	平均每单价格
# 2020-04-26 04	80	6	13.33
# 2020-04-26 05	55	5	11
# 2020-04-26 06	55	5	11
# 2020-04-26 07	60	5	12
# 2020-04-26 08	50	5	10
# 2020-04-26 09	80	6	13.33
#
#
# slidewindowconsumption表结构如下：
# 字段	类型	中文含义	备注
# consumptiontime	varchar	订单时间段
# consumptionsum	double	该窗口内的订单总金额
# consumptioncount	double	订单总数量
# consumptionavg	double	平均每单价格	上面两个字段相除，四舍五入保留两位小数











