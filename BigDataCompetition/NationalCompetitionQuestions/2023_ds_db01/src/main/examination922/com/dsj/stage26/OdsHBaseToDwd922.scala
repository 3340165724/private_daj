package com.dsj.stage26


import org.apache.hadoop.hbase.{CompareOperator, HBaseConfiguration, TableName}
import org.apache.hadoop.hbase.client.{ConnectionFactory, Scan}
import org.apache.hadoop.hbase.filter.{RowFilter, SubstringComparator}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row, SaveMode, SparkSession}
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import scala.jdk.CollectionConverters.asScalaIteratorConverter

object OdsHBaseToDwd922 {
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder()
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "true")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    // 获取当前
    val currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())

    // 创建需要的表
    val ods_tables = Array("order_master", "order_detail", "product_browse")
    val dwd_tables = Array("fact_order_master", "fact_order_detail", "fact_product_browse")
    val hbase_tables = Array("hbase_order_master", "hbase_order_detail", "hbase_product_browse")
    for (i <- 0 until ods_tables.length) {
      // 获取到表名
      val ods_table = ods_tables(i)
      val dwd_table = dwd_tables(i)
      val hbase_table = hbase_tables(i)
      // 从ods层拿出数据
      val ods_df = spark.sql(s"select * from ods.${ods_table} where etl_date='20230921'")
        .drop("etl_date")


      // 从hbase中拿出数据
      val hbase_df = readHBaseDf(hbase_table, ods_df, spark)

      // 联合
      var all_df = ods_df.unionByName(hbase_df).coalesce(1)
        .withColumn("dwd_insert_user", lit("user1"))
        .withColumn("dwd_insert_time", lit(currDate).cast("timestamp"))
        .withColumn("dwd_modify_user", lit("user1"))
        .withColumn("dwd_modify_time", lit(currDate).cast("timestamp"))
        .withColumn("etl_date", lit("20230921"))
      if (ods_table.equals("order_master")) {
        all_df = all_df
          .withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
          .withColumn("shipping_time", to_timestamp(col("shipping_time"), "yyyyMMddHHmmss"))
          .withColumn("pay_time", to_timestamp(col("pay_time"), "yyyyMMddHHmmss"))
          .withColumn("receive_time", to_timestamp(col("receive_time"), "yyyyMMddHHmmss"))
      } else if (ods_table.equals("order_detail")) {
        all_df = all_df
          .withColumn("create_time", to_timestamp(col("create_time"), "yyyyMMddHHmmss"))
      }
      ods_df.printSchema()
      hbase_df.printSchema()
      all_df.printSchema()
      // 追加模式写入hive的dwd层
      all_df.write.mode(SaveMode.Append).format("hive").partitionBy("etl_date").saveAsTable(s"dwd.${dwd_table}")
    }

    // 关闭资源
    spark.stop()
  }


  def readHBaseDf(hbaseName: String, odsDf: DataFrame, spark: SparkSession): DataFrame = {
    // 获取ods的数据结构
    val fields = odsDf.schema.fields

    // 创建hbase连接
    val hbaseConf = HBaseConfiguration.create()
    hbaseConf.set("hbase.zookeeper.quorum", "172.20.37.85,172.20.37.237,172.20.37.7")
    hbaseConf.set("hbase.zookeeper.property.clientPort", "2181")
    // 获取连接
    val connection = ConnectionFactory.createConnection(hbaseConf)

    // 创建表对象
    val table = connection.getTable(TableName.valueOf(hbaseName))

    // 创建查询数据方式
    val scan = new Scan()
    // 对rowKey数据过滤
    scan.setFilter(new RowFilter(CompareOperator.EQUAL, new SubstringComparator("20230921")))

    // 执行查询
    val resultScanner = table.getScanner(scan)
    val list = resultScanner.iterator().asScala.map(x => {
      // 根据ods数据结构循环读取hbase中对应的数据
      val values = fields.map(field => {
        // ods中列名
        val name = field.name
        // ods中列的类型
        val tp = field.dataType.typeName
        // 匹配类型
        val value = tp match {
          case "long" => Bytes.toLong(x.getValue("info".getBytes(), name.getBytes()))
          case "integer" => Bytes.toInt(x.getValue("info".getBytes(), name.getBytes()))
          case "double" => Bytes.toBigDecimal(x.getValue("info".getBytes(), name.getBytes())).doubleValue()
          case "float" => Bytes.toFloat(x.getValue("info".getBytes(), name.getBytes()))
          case typa if typa.contains("decimal") => Bytes.toBigDecimal(x.getValue("info".getBytes(), name.getBytes())).doubleValue()
          case "timestamp" => Timestamp.valueOf(Bytes.toString(x.getValue("info".getBytes(), name.getBytes())))
          case _ => Bytes.toString(x.getValue("info".getBytes(), name.getBytes()))
        }
        value
      })
      Row(values: _*)
    }).toList
    // 根据ods的结构和读取构建hbase的数据产生一个新的datafram
    val hbase_df = spark.createDataFrame(spark.sparkContext.makeRDD(list), odsDf.schema)
    // 根据读取出来的数据构建dataframe返回
    hbase_df
  }
}
