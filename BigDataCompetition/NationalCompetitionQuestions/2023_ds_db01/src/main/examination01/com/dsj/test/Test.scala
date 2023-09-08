package com.dsj.test

import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.util.Bytes
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
      // 这里的rowKey暂时没用上，比赛看情况
     val rowKey = Bytes.toString(result.getRow)

     /*
     * 下面就是根据列族，列名取出对应的Cell单元格的数据（特别注意：比赛的时候会给一个hbase的表结构，列是什么类型就转为什么类型）
     *  如果hbase中列是int类型，则使用Bytes.toInt()
     *  如果hbase中列是double类型，则使用Bytes.toDouble()
     *  如果hbase中列是字符串或timestamp类型，则使用Bytes.toString,但是最后datafrmae需要单独处理这个为timestamp类型，下面代码有这个处理
     *  。withColumn("modified_time",col("modified_time").cast("timestamp"))
     * */
    val log_id = Bytes.toInt()

    })



  }
}
