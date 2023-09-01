package com.dsj.stage3

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}
import org.apache.hbase.thirdparty.com.google.gson.JsonObject

import java.util.Properties
import scala.util.Random

/**
 * flink消费Kafka中的ods_mall_log的数据（数据是从Flume端口实时数据采集来的）
 * */
object FlinkConsumeKafka2 {
  def main(args: Array[String]): Unit = {
    // 获取flink执行环境
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime) // 处理时间
    // 添加数据来源Kafka
    val prop = new Properties()
    prop.setProperty("bootstrap.servers", "192.168.66.130:9092,192.168.66.131:9092,192.168.66.132:9092")
    prop.setProperty("group.id", "aaa")
    val dataStream = env.addSource(new FlinkKafkaConsumer[String]("ods_mall_log", new SimpleStringSchema(), prop))
      .filter(_.startsWith("customer_login_log"))
      .map(x => {
        // customer_login_log:(15858|'20230824230453'|'6.18.122.194'|1);
        // 将数据变成15858|20230824230453|6.18.122.194|1
        val str = x.split(":")(1).replace("\\(|\\)|\\'|\\;", "")
        // 按 | 分隔数据为数组
        val arr2 = str.split("\\|")
        // rowKey就是 0-9 随机数 + 客户id + 登陆时间
        val login_id = Random.nextInt(10) + arr2(0) + arr2(1)
        // 取出每个字段
        val customer_id = arr2(0)
        val login_time = arr2(1)
        val login_ip = arr2(2)
        val login_type = arr2(3)
        // 将其转为json格式
        val obj = new JsonObject()
        obj.addProperty("login_id", login_id)
        obj.addProperty("customer_id", customer_id)
        obj.addProperty("login_time", login_time)
        obj.addProperty("login_ip", login_ip)
        obj.addProperty("login_type", login_type)
        // 返回json字符串
        obj.toString
      })

    // 使用filter也可以或者使用侧流输出流分流
    dataStream.addSink(new FlinkKafkaProducer[String]("dim_customer_login_log", new SimpleStringSchema(), prop))
    // 备份到hbuse（利用工具类将数据写入hbase中）
    dataStream.addSink(new KafkaToHBase("dsj:customer_login_log"))
    // 关闭资源
  }
}
