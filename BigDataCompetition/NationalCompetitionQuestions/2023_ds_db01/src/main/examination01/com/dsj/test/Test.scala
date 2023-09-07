package com.dsj.test


import org.apache.spark.sql.{SaveMode, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions.Window

object Test {
  def main(args: Array[String]): Unit = {


    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置分区的模式是非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在用引号引起来的列名称中使用正则表达式
      .getOrCreate()


    // 获取当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    val tables_2_ods = Array("customer_inf", "product_info", "coupon_info")
    // 分别对应需要合并的id字段
    val tables_2_id =  Array("customer_id", "product_id", "coupon_id")
    for(i <- 0 until tables_2_ods.length){
      // 取出当前需要操作的表名
      val table = tables_2_ods(i)
      // 从hive的ods层拿出数据，增加4列的目的是为了ods_df的结构和dwd_df的结构一致，然后才能union
      val ods_df = spark.sql(s"select * from 2023_ods1_ds_db01.${table} where etl_date='20230828'")
        .withColumn("dwd_insert_user",lit("user1"))
        .withColumn("dwd_insert_time",lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user",lit("user1"))
        .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))
      // 从hive的dwd层拿出数据
      val dwd_df = spark.sql(s"select * from 2023_dwd1_ds_db01.dim_${table}")

      // 合并数据,合并后可能会有重复数据，需要进行清洗去重
      val all_df = ods_df.unionByName(dwd_df)
        // 按id取相同id中dwd_insert_time最小的时间
        .withColumn("dwd_insert_time",min("dwd_insert_time").over(Window.partitionBy(tables_2_id(i))))
        //dwd_modify_time取最新的时间
        .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))
        .withColumn("seq",row_number().over(Window.partitionBy(tables_2_id(i)).orderBy("modified_time")))
        .filter(_.getAs("seq").equals(1))
        .drop("seq")
      // 写入dwd
      all_df.write.mode(SaveMode.Overwrite).format("hive").partitionBy("etl_date").saveAsTable(s"2023_dwd1_ds_db01.dim_${table}")

    }

  }
}
