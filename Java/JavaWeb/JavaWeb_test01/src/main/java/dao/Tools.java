package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AddFond;
import model.Student;
import model.User;

public class Tools {
	static Connection conn;
	static PreparedStatement pst;
	static ResultSet rs;

	// 增、删、改
	public static int updateData(String sql, Object... data) {
		// 连接数据库
		conn = DbConnection.getConn();
		try {
			// 预处理sql语句
			pst = conn.prepareStatement(sql);
			// 给？赋值
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			// 执行SQL语句
			return pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.closeConn(conn, pst, rs);
		}
		return 0;
	}

	// 查询Student
	public static List<Student> selectStudentData(String sql, Object... data) {
		// 获取数据库连接
		conn = DbConnection.getConn();
		try {
			// 预处理SQL语句
			pst = conn.prepareStatement(sql);
			// 给？赋值
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			// 执行SQL语句
			rs = pst.executeQuery();
			// 创建集合，存结果集
			List<Student> list = new ArrayList<Student>();
			// 处理结果集
			while (rs.next()) {
				Student s = new Student();
				s.setSid(rs.getString("SId"));
				s.setSname(rs.getString("Sname"));
				s.setSage(rs.getString("Sage"));
				s.setSsex(rs.getString("Ssex"));
				// 添加到集合中
				list.add(s);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbConnection.closeConn(conn, pst, rs);
		}
		return null;
	}

	// 查询User
	public static List<User> selectUserData(String sql, Object... data) {
		// 获取数据库链接
		conn = DbConnection.getConn();

		try {
			// 预处理sql语句
			pst = conn.prepareStatement(sql);
			// 给？赋值
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			// 执行sql语句
			rs = pst.executeQuery();
			// 创建集合，用于存储结果集
			List<User> list = new ArrayList<User>();
			// 处理结果集
			while (rs.next()) {
				// 创建实体类对象
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setUserName(rs.getString("user_name"));
				u.setPassword(rs.getString("password"));
				u.setAdmin(rs.getInt("admin"));
				// 添加到集合
				list.add(u);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	//查询食物 add_fond
	public static List<AddFond> selectAddFondData(String sql, Object... data){
		// 获取数据库链接
				conn = DbConnection.getConn();

				try {
					// 预处理sql语句
					pst = conn.prepareStatement(sql);
					// 给？赋值
					for (int i = 0; i < data.length; i++) {
						pst.setObject(i + 1, data[i]);
					}
					// 执行sql语句
					rs = pst.executeQuery();
					// 创建集合，用于存储结果集
					List<AddFond> list = new ArrayList<AddFond>();
					// 处理结果集
					while (rs.next()) {
						// 创建实体类对象
						AddFond af = new AddFond();
						af.setId(rs.getInt("id"));
						af.setCuisine(rs.getString("cuisine"));
						af.setFoodName(rs.getString("food_name"));
						af.setPrice(rs.getDouble("price"));
						af.setMemberPrice(rs.getDouble("member_price"));
						// 添加到集合
						list.add(af);
					}
					return list;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		return null;
	}
	//查询食物共有多少条的方法 add_fond
	public static int selectCountAddFondData(String sql, Object... data){
		// 获取数据库链接
		conn = DbConnection.getConn();
		
		try {
			// 预处理sql语句
			pst = conn.prepareStatement(sql);
			// 给？赋值
			for (int i = 0; i < data.length; i++) {
				pst.setObject(i + 1, data[i]);
			}
			// 执行sql语句
			rs = pst.executeQuery();
//			// 创建集合，用于存储结果集
//			List<AddFond> list = new ArrayList<AddFond>();
			// 处理结果集
			int count = 0;
//			rs.next();
//			count=rs.getInt(count);
			while (rs.next()) {
				// 创建实体类对象
				AddFond af = new AddFond();
				af.setId(rs.getInt("id"));
				af.setCuisine(rs.getString("cuisine"));
				af.setFoodName(rs.getString("food_name"));
				af.setPrice(rs.getDouble("price"));
				af.setMemberPrice(rs.getDouble("member_price"));
//				 添加到集合
//				list.add(af);
				count ++;
			}
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
}
