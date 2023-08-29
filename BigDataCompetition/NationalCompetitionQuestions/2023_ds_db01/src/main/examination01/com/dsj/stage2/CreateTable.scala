package com.dsj.stage2

import org.apache.spark.sql.SparkSession

object CreateTable {
  /*编写 scala 工程代码，将 ods库中所有表数据抽取到 Hive 的 dwd 库中对应表中。
  要求如下:若ods表中有涉及到有时间类型，在dwd中都需转为timestamp类型，不记录毫秒数，若原数据中只有年月日.
  则在时分秒的位置添加00:00:00，添加之后使其符合yyyy-MM-dd HH:mm:ss。、
  抽取ods库中各表中昨天的分区数据 (任务一生成的分区) (其中order master、order detail个表数据需要从hbase中取出，然后和ods中对应表join)，
  结合dwd库表中现有的数据，根据主键合并数据到dwd库中的分区表(合并是指对dwd层数据进行插入或修改，需修改的数据以主键为合并字段，
  根据modified time排序取最新的一条)，分区字段为etl date且值与ods库的相对应表该值相等,
  并添加dwd insert user、dwd insert time、 dwd modify user、dwd modify time四列其中dwd insert user、dwd modify user均填写"userl”。
  若该条记录第一次进入数仓dwd层则dwd insert time、dwd modify time均存当前操作时间，并进行数据类型转换。若该数据在进入dwd层时发生了合并修改，
  则dwd insert time时间不变，dwd modify time存当前操作时间,其余列存最新的值。
  使用hive cli执行show partitions dwd.表名命令6、待任务完成以后，
  需删除 ods.order master中的分区，仅保留最近的三个分区。并在 hive cli 执行show partitions ods.order master命令，
  将结果截图粘贴至 对应报告中*/
  def main(args: Array[String]): Unit = {
    // 创建sparksession对象
    val spark = SparkSession.builder().appName("CreateTable")
      .enableHiveSupport()
      .config("hive.exec.dynamic.partition", "true")
      .config("hive.exec.dynamic.partition.mode", "nonstrict")
      .config("hive.exec.max.dynamic.partitions", 2000)
      .getOrCreate()

    // 创建数据库
    spark.sql("create database 2023_ods1_ds_db01")

    // 进入数据库
    spark.sql("use 2023_ods1_ds_db01")
    // 关闭
    spark.stop()
  }
}
