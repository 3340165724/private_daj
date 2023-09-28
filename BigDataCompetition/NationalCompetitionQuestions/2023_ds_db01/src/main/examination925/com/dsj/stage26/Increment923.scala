package com.dsj.stage26

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object Increment923 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val mysql_read = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.20.37.85:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230924"

    // todo  以modified_time作为增量字段
    val tables_1 = Array("customer_inf", "product_info", "order_master", "order_detail", "coupon_info", "customer_addr", "customer_level_inf", "customer_login", "order_cart", "product_browse")
    tables_1.foreach(table => {
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from ods.${table}").first().getString(0)

      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where modified_time > '${ods_df}'"
      }
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))

      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })


    // todo 以create_time作为增量条件
    val tables_2 = Array("customer_point_log")
    tables_2.foreach(table => {
      val ods_df = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from ods.${table}").first().getString(0)

      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where create_time > '${ods_df}'"
      }
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))

      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 以login_time作为增量
    val tables_3 = Array("customer_login_log")
    tables_3.foreach(table => {
      val ods_df = spark.sql(s"select string(if(max(login_time) is null,'',max(login_time))) from ods.${table}").first().getString(0)

      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where login_time > '${ods_df}'"
      }
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))

      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 三列取最大值
    val ods_df_ = spark.sql("select greatest(max(get_time), max(if(used_time='NULL','',used_time)), max(if(pay_time='NULL','',pay_time))) from ods.coupon_use").first().getString(0)
    val df_ = mysql_read.option("dbtable", "coupon_use").load()
      .where(array_max(array_remove(array("get_time", "used_time", "pay_time"), "NULL")).cast("string") > ods_df_)
      .withColumn("etl_date", lit(etl_date))
    df_.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable("ods.coupon_use")

    spark.stop()
  }
}
