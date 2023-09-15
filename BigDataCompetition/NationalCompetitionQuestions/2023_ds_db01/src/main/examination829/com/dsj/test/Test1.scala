package com.dsj.test


import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SaveMode, SparkSession}

import java.text.SimpleDateFormat
import java.util.Date


object Test1 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    /**
     * todo 第一种操作
     * ods数据取出昨天分区的数据直接插入dwd对应表中(customer_balance_log、customer_login_log、customer_point_log)
     * */
    // 要抽取的表
    val tables = Array("customer_balance_log", "customer_login_log", "customer_point_log")
    tables.foreach(table => {
      // 从ods数据取出昨天分区的数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230911'")
      // 追加到hive的dwd层
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_${table}")
    })

    /*
    * todo 第二种操作
    *  ods中表数据取出和dwd对应表数据取出进行“合并”操作
    *  合并:按时间取最新的一条数据，dwd_insert_time取最早的时间，dwd_modified_time取当前时间，
    *  其他列取(customer_inf、product_info、coupon_info)
    *
    *   思路：先将ods和dwd合并，根据相同id将dwd_insert_time都改为最小的那个时间，按同id的modified_time降序排名seq列，保留排名seq为1的行，删除seq列
    * */
    // 获取系统当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())
    println(currDate)
    // 需要操作的表
    val tables_2_ods = Array("customer_inf", "product_info", "coupon_info")
    // 分别对应需要并的id字段
    val tables_2_id = Array("customer_id", "product_id", "coupon_id")
    println(currDate)
    for (i <- 0 until tables_2_ods.length) {
      // 得到表名
      val table = tables_2_ods(i)
      // 从hive的ods层拿出昨天分区的数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230911'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))

      println(currDate)
      // 从hive的dwd层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")
      // 合并
      val all_df = ods_df.unionByName(dwd_df)
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(tables_2_id(i))))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        .withColumn("seq", row_number().over(Window.partitionBy(tables_2_id(i)).orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1))
        .drop("seq")
      // 创建临时视图
      all_df.createOrReplaceTempView("mytable")
      // 覆盖模式写入hive的dwd表
      spark.sql(s"insert overwrite dwd.dim_${table} select `(etl_date)?+.+`, etl_date from mytable")
    }

    spark.stop()
  }
}
