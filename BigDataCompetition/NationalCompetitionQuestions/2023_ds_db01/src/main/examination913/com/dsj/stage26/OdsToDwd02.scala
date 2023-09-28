package com.dsj.stage26

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd02 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 获取系统当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    // 需要进行合并操作做的表
    val ods_tables = Array("customer_inf", "product_info")
    val ids = Array("customer_id", "product_id")
    val dwd_tables = Array("dim_customer_inf", "dim_product_info")
    for (i <- 0 until ods_tables.length) {
      // 获取到ods层的表
      val ods_table = ods_tables(i)
      // 获取到dwd层的表
      val dwd_table = dwd_tables(i)
      // 从hive的ods层拿出昨天的分区（任务一生成的分区）数据
      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230912'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))

      // 从dwd层拿出最新分区现有的数据
      val dwd_df = spark.sql(s"select * from dwd.${dwd_table}")

      // 合并 则dwd_insert_time时间不变，dwd_modify_time存当前操作时间，其余列存最新的值
      val all_df = ods_df.unionByName(dwd_df)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(ids(i))))
        .withColumn("seq", row_number().over(Window.partitionBy(ids(i)).orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1))
        .drop("seq")
        .withColumn("etl_date", lit("20230912"))

      // todo 将合并之后的完整数据追加模式写入到dwd中最新分区
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
      //      final_df.createOrReplaceTempView("mytable")
      //      spark.sql(s"insert into dwd.${dwd_table} select * from mytable")    //必须列顺序完全对上
    }

    // 关闭资源
    spark.stop()
  }
}
