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

    val df = spark.sql(
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
    df.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dws.user_consumption_day_aggr")

    // 关闭资源
    spark.stop()
  }
}
