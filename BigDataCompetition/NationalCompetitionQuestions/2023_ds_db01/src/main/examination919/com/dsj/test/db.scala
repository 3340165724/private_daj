package com.dsj.test

import org.apache.spark.sql.{SaveMode, SparkSession}

object db {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .enableHiveSupport() // 开启hive支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstaict") // 设置分区模式为非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 最大分区数
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val df1 = spark.sql(
      """
        |select  customer_id, customer_name, sum(order_money) as total_amount, count(*) as total_count, year, month, day
        |from (select distinct order_sn, o.customer_id, c.customer_name,order_money, year(create_time) as year, month(create_time) as month, day(create_time) as day
        |      from dwd.fact_order_master as o
        |      inner join dwd.dim_customer_inf as c  on o.customer_id = c.customer_id
        |      where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8 ) as t1
        |group by  customer_id, customer_name, year, month, day
        |""".stripMargin)
    // 存入dws层（需自建）的user_consumption_day_aggr表中（表结构如下）
    df1.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.user_consumption_day_aggr")


    val df2 = spark.sql(
      """
        |select city_name, province_name, cast(total_amount as decimal(25,2)) , total_count,
        |       row_number() over (partition by province_name, year, month order by  total_amount) as sequence, year, month
        |from (select  city as city_name, province as province_name, sum(order_money) as total_amount, count(*) as total_count, year, month
        |      from (select distinct order_sn, city, province, year(create_time) as year, month(create_time) as month, order_money
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
        |      group by city, province,  year, month)
        |""".stripMargin)
    //  将计算结果存入Hive的dws数据库city_consumption_day_aggr表中（表结构如下）
    df2.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.city_consumption_day_aggr")


    val df3 = spark.sql(
      """
        |select  cityname, cityavgconsumption, provincename, provinceavgconsumption,
        |       case
        |           when cityavgconsumption > provinceavgconsumption then "高"
        |           when cityavgconsumption < provinceavgconsumption then "低"
        |           when cityavgconsumption = provinceavgconsumption then "相同"
        |           end as comparison
        |from (select distinct city as cityname, province as provincename,
        |             avg(order_money) over(partition by province, city, year, month ) as cityavgconsumption,
        |             avg(order_money) over(partition by province, year, month) as provinceavgconsumption
        |      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status= "已退款") and length(city) <=8 ))
        |""".stripMargin)
    df3.write.mode(SaveMode.Append).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user", "default")
      .option("password", "123456")
      .option("dbtable", "cityavgcmpprovince").save()


    val df4 = spark.sql(
      """
        |select city as cityname, citymidconsumption, province as provincename, provincemidconsumption,
        |       case
        |           when citymidconsumption > provincemidconsumption then '高'
        |           when citymidconsumption < provincemidconsumption then '低'
        |           when citymidconsumption = provincemidconsumption then '相同' end as comparison
        |from (select distinct city, province,
        |                      percentile_approx(order_money,0.5) over(partition by province,city, year, month ) as citymidconsumption,
        |                      percentile_approx(order_money,0.5) over(partition by province, year, month) as provincemidconsumption
        |      from (select distinct order_sn, city, province, order_money, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8))
        |""".stripMargin)
    df4.printSchema()
    df4.write.mode(SaveMode.Append).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user", "default")
      .option("password", "123456")
      .option("dbtable", "citymidcmpprovince")
      .save()


    val df5 = spark.sql(
      """
        |select province as provincename,
        |       concat_ws(',',collect_list(substr(city,-3,3))) as citynames,
        |       concat_ws(',',collect_list(total)) as cityamount
        |from (select province, city, total, seq
        |      from (select province, city, total, row_number() over (partition by province order by total desc) as seq
        |            from (select province, city, cast(sum(order_money) as decimal(10,0) ) as total
        |                  from (select distinct order_sn, province, city, order_money
        |                        from dwd.fact_order_master
        |                        where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款")
        |                          and length(city) <= 8 and year(create_time) = 2022)
        |                  group by province, city) ) as t1
        |      where seq < 4)
        |group by province
        |""".stripMargin)
    df5.write.mode(SaveMode.Append).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user", "default")
      .option("password", "123456")
      .option("dbtable", "regiontopthree")
      .save()

  }
}
