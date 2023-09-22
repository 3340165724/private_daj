package com.dsj.stage24

import com.ibm.icu.text.SimpleDateFormat
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

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
    val tables_1 = Array("customer_level_inf","customer_login_log","customer_addr","coupon_use","coupon_info")
    tables_1.foreach(table => {
      // 从ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230921'")
      // 追加模式写入dwd层的静态分区
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_${table}")
    })

    // 获取系统当前时间
    val currDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
    // todo 从ods数据拿出和dwd层合并
    // 需要抽取的表
    val tables_2 = Array("customer_inf","product_info")
    val ids = Array("customer_id","product_id")
    for(i <- 0 until tables_2.length) {
      // 获取到表名
      val table = tables_2(i)
      // ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='202030921'")
        .withColumn("dwd_insert_user",lit("user1"))
        .withColumn("dwd_insert_time",lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user",lit("user1"))
        .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))
      // 从dwd层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")
      // 联合
      val all_df = ods_df.unionByName(dwd_df).coalesce(1)
        .withColumn("dwd_insert_time", min("dwd_insert_time") over(Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq",row_number()over(Window.partitionBy(s"${ids(i)}")))
        .filter(_.getAs("seq").equals(1)).drop("etl_date")
        .withColumn("etl_date",lit("20230921"))
      // 追加模式写入dwd层
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    }

    // 关闭资源
    spark.stop()
  }
}
