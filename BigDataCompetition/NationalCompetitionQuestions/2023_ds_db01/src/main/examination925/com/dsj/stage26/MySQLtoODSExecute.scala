package com.dsj.stage26

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.{SaveMode, SparkSession}
import org.apache.spark.sql.functions.lit
import java.text.SimpleDateFormat
import java.util.{Date, Properties}

object MySQLtoODSExecute {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    Logger.getLogger("org").setLevel(Level.ERROR)
    val warehouse = "hdfs://bigdata1:9000/user/hive/warehouse"
    val conf = new SparkConf().setMaster("local").setAppName("read mysql write hive")
      .set("spark.testing.memory", "471859200")
    val sparkSession = SparkSession.builder().enableHiveSupport().config(conf)
      .config("spark.sql.warehouse.dir", warehouse)
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict").getOrCreate()
    //    不设置这个参数会导致抽入hive的数据查询不出来
    sparkSession.conf.set("spark.sql.parquet.writeLegacyFormat", "true")
    /*
    报错信息
    Failed with exception java.io.IOException:org.apache.parquet.io.ParquetDecodingException: Can not read value at 0 in block -1 in file hdfs://bigdata1:9000/user/hive/warehouse/ods.db/order_detail/etldate=20230301/part-00000-909d1f9d-dc21-41c0-9b00-4c2c18b687b7.c000.snappy.parquet
    27107 [a0c73ad9-675e-4a18-8911-d298ce53a09f main] ERROR CliDriver  - Failed with exception java.io.IOException:org.apache.parquet.io.ParquetDecodingException: Can not read value at 0 in block -1 in file hdfs://bigdata1:9000/user/hive/warehouse/ods.db/order_detail/etldate=20230301/part-00000-909d1f9d-dc21-41c0-9b00-4c2c18b687b7.c000.snappy.parquet
    java.io.IOException: org.apache.parquet.io.ParquetDecodingException: Can not read value at 0 in block -1 in file hdfs://bigdata1:9000/user/hive/warehouse/ods.db/order_detail/etldate=20230301/part-00000-909d1f9d-dc21-41c0-9b00-4c2c18b687b7.c000.snappy.parquet
     */

    //    连接数据的参数
    val MYSQLDBURL: String = "jdbc:mysql://172.20.37.85:3306/ds_db01?useUnicode=true&characterEncoding=utf-8" // mysql url地址
    val properties: Properties = new Properties()
    properties.put("user", "root") //用户名
    properties.put("password", "123456") // 密码
    properties.put("driver", "com.mysql.jdbc.Driver") // 驱动名称

    sparkSession.sql("show databases").show()
    val mySqlTable = Array("customer_inf", "order_detail", "order_master", "product_info","coupon_use")
    mySqlTable.foreach(x => {
      println(x)
      sparkSession.read.jdbc(MYSQLDBURL, x,
        properties).createOrReplaceTempView(x)
      if (x.equals("product_info")) {
        val frame = sparkSession.sql(s"select * from $x where modified_time<='2022-09-05 00:00:00'")
          .withColumn("etl_date", lit("20230301"))
        frame.write
          //  .format("parquet")
          .format("hive")
          .mode(SaveMode.Append)
          .partitionBy("etl_date")
          .saveAsTable(s"ods.${x}")
      }
      else if(x.equals("coupon_use")){
        val frame = sparkSession.sql(s"select * from $x where get_time <= '20220318022313'")
          .withColumn("etl_date", lit("20230301"))
        frame.write
          //  .format("parquet")
          .format("hive")
          .mode(SaveMode.Append)
          .partitionBy("etl_date")
          .saveAsTable(s"ods.${x}")
      }
      else {
        val frame = sparkSession.sql(s"select * from $x where modified_time<='2022-08-21 00:00:00'")
          .withColumn("etl_date", lit("20230301"))

        frame.printSchema()
        frame.show(5)
        frame.write
          //        .format("parquet")
          .format("hive")
          .mode(SaveMode.Append)
          .partitionBy("etl_date")
          .saveAsTable(s"ods.${x}")
      }
      println(s"$x 插入成功")
      sparkSession.sql(s"select * from ods.${x} limit 1").show()
    })

    sparkSession.stop()
  }

}
