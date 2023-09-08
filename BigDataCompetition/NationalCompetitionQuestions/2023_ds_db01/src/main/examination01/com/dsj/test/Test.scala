package com.dsj.test

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.spark.sql.SparkSession

import scala.collection.convert.ImplicitConversions.`iterator asScala`


object Test {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("Test")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition","true")
      .config("hive.exec.dynamic.partition.mode","nonstrict")
      .config("hive.exec.max.dynamic.partitions",2000)
      .config("spark.sql.parser.quotedRegexColumnNames","true")
      .getOrCreate()

    // 隐式转换
   import spark.implicits._

    // 创建hbase连接对象
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum","192.168.66.130,192.168.131,192.168.66.132")
    // zookeeper端口
    hbaseConf.set("hbase.zookeeper.property.clientPort","2181")
    // 获取和hbase的链接
    val connection = ConnectionFactory.createConnection(hbaseConf)

    /*
    * todo 第三种操作
    *   ods中数据取出和hbase对应表的数据取出union连接，写入dwd中
    * */

    // 拿出ods中前一个最新分区的数据
    val ods_df = spark.sql(s"select * from 2023_ods1_ds_db01.product_browse where etl_date='20230828'")
    // 从hbase中拿出数据
    val table = connection.getTable(TableName.valueOf("dsj:product_browse")) // 获取表对象
    // 创建查询数据的方式
    val scan = new Scan()
    // 执行查询
    val resultScanner = table.getScanner(scan)
    val hbsae_df = resultScanner.iterator().map(result =>{

    })



  }
}
