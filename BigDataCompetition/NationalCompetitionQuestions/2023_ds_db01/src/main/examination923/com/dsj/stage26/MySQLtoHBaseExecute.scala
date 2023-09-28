package com.dsj.stage26

import java.text.SimpleDateFormat
import java.util.{Properties, Random}

import org.apache.hadoop.hbase.client.Put
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapred.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants}
import org.apache.hadoop.mapred.JobConf
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/**

HBase中数据结构如下：
product_id: long
product_core: string
product_name: string
bar_code: string
brand_id: long
one_category_id: integer
two_category_id: integer
three_category_id: integer
supplier_id: long
price: decimal(8,2)
average_cost: decimal(18,2)
publish_status: integer
audit_status: integer
weight: BigDecimal
length: BigDecimal
height: BigDecimal
width: BigDecimal
color_type: string
production_date: string
shelf_life: integer
descript: string
indate: string
modified_time: string
*/
//ods中的表结构如下：
/*
product_id          	int
product_core        	string
product_name        	string
bar_code            	string
brand_id            	int
one_category_id     	int
two_category_id     	int
three_category_id   	int
supplier_id         	int
price               	double
average_cost        	double
publish_status      	int
audit_status        	int
weight              	double
length              	double
height              	double
width               	double
color_type          	string
production_date     	timestamp
shelf_life          	int
descript            	string
indate              	timestamp
modified_time       	timestamp
etl_date            	string
 */
//读取mysql中商品表数据中的2022-10-01和2022-11-01的数据写入hbase中
object MySQLtoHBaseExecute {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val conf = new SparkConf().setMaster("local").setAppName("read mysql write hive")
      .set("spark.testing.memory", "471859200")
    val sparkSession = SparkSession.builder().config(conf).getOrCreate()
    //    连接数据的参数
    val MYSQLDBURL: String = "jdbc:mysql://172.20.37.85:3306/ds_db01?useUnicode=true&characterEncoding=utf-8&useSSL=false" // mysql url地址
    val properties: Properties = new Properties()
    properties.put("user", "root") //用户名
    properties.put("password", "123456") // 密码
    properties.put("driver", "com.mysql.jdbc.Driver") // 驱动名称
    val mySqlTable = Array( "order_master","order_detail","product_browse")
    mySqlTable.foreach(x => {
      sparkSession.read.jdbc(MYSQLDBURL, x,
        properties).createTempView(x)
      val mysql = s"select * from $x where date_format(modified_time,'yyyy-MM-dd') = '2022-10-01' or date_format(modified_time,'yyyy-MM-dd') = '2022-11-01'"
  var frame = sparkSession.sql(mysql)
    .withColumn("modified_time",date_format(col("modified_time"),"yyyy-MM-dd HH:mm:ss.SSSS"))
      val schemass = frame.schema.fields
        //将数据写入hbase中
        val config = HBaseConfiguration.create()
      config.set(HConstants.ZOOKEEPER_QUORUM, "172.20.37.85,172.20.37.237,172.20.37.7")
      config.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181")
      val cols = frame.columns //所有列
      val sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      val sdf2 = new SimpleDateFormat("yyyyMMddHHmmss")
      val putrdd = frame.rdd.map(x=>{
        val date = x.getAs("modified_time").toString
        val rowkey = new Random().nextInt(10)+sdf2.format(sdf1.parse(date))
        val put = new Put(rowkey.getBytes())
        for (i <- 0 until cols.length) {
          val col = cols(i)
          val info = x.getAs(cols(i)).toString
          val fieldName = schemass(i).name
          val fieldType = schemass(i).dataType.typeName
          if (fieldType.equals("long")) {
            //mysql中的int对应了long类型
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getLong(i)))
          }
          else if (fieldType.equals("integer")) {
            //mysql中的tinyint对应了integer
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getInt(i)))
          }
          else if (fieldType.equals("double")) {
            //mysql中的double对应了BigDecimal
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(new java.math.BigDecimal(x.getDouble(i))))
          }
          else if(fieldType.equals("float")){
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getFloat(i)))
          }
          else if (fieldType.contains("decimal")) {
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getDecimal(i)))
          }
          else if(fieldType.equals("timestamp")){
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getTimestamp(i).toString))
          }
          else {
            put.addColumn("info".getBytes(), col.getBytes(), Bytes.toBytes(x.getString(i)))
          }
        }
        (new ImmutableBytesWritable(), put)
    })
      putrdd.foreach(println)
      val job=new JobConf(config)
      job.setOutputFormat(classOf[TableOutputFormat])
      job.set(TableOutputFormat.OUTPUT_TABLE,"hbase_"+x)
      putrdd.saveAsHadoopDataset(job)
      println(s"$x 插入成功")
    })
    sparkSession.stop()
  }

}
