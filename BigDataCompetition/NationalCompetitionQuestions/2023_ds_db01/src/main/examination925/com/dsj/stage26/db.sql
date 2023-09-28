select customer_id, customer_name, identity_card_no,
       case
           when gender="M" then "男"
           when gender="W" then "女"
           else "未知"
           end as gender,
       customer_point, register_time, level_name, customer_money, province, city, address, modified_time
from (select *, row_number() over (partition by customer_id order by modified_time desc) as seq
      from (select distinct dci.customer_id, customer_name, identity_card_no, gender, customer_point, register_time, level_name, customer_money, province, city, address, dca.modified_time
            from dwd.dim_customer_inf as dci
                     inner join  dwd.dim_customer_addr as dca  on dci.customer_id= dca.customer_id
                     inner join dwd.dim_customer_level_inf as dcli  on dci.customer_level = dcli.customer_level))
where seq=1


# 计算各个城市下的平均订单总额，并且计算各个城市在该省份下的排名
select  province as province_name, city as city_name, city_avg, row_number() over (partition by province order by city_avg desc ) as city_seqence
from (select province, city, avg(order_money) as city_avg
      from (select distinct order_sn, province, city, order_money
            from dwd.fact_order_master
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
    group by province, city)



# 连续两个月下单且保持增长的用户
select t1.customer_id as customer_id, t1.customer_name as customer_name,
       concat(concat(year1, if(month1 < 10, concat("0",month1),month1)),"_",concat(year2, if(month2 < 10, concat("0",month2),month2))) as continuous_month,
       (c1 + c2) as continuous_count, (s1+s2) as continuous_sum
from (select customer_id, customer_name, count(*) as c1, sum(order_money) as s1, year1, month1
      from (select distinct order_sn, o.customer_id, customer_name, year(create_time) as year1, month(create_time) as month1, order_money
            from dwd.fact_order_master as o
                     inner join  dwd.dim_customer_inf as c on o.customer_id = c.customer_id
            where order_sn not in(select order_sn from dwd.fact_order_master where order_status = "已退款") and length(city) <= 8)
      group by customer_id, customer_name,  year1, month1) as t1
inner join (select customer_id, customer_name, count(*) as c2, sum(order_money) as s2, year2, month2
            from (select distinct order_sn, o.customer_id, customer_name, year(create_time) as year2, month(create_time) as month2, order_money
                  from dwd.fact_order_master as o
                   inner join  dwd.dim_customer_inf as c on o.customer_id = c.customer_id
                  where order_sn not in(select order_sn from dwd.fact_order_master where order_status = "已退款") and length(city) <= 8)
            group by customer_id, customer_name,  year2, month2) as t2
on t1.customer_id=t2.customer_id
where s1 < s2 and  (year1 = year2 and month1=month2-1) or (year1=year2-1 and month1=12 and month2=01)




