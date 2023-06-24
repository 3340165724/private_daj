package com.dsj

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

import java.util.Properties

object _3_DynamicPartitioning {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("DynamicPartitioning")
      .enableHiveSupport()
      .getOrCreate()

    // 引入是用于将DataFrame隐式转换成RDD，使def能够使用RDD中的方法
    import spark.implicits._


    // todo 从MySQL中拿数据
    val url = "jdbc:mysql://192.168.66.130:3306/shtd_student?useSSL=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")
    // 查询数据得到DataFrame
    val df = spark.read.jdbc(url, "tb_student", prop)
    // val df = spark.read.jdbc(url, "(select * from tb_class where school='云南职业技术学院') as tb", prop)


    /*
    * todo 对dataframe数据进行处理
    *  dataframe
    *  withColumn()：对某一列进行修改，有列就修改，没有列就新增
    *  regexp_replace()：替换，将某列中的某个字符替换为其他字符
    *  date_format()：指定格式，指定某一列的数据格式
    *
    * todo  RDD
    *  replace：替换
    *  substring：截取
    * */
    val format1 = df.withColumn("reg_month", regexp_replace(col("reg_date"), pattern = "-", replacement = ""))
    val firmat2 = df.withColumn("reg_month", date_format(col("reg_date"), "yyyyMM"))
    val format3 = df.rdd.map(x => {
      val mother_date = x.getDate(7).toString.replace("-", "").substring(0, 6)
      (x.getInt(0), x.getString(1), x.getInt(2), x.getDate(3), x.getString(4), x.getString(5), x.getInt(6), x.getDate(7), mother_date)
    }).toDF("sid", "sname", "sex", "birthday", "phone", "address", "scid", "reg_date", "reg_month")

    format3.show()

    // 注册临时表
    format3.createOrReplaceTempView("student")

    // todo 插入hive动态分区表
    // 开启hive动态分区
    spark.sql("set hive.exec.dynamic.partition=true")
    // 设置动态分区的格式= 非严格模式
    spark.sql("set hive.exec.dynamic.partition.mode=nonstrict")
    // 插入数据
    spark.sql("insert into ods1.tb_student partition( reg_month) select * from student")

    // todo 删除动态分区表其中几个分区
    spark.sql("alter table tb_student drop partition(reg_month < '201910')")

    // 将性的的0或1换成男或女
   val changeFormat =   df.withColumn("sex",when(col("sex").equalTo(0),lit("男")).otherwise(lit("女")))

    changeFormat.show()

    spark.stop()
  }
}







