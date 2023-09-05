package com.dsj.test

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}


object Test {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Test").enableHiveSupport()
      .config("hive.exec.dynamic.partition","true")
      .config("hive.exec.dynamic.partition.mode","nonstrict")
      .config("hive.exec.max.dynamic.partitions",2000)
      .getOrCreate()


    // 从MySQL中拿出数据
    val mysql_reader = spark.read.format("jdbc")
      .option("url","jdbc:mysql://192.168.66.130:3306/ds_db01")
      .option("user","root")
      .option("password","123456")

    val etl_date = "20230828"

    val tables = Array("brand_info","coupon_info","customer_addr","customer_inf","customer_level_inf","customer_login","favor_info","order_cart","order_detail","order_master","product_browse","product_category","product_comment","product_info","product_pic_info","shipping_info","supplier_info","warehouse_info","warehouse_product")

    tables.foreach(table => {
      // 在hive中找到最大的modified_time值
      val max_modified_time = spark.sql("select string(if(max(modified_time) is null,'',max(modified_time)) ) from 2023_ods1_ds_db01.${table}").first().getString(0)
      var sql = "select * from ${table}"
      if(!max_modified_time.equals("")){
        sql += s" where modified_time > '${max_modified_time}'"
      }
      val df = mysql_reader.option("dbtable",s"( ${sql}) as t1").load().withColumn("etl_date",lit(etl_date))
      // 写入hive的ods层
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_ods1_ds_db01.${table}")
    })
  }
}
