package com.dsj.test

import org.apache.spark.sql.{SaveMode,SparkSession}
import org.apache.spark.sql.functions._


object Test {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Test")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition","true")
      .config("hive.exec.dynamic.partition.mode","nonstrict")
      .config("hive.exec.max.dynamic.partitions",2000)
      .config("spark.sql.parser.quotedRegexColumnNames","true")
      .getOrCreate()

    // 连接MySQL
    val mysql_reader = spark.read.format("jdbc")
      .option("url","jdbc:mysql://192.168.66.130:3306/ds_db01")
      .option("user","root")
      .option("password","123456")

    val etl_date = "20230828"

    /**
     * todo CASE4
     * coupon_use表取三个日期列最大值作为增量字段
     * */
    // 消费券使用记录表以三列取最大的查询
    val max_time = spark.sql("select if(c is null,'',c) from (select greatest(max(get_time),max(if(used_time='NULL','',used_time)),max(if(pay_time='NULL','',pay_time))) as c from 2023_ods1_ds_db01.coupon_use) as t1").first().getString(0)

    // 从MySQL中拿出数据
    val coupon_df = mysql_reader.option("dbtable","coupon_use").load()
      .where(array_max(array_remove(array("get_time","used_time","pay_time"), "NULL")).cast("string")> max_time)
      .withColumn("etl_date", lit(etl_date))
    coupon_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable("2023_ods1_ds_db01.coupon_use")
  }
}
