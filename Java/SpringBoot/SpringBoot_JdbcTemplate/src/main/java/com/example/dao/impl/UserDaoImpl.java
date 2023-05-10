package com.example.dao.impl;

import com.example.dao.IUserDao;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

/*
 * - 实现接口到的方法
 *
 * - @Repository("userDao") : 为DAO层的组件类提供一个托管机制，方便统一管理和访问数据库
 *
 * */
@Repository("userDao")
public class UserDaoImpl implements IUserDao {
    // 注入 jdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // TODO CASE1
    /**
     * - 无条件查询结果集
     *
     * - 全表查询， 前端数据与数据库数据匹配
     *
     */
    @Override
    public List<User> queryUser() {

        // 定义SQL
        String sql = "select id, username, password from " + USER;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
    }

    // TODO CASE2
    /**
     *
     * - 带数据查询
     *
     * - 前端动态数据查询，在与数据库中密码匹配
     *
     * */
    @Override
    public User queryByUsername(String username) {
        // 定义SQL
        String sql = "select id, username, password from " + USER + " where username='" + username + "'";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> this.mapResult(rs));
    }
}
