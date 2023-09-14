package com.dsj.test

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object IncrementalExtractionTest {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 连接MySQL
    val mysql_reader = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.20.37.78:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230914"

    // todo 以modified_time作为增量字段
    // 需要增量抽取的表
    val tables = Array("brand_info", "coupon_info", "customer_addr", "customer_inf", "customer_level_inf", "customer_login", "favor_info", "order_cart", "order_cart", "order_master", "product_browse", "product_category", "product_comment", "product_info", "product_pic_info", "shipping_info", "supplier_info", "warehouse_info", "warehouse_product")
    tables.foreach(table => {
      // 从hive中去找modified_time最大的值
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from ods.${table}").first().getString(0)
      // 如果最大值不为空
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where modified_time > '${ods_df}'"
      }
      // 从mysql中拿出大于最大时间的数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区中表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 以create_time为增量的操作
    // 需要增量抽取的表
    val tables_2 = Array("customer_balance_log", "customer_point_log")
    tables_2.foreach(table => {
      // 从hive的ods层表拿出create_time最大数据，
      val ods_df = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from ods.${table}").first().getString(0)
      // 如果最大值不为空，则添加条件作为增量条件
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where create_time > '${ods_df}'"
      }
      // 从MySQL中拿出数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 以login_time增量
    // 需要增量抽取的表
    val tables_3 = Array("customer_login_log")
    tables_3.foreach(table => {
      // 从hive的ods层表拿出create_time最大数据，
      val ods_df = spark.sql(s"select string(if(max(login_time) is null,'',max(login_time))) from ods.${table}").first().getString(0)
      // 如果最大值不为空，则添加条件作为增量条件
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where login_time > '${ods_df}'"
      }
      // 从MySQL中拿出数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 三列取最大值
    // 从hive的ods层coupon_use表中get_time、user_time、pay_time散列中拿出最大值，作为增量条件
    val ods_df = spark.sql("select if(c is null, '',c) from (select greatest(max(get_time),max(if(user_time='NULL','',user_time)),max(if(pay_time='NULL','',pay_time))) as c from ods.coupon_use) as t1")
      .first().getString(0)
    // 从MySQL中拿出数据
    val df = mysql_reader.option("dbtable", "ods.coupon_use").load()
      .where(array_max(array_remove(array("get_time", "user_time", "pay_time"), "NULL")).cast("string") > ods_df)
      .withColumn("etl_date", lit(etl_date))
    // 追加模式写入hive的ods层的静态分区表中
    df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable("ods.coupon_use")

    // 关闭资源
    spark.stop()
  }
}
