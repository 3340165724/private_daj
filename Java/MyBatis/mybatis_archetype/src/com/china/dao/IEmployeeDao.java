package com.china.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.china.bean.Employee;

/**
 * 重点！
 * 补充SQL或方法
 */
public interface IEmployeeDao {

	// 1
	@Select("select id, name, gender, age, position, nationality " + //
			"from employee")
	public List<Employee> queryList();

	// 2
	@Select("select id, name, gender, age, position, nationality " + //
			"from employee where id=#{id}")
	public Employee queryById(Integer id);

	// 3
	@Insert("insert into employee(" + 
				"name, " + //
				"gender, " + //
				"age, " + //
				"position, " + //
				"nationality" + //
			") " + //
			"values(" + //
				"#{name}, " + //
				"#{gender}, " + //
				"#{age}, " + //
				"#{position}, " + //
				"#{nationality}" + //
			")")
	public Integer save(Employee e);

	// 4
	@Update("update employee " + //
			"set " + //
				"name=#{name}, " + //
				"gender=#{gender}, " + //
				"age=#{age}, " + //
				"position=#{position}, " + //
				"nationality=#{nationality} " + //
			"where id=#{id}")
	public Integer update(Employee e);

	// 5
	@Delete("delete from employee where id=#{id}")
	public Integer deleteById(Integer id);
}
