package com.liu.dao.impl;

import com.liu.dao.INewsDao;
import com.liu.pojo.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("newsDao")
public class NewsImpl implements INewsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<News> queryNews() {
        String sql = "select id , content from news";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<News>(News.class));
    }


    @Override
    public News mapResult(ResultSet rs) throws SQLException {
        return null;
    }

}
