package dao;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection {
	private static final String URL = "jdbc:mysql://localhost:3306/db_student?characterEncoding=UTF-8";
	private static final String USER = "root";
	private static final String PASSWORD = "123456";
	
	// 加载驱动
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// 连接数据库
	public static Connection getConn() {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 关闭数据库
	public static void closeConn(Connection conn, PreparedStatement pst, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws SQLException {
		System.out.println(getConn().getMetaData().getDriverVersion());
	}
}
