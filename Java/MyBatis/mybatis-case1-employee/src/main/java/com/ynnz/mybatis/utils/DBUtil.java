package com.ynnz.mybatis.utils;

import java.sql.*;

/**
 * JDBC 工具类
 */
public class DBUtil {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String URL = "jdbc:mysql://192.168.66.239:3306/liKaihusing";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    //静态代码块
    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    //封装获取连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

    //释放资源
    public void close(PreparedStatement stmt ,Connection conn,ResultSet rst){
        if (stmt == null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn == null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (rst == null) {
            try {
                rst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
