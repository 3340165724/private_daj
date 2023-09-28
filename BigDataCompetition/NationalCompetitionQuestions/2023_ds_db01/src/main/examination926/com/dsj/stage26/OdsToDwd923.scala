package com.dsj.stage26

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date

object OdsToDwd923 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "true")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    // todo 直接抽取到dwd
    val tables_1 = Array()
    tables_1.foreach(table => {
      var ods_df = spark.sql(s"select * from ods.${table} where etl_date")
        .withColumn("dwd_insert_user",lit("user1"))
        .withColumn("dwd_insert_time",lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user",lit("user1"))
        .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))
      if(table.equals("coupon_use")){
        ods_df = ods_df.withColumn("get_time", to_timestamp(col("get_time"),"yyyyMMddHHmmss"))
          .withColumn("used_time",to_timestamp(col("user_time"),"yyyy-MM-dd HH:mm:ss"))
          .withColumn("pay_time",to_timestamp(col("pay_time"),"yyyy-MM-dd HH:mm:ss"))
      }
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.dim_${table}")
    })

    // todo ods数据和dwd数据合并清洗后合并到dwd
    val ods_tables = Array()
    val ids = Array()
    val dwd_tables = Array()
    for (i <- 0 until ods_tables.length){
      val ods_table = ods_tables(i)
      val dwd_table = dwd_tables(i)
      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230925'")
        .withColumn("dwd_insert_user",lit("user1"))
        .withColumn("dwd_insert_time",lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user",lit("user1"))
        .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))

      val dwd_df = spark.sql(s"select * from dwd.${dwd_table}")

      val all_df = ods_df.unionByName(dwd_df)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(s"${ids(i)}")))
        .withColumn("seq",row_number().over(Window.partitionBy(s"${ids(i)}").orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1)).drop("seq")
        .withColumn("etl_date",lit("20230925"))

      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
    }

    //

    // 关闭资源
    spark.stop()
  }
}
