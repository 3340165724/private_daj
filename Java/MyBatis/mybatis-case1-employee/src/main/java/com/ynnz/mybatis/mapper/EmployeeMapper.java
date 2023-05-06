package com.ynnz.mybatis.mapper;

import com.ynnz.mybatis.pojo.Employee;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liKaihusing
 * @version 1.0
 * @since 1.0
 */
public interface  EmployeeMapper {

    //新增语句
    @Insert("insert into employee values (null,#{name},#{gender},#{position},#{nationlity})")
    int insert(Employee employee);


    //删除语句
    @Delete("delete from employee where id = #{id}")
    int deleteById(int id);


    //修改语句
    @Update("update employee set  name = #{name}, gender = #{gender},position = #{position},nationlity = #{nationlity} where id = #{id}")
    int update(Employee employee);

    //修改语句
    @Update("update employee set  name = #{name}  where id = #{id}")
    int updateTo(Employee employee);

    //根据id进行查询
    @Select("select id, name, gender, position, nationlity from employee  where id = #{id}")
    Employee queryById(Integer id);

    //全表查询
    @Select("select id, name, gender, position, nationlity from employee ")
    List<Employee> querList();

    @Insert("inset into employee(" +
            "name ," +
            "gender ," +
            "position ," +
            "nationlity" +
            ")" +
            "values(" +
            "#{name}," +
            "#{gender}," +
            "#{position}," +
            "#{nationlity}" +
            ")")
    public Integer save(Employee e);


}
