package com.dsj.stage24

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd917 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport() // 开启hive支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstaict") // 设置分区模式为非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 最大分区数
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // todo 直接抽取到dwd
    // 需要抽取的表
    val tables_1 = Array("customer_balance_log", "customer_login_log", "customer_point_log")
    tables_1.foreach(table => {
      // 从hive的ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230917'")
      // 直接追加模式写入到dwd层的静态分区表
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_${table}")
    })

    // todo 第二种ods的数据取出和dwd的数据合并
    // 获取系统当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    // 需要的表
    val tables_2 = Array("customer_inf", "product_info", "coupon_info")
    val ids = Array("customer_id", "product_id", "coupon_id")
    for (i <- 0 until tables_2.length) {
      // 获取到表名
      val table = tables_2(i)
      // 从ods层拿出最新分区的数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230917'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      // 从dwd层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")

      // 合并并去重
      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time") over (Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq", row_number().over(Window.partitionBy(s"${ids(i)}")))
        .filter(_.getAs("seq").equals(1)).drop("seq")
        .withColumn("etl_date", lit("20230917"))
      // 追加模式写入静态分区表中
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    }


    // 关闭资源
    spark.stop()
  }
}
