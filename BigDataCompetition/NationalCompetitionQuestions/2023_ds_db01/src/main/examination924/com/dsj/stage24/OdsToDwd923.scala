package com.dsj.stage24

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd923 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    // todo 直接抽取
    val tables_1 = Array("customer_level_inf", "customer_login_log", "customer_addr", "coupon_use", "coupon_info")
    tables_1.foreach(table => {
      var ods_df = spark.sql(s"select * from ods.${table} where etl_date = '20230923'")
        .drop("etl_date")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        .withColumn("etl_date", lit("20230923"))
      if (table.equals("coupon_use")) {
        ods_df = ods_df.withColumn("get_time", to_timestamp(col("get_time"), "yyyyMMddHHmmss"))
          .withColumn("used_time", to_timestamp(col("used_time"), "yyyyMMddHHmmss"))
          .withColumn("pay_time", to_timestamp(col("pay_time"), "yyyyMMddHHmmss"))
      }
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    })

    // todo ods层数据和dwd数据合并存入dwd
    val tables = Array("customer_inf", "product_info")
    val ids = Array("customer_id", "product_id")
    for (i <- 0 until tables.length) {
      val table = tables(i)
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230923'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")

      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq", row_number().over(Window.partitionBy(s"${ids(i)}").orderBy("modified_time")))
        .filter(_.getAs("seq").equals(1)).drop("seq")
        .withColumn("etl_date", lit("20230923"))
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    }

    // todo 三表数据合并
    val df = spark.sql(
      """
        |select customer_id, customer_name, identity_card_no,
        |       case
        |           when gender="M" then "男"
        |           when gender="W" then "女"
        |           else "未知"
        |           end as gender,
        |       customer_point, register_time, level_name, customer_money, province, city, address, modified_time
        |from (select *, row_number() over (partition by customer_id order by modified_time desc) as seq
        |      from (select dci.customer_id, customer_name, identity_card_no,
        |                   gender, customer_point, register_time, level_name, customer_money, province, city, address, dca.modified_time
        |            from dwd.dim_customer_inf as dci
        |            inner join dwd.dim_customer_addr as dca on dci.customer_id = dca.customer_id
        |            inner join dwd.dim_customer_level_inf as cl on dci.customer_level = cl.customer_level))
        |where seq = 1
        |""".stripMargin)
    df.printSchema()
    df.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dwd.dim_customer_services")

    // 关闭
    spark.stop()
  }
}
