package com.dsj.stage4


import java.text.SimpleDateFormat
import java.util.Date
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

// dim_开头的表叫维度表（数据会发生更新）
// Fact_开头的表叫事实表（数据不会被修改，只会新增）

/**
 * ods的数据经过处理到dwd中 */
object OdsToDwd {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置分区的模式是非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在用引号引起来的列名称中使用正则表达式
      .getOrCreate()


    /**
     * todo 第一种操作
     * ods数据取出昨天分区的数据直接插入dwd对应表中(customer_balance_log、customer_login_log、customer_point_log)
     * */
    // 定义数组
    val tables_1 = Array("customer_balance_log", "customer_login_log", "customer_point_log")
    tables_1.foreach(table => {
      // 从ods层拿出分区数据
      val df = spark.sql(s"select * from 2023_ods1_ds_db01.${table} where etl_date='20230828'")
      // 追加模式写入hive的dwd层，以etl_date为静态分区字段
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_dwd1_ds_db01.fact_${table}")
    })

    /*
    * todo 第二种操作
    *  ods中表数据取出和dwd对应表数据取出进行“合并”操作
    *  合并:按时间取最新的一条数据，dwd_insert_time取最早的时间，dwd_modified_time取当前时间，
    *  其他列取(customer_inf、product_info、coupon_info)
    * */
    // 获取当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    val tables_2_ods = Array("customer_inf", "product_info", "coupon_info")
    // 分别对应需要合并的id字段
    val tables_2_id = Array("customer_id", "product_id", "coupon_id")
    for (i <- 0 until tables_2_ods.length) {
      // 取出当前需要操作的表名
      val table = tables_2_ods(i)
      // 从hive的ods层拿出数据，增加4列的目的是为了ods_df的结构和dwd_df的结构一致，然后才能union
      val ods_df = spark.sql(s"select * from 2023_ods1_ds_db01.${table} where etl_date='20230828'")
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
      // 从hive的dwd层中拿出数据
      val dwd_df = spark.sql(s"select * from 2023_dwd1_ds_db01.dim_${table}")
      ods_df.printSchema()
      dwd_df.printSchema()
      val all_df = ods_df.unionByName(dwd_df)
        // 按id取相同id中dwd_insert_time最小的时间
        .withColumn("dwd_insert_time", min("dwd_insert_time").over(Window.partitionBy(tables_2_id(i))))
        //dwd_modify_time取最新的时间
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        .withColumn("seq", row_number().over(Window.partitionBy(tables_2_id(i)).orderBy(desc("modified_time"))))
        .filter(_.getAs("seq").equals(1)).drop("seq")
      // 思路：先将ods和dwd合并，根据相同id将dwd_insert_time都改为最小的那个时间，按同id的modified_time降序排名seq列，保留排名seq为1的行，删除seq列
      all_df.write.mode(SaveMode.Overwrite).format("hive").partitionBy("etl_date").saveAsTable(s"2023_dwd1_ds_db01.dim_${table}") //存在一个读写的问题
      // 第二种方式写入数据
      //      all_df.createOrReplaceTempView("mytable")
      // 使用insert语句覆盖写入表的时候不存在读写的问题
      //      spark.sql(s"insert overwrite 2023_dwd1_ds_db01.dim_${table} select `(etl_date)?+.+`,etl_date from mytable")
    }

    // 关闭
    spark.stop()
  }
}
