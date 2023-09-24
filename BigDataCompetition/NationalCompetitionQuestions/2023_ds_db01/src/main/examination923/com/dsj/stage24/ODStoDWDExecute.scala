package com.dsj.stage24

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.functions.{current_timestamp, date_format, lit}
import org.apache.spark.sql.{SaveMode, SparkSession}

object ODStoDWDExecute {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    Logger.getLogger("org").setLevel(Level.ERROR)
    val warehouse = "hdfs://bigdata1:9000/user/hive/warehouse"
    val conf = new SparkConf().setMaster("local").setAppName("read ods write dwd")
      .set("spark.testing.memory", "471859200")
    val sparkSession = SparkSession.builder().enableHiveSupport().config(conf)
      .config("spark.sql.warehouse.dir", warehouse).getOrCreate()
    //    不设置这个参数会导致抽入hive的数据查询不出来
    sparkSession.conf.set("spark.sql.parquet.writeLegacyFormat", "true")

    sparkSession.sql("set hive.exec.dynamic.partition=true")
    // 允许所有的分区列都是动态分区列
    sparkSession.sql("set hive.exec.dynamic.partition.mode=nonstrict")
    sparkSession.sql("set hive.exec.max.dynamic.partitions=5000")

    val mySqlTable = Array("customer_inf", "product_info")
    mySqlTable.foreach(x => {
      val frame = sparkSession.sql(s"select * from ods.$x  where etl_date='20230301'")
        //        dwd_insert_user、dwd_insert_time、dwd_modify_user、dwd_modify_time
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(lit(date_format(current_timestamp(), "yyyy-MM-dd HH:mm:ss")).cast("timestamp")))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(lit(date_format(current_timestamp(), "yyyy-MM-dd HH:mm:ss")).cast("timestamp")))
      frame.write
        //  .format("parquet")
        .mode(SaveMode.Overwrite)
        .partitionBy("etl_date")
        .saveAsTable(s"dwd.dim_${x}")
      println(s"$x 插入成功")
      sparkSession.sql(s"select * from dwd.dim_${x} limit 1").show()
    })

    sparkSession.stop()
  }
}
