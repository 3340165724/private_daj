package com.dsj._1_student

import org.apache.spark.sql.SparkSession

import java.util.Properties

object FullExtraction {
  def main(args: Array[String]): Unit = {
    // 用 spark-shell 输入多行命令
    // scla-shell里直接输入  :paste 命令，黏贴后结束按ctrl+D

    val spark = SparkSession.builder()
      .appName("HiveTableAndQueryMySQL")
      .master("local")
      .getOrCreate()

    val sc = spark.sparkContext

    // 隐式转换
    import spark.implicits._

    // 利用SparkSession对象从MySQL中拿出需要的数据
    val url = "jdbc:mysql://172.20.37.230:3306/shtd_student?useSSL=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")


    // 不保险 了解
    // 查询数据得到DataFrame
    //    val df = spark.read.jdbc(url, "(select * from tb_class where cid > 15) as t1", prop)
    // val df = spark.read.jdbc(url, "(select * from tb_class where school='云南职业技术学院') as tb", prop)


    // 查询数据得到DataFrame
    val df = spark.read.jdbc(url, "tb_class", prop)

    // 创建临时表
    df.createOrReplaceTempView("myclass")

    // 使用数据库,并插入数据到静态分区
    spark.sql("insert into ods1_student.tb_class partition(etldate='20230221') select * from myclass where cid>15")

    /* todo 打包的方式
     *  方式一 ：右击项目--》Open Module Settings--》Artifacts--》+ JAR--》From modules with dependencies...-->ok-->只保留output(最后一个)--》ok
     *         Build--》Build Artifacts...--》Build
     *  方式二：maven--》Lifecycle--》package--》右击--》Run Maven Build
     *         项目下面target目录下面--》展开--》2022_shtd_student-1.0-SNAPSHOT.jar
     */

    // 多表一起插入
    val tables = Array("t1","t2","t3")
    for(tableName <- tables){
      val df_ = spark.read.jdbc(url,tableName,prop)
      df_.createOrReplaceTempView("tableName")
      spark.sql(s"insert into ods1_student.${tableName} partition(etldate='20230821') select * from tableName")
    }
    spark.stop()
  }
}
