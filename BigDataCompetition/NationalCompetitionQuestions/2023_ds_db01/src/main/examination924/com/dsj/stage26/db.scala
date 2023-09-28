package com.dsj.stage26

import org.apache.spark.sql.{SaveMode, SparkSession}

object db {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val df8 = spark.sql(
      """
        |select province,count(*) as c
        |from (select distinct order_sn, province
        |      from dwd.fact_order_master
        |      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
        |group by province
        |order by c desc
        |""".stripMargin)


    val df6 = spark.sql(
      """
        |select t1.product_id as topquantityid, t1.product_name as topquantityname, topquantity,
        |       t2.product_id as toppriceid, t2.product_name as toppricename, topprice,
        |       seq1 as sequence
        |from (select product_id, product_name, topquantity, seq1
        |      from (select *, row_number() over (order by topquantity desc) as seq1
        |            from (select product_id, product_name, sum(product_cnt) as topquantity
        |                  from dwd.fact_order_detail
        |                  group by product_id, product_name))
        |      where seq1 <= 10) as t1
        |inner join (select product_id, product_name, topprice, seq2
        |            from (select *, row_number() over (order by topprice desc) as seq2
        |                  from (select product_id, product_name, sum(product_cnt * product_price) as topprice
        |                        from dwd.fact_order_detail
        |                        group by product_id, product_name))
        |            where seq2 <= 10) as t2
        |on t1.seq1=t2.seq2
        |""".stripMargin)
    df6.write.mode(SaveMode.Append).format("jdbc")
      .option("driver","ru.yandex.clickhouse.ClickHouseDriver")
      .option("url","jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user","default")
      .option("password","123456")
      .option("dbtable","topten")
      .save()


    val df5 = spark.sql(
      """
        |select  province as provincename,
        |       concat_ws(',', collect_list(substr(city, -3 ,3))) as citynames,
        |       concat_ws(',',collect_list(total)) as cityamount
        |from (select  province, city, total, seq
        |      from (select province, city, total, row_number() over (partition by province order by total desc) as seq
        |            from (select province, city, cast(sum(order_money) as decimal(10,0)) as total
        |                  from (select distinct order_sn, province, city, order_money
        |                        from dwd.fact_order_master
        |                        where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
        |                          and length(city) <= 8 and year(create_time)=2022)
        |                  group by province, city)) as t1
        |      where seq < 4)
        |group by province
        |""".stripMargin)
    df5.write.mode(SaveMode.Append).format("jdbc")
      .option("driver","ru.yandex.clickhouse.ClickHouseDriver")
      .option("url","jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user","default")
      .option("password","123456")
      .option("dbtable","regiontopthree")
      .save()


    val df4 = spark.sql(
      """
        |select city as cityname, citymidonsumption, province as provincename, provincemidconsumption,
        |       case
        |           when citymidonsumption > provincemidconsumption then "高"
        |           when citymidonsumption < provincemidconsumption then "低"
        |           when citymidonsumption = provincemidconsumption then "相同"
        |           end as comparison
        |from (select distinct  city, percentile_approx(order_money, 0.5) over(partition by province, city, year, month) as citymidonsumption,
        |             province, percentile_approx(order_money, 0.5) over(partition by province, year, month) as provincemidconsumption
        |      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))
        |""".stripMargin)
    df4.write.mode(SaveMode.Append).format("jdbc")
      .option("driver","ru.yandex.clickhouse.ClickHouseDriver")
      .option("url","jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user","default")
      .option("password","123456")
      .option("dbtable","citymidcmpprovince")
      .save()


    val df3 = spark.sql(
      """
        |select city as cityname, cityavgconsumption, province as provincename, provinceavgconsumption,
        |       case
        |           when cityavgconsumption > provinceavgconsumption then "高"
        |           when cityavgconsumption < provinceavgconsumption then "低"
        |           when cityavgconsumption = provinceavgconsumption then "相同"
        |           end as comparison
        |from (select distinct city, avg(order_money) over(partition by province, city, year, month ) as cityavgconsumption ,
        |             province, avg(order_money) over(partition by province, year, month ) as provinceavgconsumption
        |      from (select distinct order_sn, city, order_money, province, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))
        |""".stripMargin)
    df3.write.mode(SaveMode.Append).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://172.20.37.85/shtd_result")
      .option("root", "default")
      .option("password", "123456")
      .option("dbtable", "cityavgcmpprovince")
      .save()


    val df2 = spark.sql(
      """
        |select  city as city_name, province as province_name, total_amount, total_count,
        |       row_number() over (partition by province, year, month order by total_amount desc) as sequence,
        |       year, month
        |from (select city, province, sum(order_money) as total_amount, count(*) as total_count, year, month
        |      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status = "已退款" ) and length(city) <= 8)
        |      group by city, province, year, month)
        |
        |""".stripMargin)
    df2.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.city_consumption_day_aggr")


    val df1 = spark.sql(
      """
        |select customer_id, customer_name, sum(order_money) as total_amount, count(*) as total_count, year, month, day
        |from (select distinct order_sn , o.customer_id, customer_name, order_money, year(create_time) as year, month(create_time) as month, day(create_time) as day
        |      from dwd.fact_order_master as o
        |               inner join dwd.dim_customer_inf as c on o.customer_id = c.customer_id
        |      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
        |group by customer_id, customer_name, year, month, day
        |""".stripMargin)
    df1.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.user_consumption_day_aggr")




    // 关闭资源
    spark.stop()
  }
}
