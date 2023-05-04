package test;

import java.util.List; 
import java.util.Scanner;
import dao.Tools;
import model.Student;

public class test01 {
	static Scanner input = new Scanner(System.in);
	// 创建表头
	static String[] title = new String[] { "序号", "名字", "年龄", "性别" };
	// 创建二维数组
	static Object[][] data = null;
	// 接收结果集
	static List<Student> list = null;

	// 显示结果
	public static void encapsulation() {
		data = new Object[list.size()][title.length];
		System.out.println("序号\t" + "名字\t" + "年龄\t\t\t" + "性别");
		for (int i = 0; i < list.size(); i++) {
			data[i][0] = list.get(i).getSid();
			data[i][1] = list.get(i).getSname();
			data[i][2] = list.get(i).getSage();
			data[i][3] = list.get(i).getSsex();
			System.out.println(data[i][0] + "\t" + data[i][1] + "\t" + data[i][2] + "\t" + data[i][3]);
		}
	}

	public static void main(String[] args) {
		// 全表查询
		String sql = "select SId,Sname, Sage, Ssex from student";
		list = Tools.selectStudentData(sql);
		test01.encapsulation();
//		foreach(o -> System.out.println(o));
		System.out.println();

		// 根据id来查
		String sql2 = "select SId,Sname, Sage, Ssex from student where SId=?";
		System.out.println("请输入id：");
		String id = input.next();
		list = Tools.selectStudentData(sql2, id);
		test01.encapsulation();
		System.out.println();

		// 根据姓名来查
		String sql3 = "select SId,Sname, Sage, Ssex from student where Sname=?";
		System.out.println("请输入名字：");
		String name = input.next();
		list = Tools.selectStudentData(sql3, name);
		test01.encapsulation();
	}
}
