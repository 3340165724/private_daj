package com.china.main;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.china.bean.Employee;
import com.china.dao.IEmployeeDao;
import com.china.util.MybatisFactory;

public class Main {

	public static void main(String[] args) {
		
		SqlSession session = MybatisFactory.getSqlSession();
		IEmployeeDao employeeDao = session.getMapper(IEmployeeDao.class);
		
		
		List<Employee> list = employeeDao.queryList();
		list.forEach(System.out::println); 
		
	}
}
