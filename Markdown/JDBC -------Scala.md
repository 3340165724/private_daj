## JDBC ------->Scala

### 实体类

```
package model

class Student {
  var sid: String = _
  var sname: String = _
  var sage: String = _
  var ssex: String = _

  //写get和set
  def getSid: String = this.sid

  def setSid(sid: String): Unit = {
    this.sid = sid
  }

  def getSname: String = this.sname

  def setSname(sname: String): Unit = {
    this.sname = sname
  }

  def getSage: String = this.sage

  def setSage(sage : String): Unit = {
    this.sage = sage
  }

  def getSsex: String = this.ssex

  def setSsex(ssex: String): Unit = {
    this.ssex = ssex
  }


  override def toString = s"Student($sid, $sname, $sage, $ssex)"
}
```

### JDBC类

```
package dao

import java.sql.{Connection, DriverManager, PreparedStatement, ResultSet}

object DbConnection {
  // 连接数据库所需属性
  private val url = "jdbc:mysql://localhost:3306/db_student"
  private val user = "root"
  private val password = "123456"

  // 加载驱动
  class DbConnection{
    Class.forName("com.mysql.cj.jdbc.Driver")
  }

  // 连接数据库
  def getConn() : Connection =  {
    return DriverManager.getConnection(url,user,password)
  }

  // 关闭数据库
  def closeConn(conn : Connection, pst : PreparedStatement, rs : ResultSet) : Unit = {
    if (conn != null) conn.close
    if (pst != null) pst.close
    if (rs != null) rs.close
  }

  def main(args: Array[String]): Unit = {
    println(getConn())
  }
}
```

### DDL 和 DML类

```
package dao

import model.Student

import java.sql.{Connection, PreparedStatement, ResultSet}
import scala.collection.mutable.ListBuffer

class Tools {
  var conn : Connection = _
  var pst : PreparedStatement = _
  var rs : ResultSet = _

  //增、删、改
  def updateData(sql : String , data: Any*) : Int = {
    //连接数据库
    conn = DbConnection.getConn()
    //预处理sql
    pst = conn.prepareStatement(sql)
    //给？赋值
    for(i <- 0 until  data.length){
      pst.setObject(i + 1, data(i))
    }
    //执行sql
    val result = pst.executeUpdate()
    //关闭资源
    DbConnection.closeConn(conn ,pst ,rs)
    return result
  }


  // 查询
  def selectStudentData(sql : String , data: Any*) : ListBuffer[Student] = {
    //连接数据库
    conn = DbConnection.getConn()
    //预处理sql
    pst = conn.prepareStatement(sql)
    //给？赋值
    for(i <- 0 until  data.length){
      pst.setObject(i + 1, data(i))
    }
    //执行sql
    rs = pst.executeQuery()
    // 创建集合，存结果集
    var list: ListBuffer[Student] = new ListBuffer[Student]
    // 处理结果集
    while (rs.next()) {
      var  s: Student = new Student
      s.setSid(rs.getString("SId"))
      s.setSname(rs.getString("Sname"))
      s.setSage(rs.getString("Sage"))
      s.setSsex(rs.getString("Ssex"))
      // 添加到集合中
      list += s
    }
    //关闭资源
    DbConnection.closeConn(conn, pst, rs)
      return  list
  }
}
```

### 测试类

```
package test

import dao.Tools


object test {
  def main(args: Array[String]): Unit = {
    //定义sql
    val sql : String = "select * from student"
    var t : Tools = new Tools
    var list  = t.selectStudentData(sql);
    for (i <- list){
      println(i)
    }
  }

}

```



























