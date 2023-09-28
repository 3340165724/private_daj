package com.dsj.stage26

import org.apache.spark.sql.{SaveMode, SparkSession}

object db {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()


    val df = spark.sql(
      """
        |select distinct customer_id, date_format(create_time,'yyyy-MM-dd') as date
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <=8
        |""".stripMargin)
    df.printSchema()

    val df1 = spark.sql(
      """
        |select  province as province_name, city as city_name, city_avg, row_number() over (partition by province order by city_avg desc) as city_seqence
        |from (select province, city, avg(order_money) as city_avg
        |      from (select distinct order_sn, province, city, order_money
        |            from dwd.fact_order_master
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款") and length(city) <= 8)
        |    group by province, city)
        |""".stripMargin)

    df1.write.mode(SaveMode.Append).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user", "default")
      .option("password", "123456")
      .option("dbtable", "tb_province_city_avg")
      .save()


    val df3 = spark.sql(
      """
        |select t1.customer_id as customer_id, t1.customer_name as customer_name,
        |       concat(concat(year1, if(month1 < 10, concat("0",month1),month1)),"_",concat(year2, if(month2 < 10, concat("0",month2),month2))) as continuous_month,
        |       (c1 + c2) as continuous_count, (s1+s2) as continuous_sum
        |from (select customer_id, customer_name, count(*) as c1, sum(order_money) as s1, year1, month1
        |      from (select distinct order_sn, o.customer_id, customer_name, year(create_time) as year1, month(create_time) as month1, order_money
        |            from dwd.fact_order_master as o
        |                     inner join  dwd.dim_customer_inf as c on o.customer_id = c.customer_id
        |            where order_sn not in(select order_sn from dwd.fact_order_master where order_status = "已退款") and length(city) <= 8)
        |      group by customer_id, customer_name,  year1, month1) as t1
        |inner join (select customer_id, customer_name, count(*) as c2, sum(order_money) as s2, year2, month2
        |            from (select distinct order_sn, o.customer_id, customer_name, year(create_time) as year2, month(create_time) as month2, order_money
        |                  from dwd.fact_order_master as o
        |                   inner join  dwd.dim_customer_inf as c on o.customer_id = c.customer_id
        |                  where order_sn not in(select order_sn from dwd.fact_order_master where order_status = "已退款") and length(city) <= 8)
        |            group by customer_id, customer_name,  year2, month2) as t2
        |on t1.customer_id=t2.customer_id
        |where s1 < s2 and  (year1 = year2 and month1=month2-1) or (year1=year2-1 and month1=12 and month2=01)
        |""".stripMargin)
    df3.write.mode(SaveMode.Append).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url", "jdbc:clickhouse://172.20.37.85:8123/shtd_result")
      .option("user", "default")
      .option("password", "123456")
      .option("dbtable", "tb_buymonths_infos")
      .save()




    // 关闭资源
    spark.stop()
  }
}
