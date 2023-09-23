package com.dsj.stage24


import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}
import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd922 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "true")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // todo 直接抽取到dwd层
    // 需要抽取的表
    val tables_1 = Array("customer_level_inf", "customer_login_log", "customer_addr", "coupon_use", "coupon_info")
    tables_1.foreach(table => {
      // 从ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230921'")
      // 追加模式写入dwd层的静态分区
      ods_df.write.mode(SaveMode.Overwrite).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_${table}")
    })

    // 获取系统当前时间
    val currDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
    // todo 从ods数据拿出和dwd层合并
    // 需要抽取的表
    val tables_2 = Array("customer_inf", "product_info")
    val ids = Array("customer_id", "product_id")
    for (i <- 0 until tables_2.length) {
      // 获取到表名
      val table = tables_2(i)
      // ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='202030921'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      // 从dwd层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")
      // 联合
      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time") over (Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq", row_number() over (Window.partitionBy(s"${ids(i)}") orderBy (desc("modified_time"))))
        .filter(_.getAs("seq").equals(1)).drop("seq")
        .withColumn("etl_date", lit("20230921"))
      ods_df.printSchema()
      all_df.printSchema()

      // 追加模式写入dwd层
      all_df.write.mode(SaveMode.Overwrite).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    }

    // todo 三表连查
    val df = spark.sql(
      """
        |select customer_id, customer_name, identity_card_no,
        |       case
        |           when gender='M' then '男'
        |           when gender='W' then '女'
        |           else '未知'
        |           end as gender, customer_point,
        |       register_time, level_name, customer_money, province, city, address, modified_time
        |from (select *, row_number() over (partition by customer_id order by modified_time) as seq
        |      from (select ci.customer_id, customer_name, identity_card_no, gender, customer_point,
        |                   register_time, level_name, customer_money, province, city, address, ca.modified_time
        |            from dwd.dim_customer_inf as ci
        |                     inner join dwd.dim_customer_addr as ca on ci.customer_id=ca.customer_id
        |                     inner join dwd.dim_customer_level_inf as cl on  ci.customer_level=cl.customer_level))
        |where seq=1
        |""".stripMargin)
    df.write.mode(SaveMode.Overwrite).format("hive").saveAsTable("dwd.dim_customer_services")
    df.printSchema()
    // 关闭资源
    spark.stop()
  }
}
