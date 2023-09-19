package com.dsj.stage24

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object Increment918 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport() // 开启spark对hive的支持
      .config("hive.exec.dynamic.partition", "true") // 开启hive的动态分区
      .config("hive.exec.dynamic.partition.mode", "nonsrtict") // 设置hive的模式为非严格模式
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许列名使用正则表达式
      .getOrCreate()

    // 连接MySQL
    val mysql_reader = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.20.37.85:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    // 分区列
    val etl_date = "20230917"

    // todo 第一种根据modiffied_time作为增量字段
    // 需要使用的表
    val tables_1 = Array("brand_info", "coupon_info", "customer_addr", "customer_inf", "customer_level_inf", "customer_login", "favor_info",
      "order_cart", "order_detail", "order_master", "product_browse", "product_category", "product_comment", "product_info", "product_pic_info",
      "shipping_info", "supplier_info", "warehouse_info", "warehouse_product")
    tables_1.foreach(table => {
      // 从hive的ods查出modiffied_time最大的时间
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from ods.${table} ").first().getString(0)
      // 如果最大值不为空，则作为增量抽取的条件，否则全量抽取
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where modified_time > ${ods_df}"
      }
      //从MySQL中拿出数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的静态分区表中
//      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 第二种根据 create_time作为增量字段
    val tables_2 = Array("customer_balance_log", "customer_point_log")
    tables_2.foreach(table => {
      // 从hive的ods查出modiffied_time最大的时间
      val ods_df = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from ods.${table} ").first().getString(0)
      // 如果最大值不为空，则作为增量抽取的条件，否则全量抽取
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where create_time > ${ods_df}"
      }
      //从MySQL中拿出数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // 第三种根据login_time作为增量值
    // 需要的表名
    val tables_3 = Array("customer_login_log")
    tables_3.foreach(table => {
      // 从hive的ods查出modiffied_time最大的时间
      val ods_df = spark.sql(s"select string(if(max(login_time) is null,'',max(login_time))) from ods.${table} ").first().getString(0)
      // 如果最大值不为空，则作为增量抽取的条件，否则全量抽取
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where login_time > ${ods_df}"
      }
      //从MySQL中拿出数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo 第四种三列取最大值
    // 从hive的ods层coupon_use表中get_time、user_time、pay_time散列中拿出最大值，作为增量条件
    val ods_df = spark.sql(
      """
        |select if(c is null ,'',c)
        |from (select
        |           greatest(max(get_time),max(if(user_time = 'NULL','',user_time)),max(if(pay_time='NULL','',pay_time))) as c
        |      from coupon_use) as t1
        |""".stripMargin).first().getString(0)
    // 从MySQL种拿出数据
    val df = mysql_reader.option("dbtable", "coupon_use").load()
      .where(array_max(array_remove(array("get_time", "user_time", "pay_time"), "NULL")).cast("string") > ods_df)
      .withColumn("etl_date", lit(etl_date))
    // 追加模式写入hive的静态分区表中
    df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable("ods.coupon_use")
    // 关闭资源
    spark.stop()
  }
}
