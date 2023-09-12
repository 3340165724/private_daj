package com.dsj.test

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.sql.{SaveMode, SparkSession}
import scala.collection.convert.ImplicitConversions.`iterator asScala`
import org.apache.spark.sql.functions._

object Test2 {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().appName("Task1").enableHiveSupport()
      .config("hive.exec.dynamic.partition","true")
      .config("hive.exec.dynamic.partition.mode","nonstrict")
      .config("hive.exec.max.dynamic.partitions",2000)
      .config("spark.sql.parser.quotedRegexColumnNames","true")
      .getOrCreate()
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date) //获取当前时间
    import spark.implicits._
    //获取hbase的连接对象
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "172.20.37.85,172.20.37.78,172.20.37.237")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181"); // zookeeper端口号
    val connection = ConnectionFactory.createConnection(hbaseConf)  //获取和hbase的链接
    //拿出ods中前一个最新分区(增量)的数据
    val ods_df = spark.sql(s"select * from ods.product_browse where etl_date = '20230828'")
    //从hbase中拿出数据
    val table = connection.getTable(TableName.valueOf("dsj:product_browse"))  //获取表对象
    val scan = new Scan() //创建查询数据的方式(比赛的时候还要加条件的)
    val resultScanner = table.getScanner(scan)  //执行查询
    val hbase_df = resultScanner.iterator().map(result=>{
      val rowkey = Bytes.toString(result.getRow)  //这里的rowkey暂时没用上，比赛看情况
      //下面就是根据列族、列名取出对应的Cell单元格的数据(特别注意：比赛的时候会给一个hbase的表结构，列是什么类型就转为什么类型)
      //如果hbase中列是int类型，则使用Bytes.toInt()
      //如果hbase中列是double类型，则使用Bytes.toDouble()
      //如果hbase中列是字符串或timestamp类型，则使用Bytes.toString，但是最后datafrmae需要单独处理这个为timestamp类型,下面代码有这个处理
      //.withColumn("modified_time",col("modified_time").cast("timestamp"))
      val log_id = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("log_id"))).toInt
      val product_id = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("product_id"))).toInt
      val customer_id = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("customer_id"))).toInt
      val gen_order = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("gen_order"))).toInt
      val order_sn = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("order_sn")))
      val modified_time = Bytes.toString(result.getValue(Bytes.toBytes("info"),Bytes.toBytes("modified_time")))
      (log_id,product_id,customer_id,gen_order,order_sn,modified_time)  //将数据组合为元组
    }).toList.toDF("log_id","product_id","customer_id","gen_order","order_sn","modified_time")  //将列名对应上
      .withColumn("etl_date",lit("20230828"))   //新增一个etl_date，目的是为了和ods结构保持一致
      .withColumn("modified_time",col("modified_time").cast("timestamp")) //将日期处理为timestamp格式
    //将ods_df和hbase_df进行union合并（合并的前提：两个dataframe的结构完全一致）
    val ods_hbase_df = ods_df.union(hbase_df)     //合并两个数据
      .withColumn("dwd_insert_user",lit("user1"))   //因为dwd表中是有这4列的，所以这里新增了4列
      .withColumn("dwd_insert_time",lit(currDate).cast("timestamp"))
      .withColumn("dwd_modify_user",lit("user1"))
      .withColumn("dwd_modify_time",lit(currDate).cast("timestamp"))
    //将数据追加写入到dwd中对应表中 fact开头的表都是追加写入
    ods_hbase_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.fact_product_browse")
    // 关闭资源
    spark.stop()
  }
}
