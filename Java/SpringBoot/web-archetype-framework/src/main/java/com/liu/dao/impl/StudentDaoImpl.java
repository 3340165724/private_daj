package com.liu.dao.impl;

import com.liu.dao.IStudentDao;
import com.liu.pojo.Student;
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
    public Integer countStudent() {
        return jdbcTemplate.queryForObject(
                "select count(stu_id) from " + STUDENT,
                Integer.class
        );
    }


    @Override
    public Student mapResult(ResultSet rs) throws SQLException {
        return null;
    }

}
