package com.dsj.stage24

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}
import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd923 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // todo 直接抽取dwd
    val tables_1 = Array("customer_level_inf", "customer_login_log", "customer_addr", "coupon_use", "coupon_info")
    tables_1.foreach(table => {
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230922'")
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.dim_${table}")
    })

    // todo ods数据和dwd层合并

    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    val ods_tables = Array("customer_inf", "product_info")
    val ids = Array("customer_id", "product_id")
    val dwd_tables = Array("dim_customer_inf", "dim_product_info")
    for (i <- 0 until ods_tables.length) {
      // 获取到表名
      val ods_table = ods_tables(i)
      val dwd_table = dwd_tables(i)

      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230922'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))

      val dwd_df = spark.sql(s"select * from dwd.${dwd_table}")

      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq", row_number().over(Window.partitionBy(s"${ids(i)}").orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1))
        .drop("seq")
        .withColumn("etl_date", lit("20230922"))

      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
    }

    // todo 三表联查
    val df = spark.sql(
      """
        |select customer_id,customer_name, identity_card_no,
        |       case
        |           when gender="M" then "男"
        |           when gender="W" then "女"
        |           else "未知"
        |        end  as gender,  customer_point, register_time, level_name, customer_money, province, city, address, modified_time
        |from (select * , row_number() over (partition by customer_id order by modified_time desc)
        |      from (select distinct ci.customer_id, customer_name, identity_card_no, gender,
        |                   customer_point, register_time, level_name, customer_money, province, city, address, ca.modified_time
        |            from dwd.dim_customer_inf as ci
        |                     inner  join dwd.dim_customer_addr as ca  on ci.customer_id = ca.customer_id
        |                     inner join dwd.dim_customer_level_inf as li on ci.customer_level= li.customer_level))
        |""".stripMargin)
    df.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dwd.dim_customer_services")

    spark.stop()
  }
}
