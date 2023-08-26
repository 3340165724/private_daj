package com.dsj.stage2

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import java.util.Properties

object _3_FullExtractionDynamic {
  def main(args: Array[String]): Unit = {
    // 用 spark-shell 输入多行命令
    // scla-shell里直接输入  :paste 命令，黏贴后结束按ctrl+D
    val spark = SparkSession.builder().enableHiveSupport().getOrCreate()

    // 从MySQL中拿出数据
    val url = "jdbc:mysql://172.20.37.230:3306/shtd_student?useSSL=false&characterEncoding=UTF-8"
    val prop = new Properties()
    prop.setProperty("user", "root")
    prop.setProperty("password", "123456")
    val df = spark.read.jdbc(url, "tb_student", prop)

    import spark.implicits._

    /*
    * todo 对dataframe数据进行处理
    *  dataframe
    *  withColumn()：对某一列进行修改，有列就修改，没有列就新增
    *  regexp_replace()：替换，将某列中的某个字符替换为其他字符
    *  date_format()：指定格式，指定某一列的数据格式
    *   //给固定值
    *   df.withColumn("列名",lit("值"))
    *   //判断给值
    *   df.withColumn("列名",when(col("列").equalTo("值"),"取值1").otherwise("取值2"))
    *   //替换处理
    *   df.withColumn("列名",regexp_replace(col("列名"),"被替换","替换后"))
    *   //截取处理
    *   df.withColumn("列名",col("列名").substr(开始下标,截取长度))
    *   //日期格式处理
    *   df.withColumn("列名",date_format(col("列名"),"yyyyMMdd HH:mm:ss"))
    *   //类型转换
    *   df.withColumn("列名",(col("列名").cast("double"))
    *
    *
    * todo  RDD
    *  replace：替换
    *  substring：截取
    *
    * */
    val df_1 = df.withColumn("reg_month", regexp_replace(col("reg_date"), "_", "").substr(0, 6))
    val df_2 = df.withColumn("reg_month",date_format(col("reg_date"),format = "yyyyMM"))
    val df_3 = df.rdd.map(x=>{
      val month_date = x.getDate(7).toString.replace("_","").substring(0,6)
      (x.getInt(0),x.getString(1),x.getInt(2),x.getDate(3),x.getString(4), x.getString(5),x.getInt(6),x.getDate(7),month_date)
    }).toDF("sid", "sname", "sex", "birthday", "phone", "address", "scid", "reg_date", "reg_month")
    /*val df_4 = df.rdd.map(x=>{
      val month_date = x.getDate(7).toString.replace("_","").substring(0,6)
      (x.getInt(0),x.getString(1),x.getInt(2),x.getDate(3),x.getString(4), x.getString(5),x.getInt(6),x.getDate(7),month_date)
    }).toDF()*/

    // 创建临时表
    df_2.createOrReplaceTempView("mystudent")

    // todo 插入hive动态分区表
    // 开启hive动态分区
    spark.sql("set hive.exec.dynamic.partition=true")
    // 设置动态分区的格式= 非严格模式
    spark.sql("set hive.exec.dynamic.partition.mode=nonstrict")
    /*
    * 插入数据
    *  *的最后一列是分区列（reg_month）
    * */
    spark.sql("insert into ods_student.tb_student partition(reg_month) select * from mystudent")

    // 关闭
    spark.stop()
  }
}
