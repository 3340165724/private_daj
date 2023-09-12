package com.dsj.test

import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

import java.text.SimpleDateFormat
import java.util.Date

// dim_开头的表叫维度表（数据会发生更新）
// Fact_开头的表叫事实表（数据不会被修改，只会新增）

/**
 * ods的数据经过处理到dwd中 */
object Test1 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("Test1")
      .enableHiveSupport() // 开启动态分区
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置模式为非严格模式
      .config("hive.exec.max.dynamic.partitios", 2000) // 设置分区数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在引号引起来的列名称中使用正则表达式
      .getOrCreate()


    /**
     * todo 第一种操作
     * ods数据取出昨天分区的数据直接插入dwd对应表中(customer_balance_log、customer_login_log、customer_point_log)
     * */
    // 定义数组
    val tables_1 = Array("customer_balance_log", "customer_login_log", "customer_point_log")
    tables_1.foreach(table => {
      // 从hive的ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230828'")
      // 写入hive的dwd层
      ods_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${table}")
    })

    /*
   * todo 第二种操作
   *  ods中表数据取出和dwd对应表数据取出进行“合并”操作
   *  合并:按时间取最新的一条数据，dwd_insert_time取最早的时间，dwd_modified_time取当前时间，
   *  其他列取(customer_inf、product_info、coupon_info)
   *
   *   思路：先将ods和dwd合并，根据相同id将dwd_insert_time都改为最小的那个时间，按同id的modified_time降序排名seq列，保留排名seq为1的行，删除seq列
   * */
    // 获取当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    val tables_2_ods = Array("customer_inf","product_info","coupon_info")
    // 需要合并的id字段
    val tables_2_id = Array("customer_id", "product_id", "coupon_id")
    for (i <- 0 until tables_2_ods.length) {
      // 取出需要操作的表名
      val table = tables_2_ods(i)
      // 从hive的ods层拿出数据，增加4列的目的是为了ods_df的结构和dwd_df的结构一致，然后才能union
      val ods_df = spark.sql(s"select * from ods.${table} where etl_date='20230828'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      // 从hive的ods层拿出数据
      val dwd_df = spark.sql(s"select * from dwd.dim_${table}")
      // 合并，去重
      val all_df = ods_df.unionByName(dwd_df)
        // 取出id相同的dwd_insert_user的最小时间
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(tables_2_id(i))))
        // 取出id相同的最新的
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        // 去重：相同id的modified_time值降序排序，只留下第一条数据
        .withColumn("seq", row_number().over(Window.partitionBy(tables_2_id(i)).orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1))
        .drop("seq")

      // 创建临时视图
      all_df.createOrReplaceTempView("mytable")
      // 覆盖到dwd层
      spark.sql(s"insert overwrite dwd.dim_${table} select `(etl_date)?+.+`, etl_date from mytable")
    }
    // 关闭资源
    spark.stop()
  }
}
