package com.dsj.stage3


//import com.google.gson.JsonParser

import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.scala.createTypeInformation
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.streaming.api.scala.{OutputTag, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.{FlinkKafkaConsumer, FlinkKafkaProducer}
import org.apache.flink.util.Collector
import org.apache.hbase.thirdparty.com.google.gson.JsonParser

import java.util.Properties

object FlinkConsumeKafka {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)
    env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime)
    // flink对接Kafka中的ods_mall_data数据
    val prop = new Properties()
    prop.setProperty("bootstrap.servers", "192.168.66.130:9092,192.168.66.131:9092,192.168.66.132:9092")
    prop.setProperty("group.id", "aaa")
    val dataStream = env.addSource(new FlinkKafkaConsumer[String]("ods_mall_data", new SimpleStringSchema(), prop))
    val out1 = new OutputTag[String]("order")
    val out2 = new OutputTag[String]("detail")
    val stream = dataStream.process(new ProcessFunction[String, String] {
      override def processElement(i: String, context: ProcessFunction[String, String]#Context, collector: Collector[String]): Unit = {
        // 解析每条数据i，i是一个json格式的数据

        /*
        * todo 方法一：如果提供gson包
        * */
        // 将json格式的字符串解析为json对象
        // val jsonObj = JsonParser.parseString(i).getAsJsonObject()
        // 取出table属性
        // val tableName = jsonObj.get("table").getAsString()

        /*
        * todo 方法二：用hbase包
        * */
        // 将json格式的字符串解析为json对象
        val jsonObj = new JsonParser().parse(i).getAsJsonObject()
        // 取出table属性的值
        val tableName = jsonObj.get("table").getAsString()

        // 取出data属性值（只获取data的内容，具体的内容格式考生请自查），其他的表则无需处理）
        val data = jsonObj.get("data").getAsJsonObject().toString
        if (tableName.equals("order_master")) {
          context.output(out1, data)
        }
        else if (tableName.equals("order_detail")) {
          context.output(out2, data)
        }
        else {
          // 其他默认情况返回
          collector.collect(i)
        }
      }
    })

    // 从stream中取出两个侧流数据
    // 从stream中取出out1侧流数据
    val order_stream = stream.getSideOutput(out1)
    // 从stream中取出out2侧流数据
    val detail_stream = stream.getSideOutput(out2)

    // 将order_stream和detail_stream分别存储到Kafka的dwd层对应的主题中
    order_stream.addSink(new FlinkKafkaProducer[String]("fact_order_master",new SimpleStringSchema(), prop))
    detail_stream.addSink(new FlinkKafkaProducer[String]("fact_order_detail",new SimpleStringSchema(), prop))
    // 将数据备份到HBase中
    order_stream.addSink(new KafkaToHBase("dsj:order_master"))
    detail_stream.addSink(new KafkaToHBase("dsj:order_detail"))

    // 关闭
    env.execute("KafkaOdsDataToDwd")
  }
}
