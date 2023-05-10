package com.example.dao.impl;

import com.example.dao.IStudentDao;
import com.example.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("studentDao")
public class StudentDaoImpl implements IStudentDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Student> queryStudentAll(){
        // 定义sql
        String sql = "select id,student_number,student_name,student_class,gender,birth from " + STUDENT;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Student>(Student.class));
    }


    @Override
    public Student mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}

