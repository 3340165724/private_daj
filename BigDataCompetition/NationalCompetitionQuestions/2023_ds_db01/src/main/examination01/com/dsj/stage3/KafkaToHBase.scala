package com.dsj.stage3

import org.apache.flink.streaming.api.functions.sink.{RichSinkFunction, SinkFunction}
import org.apache.hadoop.hbase.client.{Connection, ConnectionFactory, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, HConstants, TableName}
import org.apache.hbase.thirdparty.com.google.gson.JsonParser

import java.text.SimpleDateFormat
import java.util.Date
import scala.util.Random


/**
 * kafka数据备份到hbase的工具类（通用）
 * 将分流后的数据写入hbase中
 * tbName：传入tbName作为区分不同的表名
 * */
class KafkaToHBase(val tbName: String) extends RichSinkFunction[String] {

  /**
   * 该方法的作用是获取一个和hbase的连接对象
   * */
  def getHbaseConn(): Connection = {
    val config = HBaseConfiguration.create()
    config.set(HConstants.ZOOKEEPER_QUORUM, "192.168.66.130:2181, 192.168.66.131:2181,192.168.66.132:2181")
    config.set(HConstants.ZOOKEEPER_CLIENT_PORT, "2181")
    val conn = ConnectionFactory.createConnection(config)
    conn
  }

  // 获取一个和hbase的链接对象
  lazy val conn = getHbaseConn()
  // 根据传入的表名字符串获取hbase中的表对象
  lazy val table = conn.getTable(TableName.valueOf(tbName))
  // 定义一个日期格式化的对象 年月日时分秒毫秒
  val sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS")

  /**
   * 将数据写入hbase对应的表
   * */
  override def invoke(value: String, context: SinkFunction.Context): Unit = {
    // 产生随机的rowKey
    val rowKey = Random.nextInt(10) + sdf.format(new Date())
    // 创建put对象放入rowKey的字节
    val put = new Put(Bytes.toBytes(rowKey))

    // 思路：动态解析json中的key作为列名，value作为hbase中存储的数据值

    // 将value字符串转为json对象
    val jsonObj = new JsonParser().parse(value).getAsJsonObject()
    // 对json对象进行迭代
    val iter = jsonObj.entrySet().iterator()
    // 如果没有就结束
    while (iter.hasNext) {
      // 拿出key-value数据对
      val entry = iter.next()
      // 拿出key
      val key = entry.getKey
      // 拿出value
      val value = entry.getValue.getAsString
      // 添加一个列和数据单元格
      put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(key), Bytes.toBytes(value))
    }
    // 写入一行数据
    table.put(put)
  }

  /**
   * 关闭连接释放资源
   * */
  override def close(): Unit = {
    table.close()
    conn.close()
  }
}
