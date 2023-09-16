package com.dsj.test

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

    /*
    * 1、根据dwd或者dws层表统计每人每天下单的数量和下单的总金额，
    *   存入dws层（需自建）的user_consumption_day_aggr表中（表结构如下），
    *   然后使用hive cli按照客户主键、订单总金额均为降序排序，查询出前5条；
    *        字段	类型	中文含义	备注
    *        customer_id	int	客户主键	customer_id
    *        customer_name	string	客户名称	customer_name
    *        total_amount	double	订单总金额	当天订单总金额
    *        total_count	int	订单总数	当天订单总数
    *        year	int	年	订单产生的年,为动态分区字段
    *        month	int	月	订单产生的月,为动态分区字段
    *        day	int	日	订单产生的日,为动态分区字段
    * */
    val df1 = spark.sql(
      """
        |
        |select customer_id,  customer_name , sum(order_money) total_amount, count(*) total_count, year, month, day
        |from (select distinct o.customer_id,customer_name, order_money,year(create_time) as year, month(create_time) as month, day(create_time) as day
        |      from dwd.fact_order_master as o
        |      inner join dwd.dim_customer_inf as c on o.customer_id = c.customer_id
        |      where order_sn not in (select order_sn from dwd.fact_order_master where order_status = '已退款')) as t1
        |group by customer_id, customer_name, year, month, day
        |""".stripMargin)

    // 将查出来的数据存入hive的dws下
    df1.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.user_consumption_day_aggr")

    /*
    * 2、根据dwd或者dws层表统计每个城市每月下单的数量和每月下单的总金额（以order_master中的地址为判断依据），
    *   并按照province_name，year，month进行分组,按照total_amount逆序排序，形成sequence值，
    *   将计算结果存入Hive的dws数据库city_consumption_day_aggr表中（表结构如下），
    *   然后使用hive cli根据订单总数、订单总金额均为降序排序，查询出前5条，
    *   在查询时对于订单总金额字段将其转为bigint类型（避免用科学计数法展示）；
    *        字段	类型	中文含义	备注
    *        city_name	string	城市名称
    *        province_name	string	省份名称
    *        total_amount	double	订单总金额	当月订单总金额
    *        total_count	int	订单总数	当月订单总数
    *        sequence	int	次序	即当月中该城市消费额在该省中的排名（分组排序）
    *        year	int	年	订单产生的年,为动态分区字段
    *        month	int	月	订单产生的月,为动态分区字段
    * */
    val df2 = spark.sql(
      """
        |select
        |       city_name, province_name,
        |       cast(total_amount as decimal(25,10)),
        |       total_count,
        |       row_number() over(partition by province_name, year, month order by total_amount desc ) as sequencesequence,
        |       year, month
        |from (select city as city_name, province as province_name, sum(order_money) as total_amount, count(*) as total_count, year, month
        |      from (select distinct province, city, order_money, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master
        |            where length(city)<=8 and order_sn not in(select order_sn from dwd.fact_order_master where order_status="已退款"))
        |      group by province, city,year, month) as t1
        |""".stripMargin)
    // Overwrite会创建表，并存入数据到dws
    df2.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.city_consumption_day_aggr")


    /*
    * 3、请根据dwd或者dws层表计算出每个城市月平均订单金额和该城市所在省份月平均订单金额相比较结果（“高/低/相同”）,
    *   存入ClickHouse数据库shtd_result的cityavgcmpprovince表中（表结构如下），
    *   然后在Linux的ClickHouse命令行中根据城市平均订单金额、省份平均订单金额均为降序排序，查询出前5条；
    *        字段	类型	中文含义	备注
    *        cityname	text	城市份名称
    *        cityavgconsumption	double	该城市平均订单金额
    *        provincename	text	省份名称
    *        provinceavgconsumption	double	该省平均订单金额
    *        comparison	text	比较结果	城市平均订单金额和该省平均订单金额比较结果，值为：高/低/相同
    * */

    val df3 = spark.sql(
      """
        |select city as cityname, cityavgconsumption, province as provincename, provinceavgconsumption,
        |       case
        |           when cityavgconsumption > provinceavgconsumption then '高'
        |           when cityavgconsumption < provinceavgconsumption then '低'
        |           when cityavgconsumption = provinceavgconsumption then '相同' end as comparison
        |from (select distinct  city, province,
        |             avg(order_money) over (partition by province, city, month) as cityavgconsumption,
        |             avg(order_money) over (partition by province, month)       as provinceavgconsumption
        |      from (select distinct city, province, order_money, year(create_time) as year, month(create_time) as month
        |            from dwd.fact_order_master as o
        |            where length(city) <= 8 and order_sn not in (select order_sn from dwd.fact_order_master where order_status = "已退款"))) as t1
        |""".stripMargin)
    // 存入ClickHouse
    df3.write.mode(SaveMode.Overwrite).format("jdbc")
      .option("driver", "ru.yandex.clickhouse.ClickHouseDriver")
      .option("url","jdbc:clickhouse://192.168.66.130:8123/shtd_result")
      .option("user","default")
      .option("password","123456")
      .option("dbtable","cityavgcmpprovince").save()



    // 关闭资源
    spark.stop()
  }
}
