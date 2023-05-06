package com.ynnz.mybatis.dao;


import com.ynnz.mybatis.pojo.Employee;
import com.ynnz.mybatis.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {
    private static Connection conn;
    private static PreparedStatement pstmt;
    private static ResultSet rst;
    private static Employee employee = new Employee();

    // 新增数据
    public  int insert(Employee employee){
        //编写sql语句
        String sql = "insert into employee(name,gender,position,nationlity ) values (?,?,?,?)";
        int count = 0;

        try {
            //获取连接
            conn = DBUtil.getConnection();

            //获取预编译对象
            pstmt = conn.prepareStatement(sql);

            //给占位符传值
            pstmt.setString(1,employee.getName());
            pstmt.setString(2,employee.getGender());
            pstmt.setString(3,employee.getPosition());
            pstmt.setString(4,employee.getNationlity());

            count = pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
       return count;
    }


    //查询语句
    public List<Employee> queryList(){

        //创建集合
         List<Employee> list = null;
        //编写sql语句
        String sql = "select id, name, gender, position, nationlity from employee";
        try {
            //获取连接
            conn = DBUtil.getConnection();

            //获取预编译对象
            pstmt = conn.prepareStatement(sql);

            //获取查询结果集
            rst = pstmt.executeQuery();

            //list集合
             list = new ArrayList<>();

            while (rst.next()) {
               Employee employee = new Employee();
               employee.setId(rst.getInt("id"));
               employee.setName(rst.getString("name"));
               employee.setGender(rst.getString("gender"));
               employee.setPosition(rst.getString("position"));
               employee.setNationlity(rst.getString("nationlity"));
               list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //根据id进行查询
    public List<Employee> queryById(Integer id){

        //创建集合
        List<Employee> list = null;
        //编写sql语句
        String sql = "select id, name, gender, position, nationlity from employee where id = ?";
        try {
            //获取连接
            conn = DBUtil.getConnection();

            //获取预编译对象
            pstmt = conn.prepareStatement(sql);

            //给占位符传值
            pstmt.setInt(1,id);

            //获取查询结果集
            rst = pstmt.executeQuery();

            //list集合
            list = new ArrayList<>();

            while (rst.next()) {
                employee.setId(rst.getInt("id"));
                employee.setName(rst.getString("name"));
                employee.setGender(rst.getString("gender"));
                employee.setPosition(rst.getString("position"));
                employee.setNationlity(rst.getString("nationlity"));
                list.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


}
