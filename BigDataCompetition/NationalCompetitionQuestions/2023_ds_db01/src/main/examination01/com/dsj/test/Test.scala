package com.dsj.test

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._


object Test {
  /*1、编写 Scala 工程代码，将MySQL的 ds_db01 库中所有表的数据全量抽取到 Hive 的 ods 库中对应表中，
其中coupon_use表取三个日期列最大值作为增量字段，customer_balance_log、customer_point_log表
使用create_time作增量字段，customer_login_log使用login_time作增量字段。字段排序，类型不变，
同时添加静态分区etl_date，分区字段类型为 String，且值为比赛前一天日期（分区字段格式为 yyyyMMdd）。
并在 hive cli 执行 show partitionssupport
ods.表名命令，将结果截图复制粘贴至对应报告中*/
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

    val etl_date = "20230911"

    // ds_db01 库中所有表的数据全量抽取到 Hive 的 ods 库中对应表中，以modified_time作为增量字段
    // 要增量抽取的表
    val tables = Array("brand_info", "coupon_info", "customer_addr", "customer_inf", "customer_level_inf", "customer_login", "favor_info", "order_cart", "order_detail", "order_master", "product_browse", "product_category", "product_comment", "product_info", "product_pic_info", "shipping_info", "supplier_info", "warehouse_info", "warehouse_product")
    tables.foreach(table => {
      // 查出hive的ods层modified_time字段的最大值
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from ods.${table}").first().getString(0)
      // 如果odf_df 的值不为空，则作为条件
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += " where modified_time > 'ods_df'"
      }
      // 从MySQL中拿出数据
      val df = mysql_reader.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // coupon_use表取三个日期列最大值作为增量字段
    // 查出hive的ods层的coupon_use表get_time、used_time、pay_time字段的最大值
    val max_time = spark.sql("select if(c is null,'',c) from (select greatest(max(get_time),max(if(used_time='NULL','',used_time)),max(if(pay_time='NULL','',pay_time))) as c from ods.coupon_use) as t1").first().getString(0)
    // 从mysql中拿出数据
    val mysql_df = mysql_reader.option("dbtable", "coupon_use").load()
      .where(array_max(array_remove(array("get_time", "used_time", "pay_time"), "NULL")).cast("string") > max_time)
      .withColumn("etl_date", lit(etl_date))
    // 追加模式写入hive的ods层的静态分区表中
    mysql_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable("ods.coupon_use")

    // customer_balance_log、customer_point_log表 使用create_time作增量字段
    // 要增量抽取的表
    val tables_2 = Array("customer_balance_log", "customer_point_log")
    tables_2.foreach(table => {
      // 从hive的ods层的表中拿出create_time的最大值
      val ods_df = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from ods.${table}").first().getString(0)
      // 如果ods_df 的值不为空，则作为增量条件
      var sql = s"select * from ${table}"
      if(!ods_df.equals("")){
        sql += " where create_time > 'ods_df'"
      }
      // 从mysql中拿出数据
      val df = mysql_reader.option("dbtable",s"(${sql}) as t1").load()
        .withColumn("etl_date",lit(etl_date))
      // 追加模式写入hive的ods层的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // customer_login_log使用login_time作增量字段
    // 要增量抽取的表
    val tables_3 = Array("customer_login_log")
    tables_3.foreach(table => {
      // 从hive的ods层的表中拿出create_time的最大值
      val ods_df = spark.sql(s"select string(if(max(login_time) is null,'',max(login_time))) from ods.${table}").first().getString(0)
      // 如果ods_df 的值不为空，则作为增量条件
      var sql = s"select * from ${table}"
      if(!ods_df.equals("")){
        sql += " where login_time > 'ods_df'"
      }
      // 从mysql中拿出数据
      val df = mysql_reader.option("dbtable",s"(${sql}) as t1").load()
        .withColumn("etl_date",lit(etl_date))
      // 追加模式写入hive的ods层的静态分区表中
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // 关闭资源
    spark.stop()
  }
}
