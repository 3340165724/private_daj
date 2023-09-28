package com.dsj.stage26

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object Increment {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 连接MySQL
    val mysql_read = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://172.20.37.85:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230918"

    // todo modified_time作为增量字段
    val tables = Array("customer_inf", "product_info", "order_master", "order_detail", "coupon_info", "customer_addr", "customer_level_inf", "customer_login", "order_cart", "product_browse")
    tables.foreach(table => {
      // 查出ods层modified_time最大的数据
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from ods.${table}").first().getString(0)
      // 如果最大值不为空，最大值作为增量条件，否则全量抽取
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where modified_time > '${ods_df}'"
      }
      // 从mysql中拿出数据
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
      // 创建临时表
      //      df.createOrReplaceTempView("mytable")
      //      // 插入数据
      //      spark.sql(s"insert into ods.${table} select * from mytable ")
    })


    // todo get_time、used_time、pay_time三列取最大值

    val ods_df = spark.sql("select if(c is null,'',c) from (select greatest(max(get_time),max(if(used_time='NULL','',used_time)),max(if(pay_time='NULL','',pay_time))) as c from ods.coupon_use) as t1").first().getString(0)
    // 从MySQL中拿出数据
    val df = mysql_read.option("dbtable", "coupon_use").load()
      .where(array_max(array_remove(array("get_time", "used_time", "pay_time"), "NULL")).cast("string") > ods_df)
      .withColumn("etl_date", lit(etl_date))
    // 追加模式写入hive的ods层
    df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.coupon_use")


    // todo login_time作为增量字段
    val tables_3 = Array("customer_login_log")
    tables_3.foreach(table => {
      // 查出ods层modified_time最大的数据
      val ods_df = spark.sql(s"select string(if(max(login_time) is null,'',max(login_time))) from ods.${table}").first().getString(0)
      // 如果最大值不为空，最大值作为增量条件，否则全量抽取
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where login_time > '${ods_df}'"
      }
      // 从mysql中拿出数据
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })

    // todo create_time作为增量字段
    val tables_4 = Array("customer_point_log")
    tables_4.foreach(table => {
      // 查出ods层modified_time最大的数据
      val ods_df = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from ods.${table}").first().getString(0)
      // 如果最大值不为空，最大值作为增量条件，否则全量抽取
      var sql = s"select * from ${table}"
      if (!ods_df.equals("")) {
        sql += s" where create_time > '${ods_df}'"
      }
      // 从mysql中拿出数据
      val df = mysql_read.option("dbtable", s"(${sql}) as t1").load()
        .withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive的ods层
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })
    // 关闭环境
    spark.stop()
  }
}
