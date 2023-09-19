package com.dsj.stage24

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 获取系统当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    // todo dwd最新分区的数据和ods分区数据合并
    // 需要的表
    val ods_tables = Array("customer_inf", "product_info")
    val dwd_tables = Array("dim_customer_inf", "dim_product_info")
    val ids = Array("customer_id", "product_id")
    for (i <- 0 until ods_tables.length) {
      // 获取到表名
      val ods_table = ods_tables(i)
      val dwd_table = dwd_tables(i)
      // 从ods拿出数据
      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230918'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      // 从dwd层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.${dwd_table} where etl_date='20230301'")
      // 合并 去重
      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq", row_number().over(Window.partitionBy(s"${ids(i)}").orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1)).drop("seq")
        .withColumn("etl_date", lit("20230918"))
      // 追加写入dwd
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
    }

    // todo 直接抽取到dwd
    // 需要的表
    //    val tables = Array("customer_level_inf", "customer_login_log", "customer_addr", "coupon_use", "coupon_info")
    val tables = Array("coupon_use", "coupon_info")
    tables.foreach(table => {
      // 从ods层拿出数据
      var ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230918'")
        .drop("etl_date")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        .withColumn("etl_date", lit("20230918"))
      if (table.equals("coupon_use")) {
        ods_df = ods_df.withColumn("get_time", to_timestamp(col("get_time"), "yyyyMMddHHmmss"))
          .withColumn("used_time", to_timestamp(col("used_time"), "yyyyMMddHHmmss"))
          .withColumn("pay_time", to_timestamp(col("pay_time"), "yyyyMMddHHmmss"))
      }
      // 追加模式写入dwd层
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    })

    // todo 抽离出关键数据 其中对于性别根据表中数据处理为【(M)男、(W)女、未知】
    // 需要操作的表
    val df = spark.sql(
      """
        |select  customer_id, customer_name, identity_card_no,
        |       case
        |           when gender="M" then "男"
        |           when gender="W" then "女"
        |           else "未知"
        |           end,
        |       customer_point, register_time,level_name, customer_money, province, city, address, modified_time
        |from (select *, row_number() over (partition by customer_id order by modified_time) as seq
        |      from (select ci.customer_id, customer_name, identity_card_no, gender, customer_point, register_time,
        |                   level_name, customer_money, province, city, address, ca.modified_time
        |            from dwd.dim_customer_inf as ci
        |            inner join dwd.dim_customer_addr as ca on ci.customer_id = ca.customer_id
        |            inner join dwd.dim_customer_level_inf as cli on ci.customer_level = cli.customer_level))
        |where seq=1
        |""".stripMargin)
    df.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dwd.dim_customer_services")




    // 关闭环境
    spark.stop()
  }
}
