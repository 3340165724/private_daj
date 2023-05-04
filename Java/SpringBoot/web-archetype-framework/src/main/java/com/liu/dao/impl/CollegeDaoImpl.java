package com.liu.dao.impl;

import com.liu.dao.ICollegeDao;
import com.liu.pojo.College;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("collegeDao")
public class CollegeDaoImpl implements ICollegeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 学院信息查询
     */
    @Override
    public List<College> queryCollege() {
        String sql = "select college, students from college";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(College.class));
    }

    /**
     * 返校信息查询
     */
    @Override
    public List<College> queryReturnCollege() {
        String sql = "select college, returnschool from college";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(College.class));
    }

    // 学生总数
    @Override
    public Integer querySumStudent() {
        String sql = "select sum(students) from college";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 已返校人数
    @Override
    public Integer queryReturnSumStudent() {
        String sql = "select sum(returnschool) from college";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public College mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}
