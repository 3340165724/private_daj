package com.dsj.stage26

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

object Increment923 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition","true")
      .config("hive.exec.dynamic.partition.mode","true")
      .config("hive.exec.max.dynamic.partitions",2000)
      .config("spark.sql.parser.quotedRegexColumnNames","true")
      .getOrCreate()

    val mysql_read = spark.read.format("jdbc")
      .option("url","jdbc:mysql://172.20.37.85:3306/ds_db01")
      .option("user","root")
      .option("password","123456")

    val etl_date = "20230925"

    // todo 以modified_time作为增量字段
    val tables_1 = Array()
    tables_1.foreach(table => {
      val ods_df = spark.sql(s"select string(if(max(modified_time) is null,'' ,max(modified_time))) from ods.${table}").first().getString(0)
      var sql = s"select * from ${table}"
      if(!ods_df.equals("")){
        sql += " where modified_time > 'ods_df'"
      }
      val df = mysql_read.option("dbtable",s"(${sql}) as t1").load()
        .withColumn("etl_date",lit(etl_date))

      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })


    // todo 以create_time作为增量字段
    val tables_2 = Array()
    tables_2.foreach(table => {
      val ods_df = spark.sql(s"select string(if(max(create_time) is null,'' ,max(create_time))) from ods.${table}").first().getString(0)
      var sql = s"select * from ${table}"
      if(!ods_df.equals("")){
        sql += " where create_time > 'ods_df'"
      }
      val df = mysql_read.option("dbtable",s"(${sql}) as t1").load()
        .withColumn("etl_date",lit(etl_date))

      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })



    // todo 以login_time作为增量字段
    val tables_3 = Array()
    tables_3.foreach(table => {
      val ods_df = spark.sql(s"select string(if(max(login_time) is null,'' ,max(login_time))) from ods.${table}").first().getString(0)
      var sql = s"select * from ${table}"
      if(!ods_df.equals("")){
        sql += " where login_time > 'ods_df'"
      }
      val df = mysql_read.option("dbtable",s"(${sql}) as t1").load()
        .withColumn("etl_date",lit(etl_date))

      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.${table}")
    })


    // todo 三个字段求最大值，最大值作为增量字段
    val ods_df = spark.sql("select greatest(max(get_time), max(if(used_time='NULL','',used_time)), max(if(pay_time='NULL','',pay_time))) from ods.coupon_use").first().getString(0)
    val df = mysql_read.option("dbtable","coupon_use").load()
      .where(array_max(array_remove(array("get_time","used_time","pay_time"),"NULL")).cast("string") > ods_df)
      .withColumn("etl_date",lit(etl_date))

    df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"ods.coupon_use")

    // 关闭资源
    spark.stop()
  }
}
