package com.dsj.test

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date


object OdsToDwdTest {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partition", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // todo 直接插入数据到dwd
    // 需要操作的表
    val tables_1 = Array("customer_balance_log", "customer_login_log", "customer_point_log")
    tables_1.foreach(table => {
      // 从ods拿出最新分区的数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230914'")
      // 插入数据到dwd层
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_${table}")
    })

    //todo ods的数据和dwd层数据合并后在插入到dwd
    // 获取当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    val tables_2_ods = Array("customer_inf", "product_info", "coupon_info")
    // 分别对应需要合并的id字段
    val tables_2_id = Array("customer_id", "product_id", "coupon_id")
    for (i <- 0 until tables_2_ods.length) {
      // 取出表名
      val table = tables_2_ods(i)

      // 从ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230914'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      // 从dwd层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")
      // 合并
      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(s"${tables_2_id(i)}")))
        .withColumn("seq", row_number().over(Window.partitionBy(s"${tables_2_id(i)}").orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1))
        .drop("seq")
        .withColumn("etl_date", lit("20230914"))

      // 追加模式写入hive的dwd层
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    }

    // 关闭资源
    spark.stop()
  }
}
