package com.dsj.stage26


import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

object db {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .enableHiveSupport() // 开启hive支持
      .config("hive.exec.dynamic.partition", "true") // 开启动态分区
      .config("hive.exec.dynamic.partition.mode", "nonstaict") // 设置分区模式为非严格模式
      .config("hive.exec.max.dynamic.partitions", 2000) // 最大分区数
      .config("spark.sql.parser.quotedRegexColumnNames", "true")
      .getOrCreate()

    val cal = Calendar.getInstance()
    cal.set(Calendar.YEAR,2022)
    cal.set(Calendar.MONTH,7)
    cal.set(Calendar.DAY_OF_MONTH,10)

    val cal2 = Calendar.getInstance()
    cal2.set(Calendar.YEAR,2022)
    cal2.set(Calendar.MONTH,2)
    cal2.set(Calendar.DAY_OF_MONTH,16)

    val sdf =new SimpleDateFormat("yyyy-MM-dd")

    while(cal.getTime.after(cal2.getTime)){
      val date1 = sdf.format(new Date(cal.getTimeInMillis))
      cal.add(Calendar.DAY_OF_MONTH,-21)

      val date2 = sdf.format(new Date(cal.getTimeInMillis))
            val sql =
              s"""
                |select concat('${date2}','_','${date1}') as datestr,customer_id, count(*) as zs
                |from (select customer_id, week, seq, (week-seq) as cha
                |      from (select customer_id, week, row_number() over (partition by customer_id order by week) as seq
                |            from (select distinct customer_id, weekofyear(login_time) week
                |                  from dwd.dim_customer_login_log
                |                  where date_format(login_time,'yyyy-MM-dd') >= '${date2}' and date_format(login_time,'yyyy-MM-dd') < '${date1}')))
                |group by customer_id, cha
                |having zs >= 3
                |""".stripMargin
          println(sql)
          spark.sql(sql).show()
    }


  }
}
