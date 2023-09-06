package com.dsj.stage2

import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions._

/**
 * 数据流向 MySQL----->hive的ods层
 * */
object IncrementalExtraction {
  /*1、编写 Scala 工程代码，将MySQL的 ds_db01 库中所有表的数据全量抽取到 Hive 的 ods 库中对应表中，
其中coupon_use表取三个日期列最大值作为增量字段，customer_balance_log、customer_point_log表
使用create_time作增量字段，customer_login_log使用login_time作增量字段。字段排序，类型不变，
同时添加静态分区etl_date，分区字段类型为 String，且值为比赛前一天日期（分区字段格式为 yyyyMMdd）。
并在 hive cli 执行 show partitionssupport
ods.表名命令，将结果截图复制粘贴至对应报告中*/

  //spark on yarn方试运行  spark-submit --master yarn --class com.wczy.ods.DsDB01MySqlToOds /opt/spark.jar
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport() // 开启hive支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置分区的模式是非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在用引号引起来的列名称中使用正则表达式
      .getOrCreate()

    /*
    * todo CASE1  以modified_time做为增量字段
    *   将MySQL的 ds_db01 库中所有表的数据全量抽取到 Hive 的 ods 库中对应表中
    *   思路：从MySQL中拿出数据，在hive找到最大的modified_time值
    * */

    // 从MySQL中拿出数据
    val mysql_reader = spark.read.format("jdbc")
      .option("url", "jdbc:mysql://192.168.66.130:3306/ds_db01")
      .option("user", "root")
      .option("password", "123456")

    val etl_date = "20230828"

    val tables = Array("brand_info", "coupon_info", "customer_addr", "customer_inf", "customer_level_inf", "customer_login", "favor_info", "order_cart", "order_detail", "order_master",
      "product_browse", "product_category", "product_comment", "product_info", "product_pic_info", "shipping_info", "supplier_info", "warehouse_info", "warehouse_product")
    tables.foreach(table => {
      //  到hive中去找最大的modified_time值
      val max_modified_time = spark.sql(s"select string(if(max(modified_time) is null,'',max(modified_time))) from 2023_ods1_ds_db01.${table}").first().getString(0)
      // 如果hive对应的表取出了最大日期，则MySQL查询时根据日期增量条件
      var sql = s"select * from ${table} "
      if (!max_modified_time.equals("")) {
        sql += s" where modified_time > '${max_modified_time}'"
      }
      // 此时df装的是从MySQL中取出来的数据
      val df = mysql_reader.option("dbtable", s"(${sql}) t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive，以etl_date为静态分区字段
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_ods1_ds_db01.${table}")
    })


    /*
    * todo CASE2  以create_time为增量的操作
    *   customer_balance_log、customer_point_log表使用create_time作增量字段
    * */
    val tables2 = Array("customer_balance_log", "customer_point_log")
    tables2.foreach(table => {
      val max_create_time = spark.sql(s"select string(if(max(create_time) is null,'',max(create_time))) from 2023_ods1_ds_db01.${table}").first().getString(0)
      // 如果hive对应的表取出了最大日期，则mysql查询时根据日期增量条件
      var sql = s"select * from ${table} "
      if (!max_create_time.equals("")) {
        sql += s" where create_time > '${max_create_time}'"
      }
      // 此时df装的就是从mysql中取出对应的数据
      val df = mysql_reader.option("dbtable", s"(${sql}) t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive，以etl_date为静态分区字段
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_ods1_ds_db01.${table}")
    })

    /*
    * todo CASE3  以login_time增量
    *   customer_login_log使用login_time作增量字段
    * */
    val tables3 = Array("customer_login_log")
    tables3.foreach(table => {
      val max_login_time = spark.sql(s"select string(if(max(login_time) is null,'',max(login_time))) from 2023_ods1_ds_db01.${table}").first().getString(0)
      // 如果hive对应的表取出了最大日期，则mysql查询时根据日期增量条件
      var sql = s"select * from ${table} "
      if (!max_login_time.equals("")) {
        sql += s" where login_time > ${max_login_time}"
      }
      // 此时df装的就是从mysql中取出对应的数据
      val df = mysql_reader.option("dbtable", s"(${sql}) t1").load().withColumn("etl_date", lit(etl_date))
      // 追加模式写入hive，以etl_date为静态分区字段
      df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_ods1_ds_db01.${table}")
    })


    /**
     * todo CASE4
     * coupon_use表取三个日期列最大值作为增量字段
     * */
    // 消费券使用记录表以三列取最大的查询
    val max_time = spark.sql("select if(c is null,'',c) from (select greatest(max(get_time),max(if(used_time='NULL','',used_time)),max(if(pay_time='NULL','',pay_time))) as c from ods.coupon_use) as t1").first().getString(0)
    println(max_time)
    // 方法二：
    val coupon_df = mysql_reader.option("dbtable", "coupon_use").load()
      .where(array_max(array_remove(array("get_time", "used_time", "pay_time"), "NULL")).cast("string") > max_time)
      .withColumn("etl_date", lit(etl_date))
    println(coupon_df.count())
    coupon_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_ods1_ds_db01.coupon_use")

    // 关闭
    spark.stop()

    /* todo 打包的方式
    *   方式一 ：右击项目--》Open Module Settings--》Artifacts--》+ JAR--》From modules with dependencies...-->ok-->只保留output(最后一个)--》ok
    *         Build--》Build Artifacts...--》Build
    *   方式二：maven--》Lifecycle--》package--》右击--》Run Maven Build
    *         项目下面target目录下面--》展开--》2022_shtd_student-1.0-SNAPSHOT.jar
    */
  }
}