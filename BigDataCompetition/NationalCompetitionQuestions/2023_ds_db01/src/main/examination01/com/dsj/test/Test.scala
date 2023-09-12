package com.dsj.test

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._


object Test {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("Test")
      .enableHiveSupport() // 开启spark对hive的支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置动态分区的模式为非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在用引号引起来的类名称中使用正则表达式
      .getOrCreate()

    /*
    * todo CASE1  以modified_time做为增量字段
    *   将MySQL的 ds_db01 库中所有表的数据全量抽取到 Hive 的 ods 库中对应表中
    *   思路：从MySQL中拿出数据，在hive找到最大的modified_time值
    * */

    // 连接MySQL的ds_db01数据库
    val mysql_read = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.20.37.78:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230828"

    val tables = Array("brand_info", "coupon_info", "customer_addr", "customer_inf", "customer_level_inf", "customer_login", "favor_info", "order_cart", "order_detail", "order_master",
      "product_browse", "product_category", "product_comment", "product_info", "product_pic_info", "shipping_info", "supplier_info", "warehouse_info", "warehouse_product")
    tables.foreach(table => {
      // 从hive的ods层拿出modified_time值最大的数据
      val max_ods_df = spark.sql(s"select string(if(max(modified_time) is null, '' ,max(modified_time) )) from ods.${table}").first().getString(0)
      // 如果 最大值不为空，则将最大值作为增量条件
      var sql = s"select * from ${table}"
      if (!max_ods_df.equals("")) {
        sql += " where modified_time > 'max_ods_df'"
      }
      // 从mysql中拿出数据
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    /*
   * todo CASE2  以create_time为增量的操作
   *   customer_balance_log、customer_point_log表使用create_time作增量字段
   * */
    // 要增量抽取的表名
    val tables_2 = Array("customer_balance_log", "customer_point_log")
    tables_2.foreach(table => {
      // 从hive的ods层中拿出最大的create_time值
      val max_create_time = spark.sql(s"select string(if(max(create_time) is null,'' ,max(create_time) )) from ods.${table}").first().getString(0)
      // 如果hive的ods层的最大值不为空，则作为增量数据的条件
      var sql = s"select * from ${table}"
      if (!max_create_time.equals("")) {
        sql += " where create_time > 'max_create_time'"
      }
      // 从mysql中拿出数据
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区表
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    /*
    * todo CASE3  以login_time增量
    *   customer_login_log使用login_time作增量字段
    * */
    val tables_3 = Array("customer_login_log")
    tables_3.foreach(table => {
      // 从hive的ods层拿出login_time值最大数据
      val max_login_time = spark.sql(s"select string(if(max(login_time) is null , '' , max(login_time))) from ods.${table}").first().getString(0)
      //
      var sql = s"select * from ${table}"
      if (!max_login_time.equals("")) {
        sql += " where login_time > 'max_login_time'"
      }
      // 从MySQL中拿出数据，同时添加动态分区
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层的静态分区
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    /**
     * todo CASE4
     * coupon_use表取三个日期列最大值作为增量字段
     * */
    // 消费券使用记录表以三列取最大的查询
    // 从hive的ods层的coupon_use表中这三列get_time、used_time、pay_time的最大值
    val max_time = spark.sql("select greatest(max(get_time),max(if(used_time='NULL', '', used_time)),max(if(pay_time='NULL','',pay_time))) from ods.coupon_use ").first().getString(0)
    println(max_time)
    // 从MySQL中拿出数据，同时添加静态分区列
    val df = mysql_read.option("dbtable", "coupon_use").load()
      .where(array_max(array_remove(array("get_time", "used_time", "pay_time"), "NULL")).cast("string") > max_time)
      .withColumn("etl_date", lit(etl_date))
    println(df.count())
    df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable("ods.coupon_use")


    // 关闭资源
    spark.stop()
  }
}
