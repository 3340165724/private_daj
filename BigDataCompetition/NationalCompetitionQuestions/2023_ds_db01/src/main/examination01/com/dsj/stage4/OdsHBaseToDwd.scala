package com.dsj.stage4

import java.text.SimpleDateFormat
import java.util.Date
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}
import org.apache.spark.sql.{SaveMode, SparkSession}
import scala.collection.convert.ImplicitConversions.`iterator asScala`
import org.apache.spark.sql.functions._

//1、取出ods中最新分区的数据dataframe
//2、取出hbase中的数据组装成dataframe（类型的问题要注意）(增加了5列，分别是etl_date、dwd_insert_user等几列)
//3、将ods的dataframe和hbase的dataframe进行合并union
//4、将合并之后的dataframe追加到dwd对应的表中
object OdsHBaseToDwd {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("IncrementalExtraction")
      .enableHiveSupport() // 开启hive支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstrict") // 设置分区的模式是非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 设置分区的数量
      .config("spark.sql.parser.quotedRegexColumnNames", "true") // 允许在用引号引起来的列名称中使用正则表达式
      .getOrCreate()

    // 隐式转换
    import spark.implicits._

    //获取hbase的连接对象
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "192.168.44.61,192.168.44.62,192.168.44.63")
    // zookeeper端口号
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    //获取和hbase的链接
    val connection = ConnectionFactory.createConnection(hbaseConf)


    /*
   * todo 第三种操作
   *  ods中表数据取出和hbase对应表数据取出union连接，写入dwd中
   *  注意:从hbase中取出数据的时候，按给予的hbase表的列类型取出数据，否则取出来是乱码(order_master、order_detail、product_browse)
   * */
    //拿出ods中前一个最新分区(增量)的数据
    val ods_df = spark.sql(s"select * from 2023_ods1_ds_db01.product_browse where etl_date = '20230828'")
    //从hbase中拿出数据
    val table = connection.getTable(TableName.valueOf("dsj:product_browse")) //获取表对象
    //创建查询数据的方式(比赛的时候还要加条件的)
    val scan = new Scan()
    //执行查询
    val resultScanner = table.getScanner(scan)
    val hbase_df = resultScanner.iterator().map(result => {
      // 这里的rowkey暂时没用上，比赛看情况
      val rowkey = Bytes.toString(result.getRow)
      /*
      * 下面就是根据列族、列名取出对应的Cell单元格的数据(特别注意：比赛的时候会给一个hbase的表结构，列是什么类型就转为什么类型)
      *  如果hbase中列是int类型，则使用Bytes.toInt()
      *  如果hbase中列是double类型，则使用Bytes.toDouble()
      *  如果hbase中列是字符串或timestamp类型，则使用Bytes.toString，但是最后datafrmae需要单独处理这个为timestamp类型,下面代码有这个处理
      *  .withColumn("modified_time",col("modified_time").cast("timestamp"))
      * */
      val log_id = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("log_id"))).toInt
      val product_id = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("product_id"))).toInt
      val customer_id = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("customer_id"))).toInt
      val gen_order = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("gen_order"))).toInt
      val order_sn = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("order_sn")))
      val modified_time = Bytes.toString(result.getValue(Bytes.toBytes("info"), Bytes.toBytes("modified_time")))
      (log_id, product_id, customer_id, gen_order, order_sn, modified_time) //将数据组合为元组
    }).toList.toDF("log_id", "product_id", "customer_id", "gen_order", "order_sn", "modified_time") //将列名对应上
      .withColumn("etl_date", lit("20230828")) //新增一个etl_date，目的是为了和ods结构保持一致
      .withColumn("modified_time", col("modified_time").cast("timestamp")) //将日期处理为timestamp格式
    // 获取当前时间
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date)
    // 将ods_df和hbase_df进行union合并（合并的前提：两个dataframe的结构完全一致）
    val ods_hbase_df = ods_df.union(hbase_df) //合并两个数据
      .withColumn("dwd_insert_user", lit("user1")) //因为dwd表中是有这4列的，所以这里新增了4列
      .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
      .withColumn("dwd_modify_user", lit("user1"))
      .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
    // 将数据追加写入到dwd中对应表中 fact开头的表都是追加写入
    ods_hbase_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"2023_dwd1_ds_db01.fact_product_browse")


    //1、取出ods中最新分区的数据dataframe
    //2、取出hbase中的数据组装成dataframe（类型的问题要注意）(增加了5列，分别是etl_date、dwd_insert_user等几列)
    //3、将ods的dataframe和hbase的dataframe进行合并union
    //4、将合并之后的dataframe追加到dwd对应的表中
    //操作order_detail表
    val ods_detail_df = spark.sql("select * from 2023_ods1_ds_db01.order_detail where etl_date='20230828'")
    val detailTable = connection.getTable(TableName.valueOf("dsj:order_detail")) // 获取表对象
    val detailScanner = detailTable.getScanner(scan) // 执行查询
    val hbaseDetailDf = detailScanner.iterator().map(x => {
      val order_detail_id = Bytes.toInt(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("order_detail_id")))
      val order_sn = Bytes.toString(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("order_sn")))
      val product_id = Bytes.toInt(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("product_id")))
      val product_name = Bytes.toString(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("product_name")))
      val product_cnt = Bytes.toInt(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("product_cnt")))
      val product_price = Bytes.toDouble(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("product_price")))
      val average_cost = Bytes.toDouble(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("average_cost")))
      val weight = Bytes.toFloat(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("weight")))
      val fee_money = Bytes.toDouble(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("fee_money")))
      val w_id = Bytes.toInt(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("w_id")))
      val create_time = Bytes.toString(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("create_time")))
      val modified_time = Bytes.toString(x.getValue(Bytes.toBytes("info"), Bytes.toBytes("modified_time")))
      (order_detail_id, order_sn, product_id, product_name, product_cnt, product_price, average_cost, weight, fee_money, w_id, create_time, modified_time)
    }).toList.toDF("order_detail_id", "order_sn", "product_id", "product_name", "product_cnt", "product_price", "average_cost", "weight", "fee_money", "w_id", "create_time", "modified_time")
      .withColumn("etl_date", lit("20230828"))
      .withColumn("modified_time", col("modified_time").cast("timestamp"))
    val detail_all_df = ods_detail_df.unionByName(hbaseDetailDf)
      .withColumn("dwd_insert_user", lit("user1")) // 因为dwd表中是有这4列的，所以这里新增了4列
      .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
      .withColumn("dwd_modify_user", lit("user1"))
      .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
    // 追加模式写入hive，以etl_date为静态分区字段
    detail_all_df.write.mode(SaveMode.Append).format("hive").saveAsTable("2023_dwd1_ds_db01.fact_order_detail")
    spark.stop()
  }
}
