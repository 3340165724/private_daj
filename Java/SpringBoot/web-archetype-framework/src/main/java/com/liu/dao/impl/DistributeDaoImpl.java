package com.liu.dao.impl;

import com.liu.dao.IDistributeDao;
import com.liu.pojo.Distribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("distributeDao")
public class DistributeDaoImpl implements IDistributeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Distribute> queryDistribute() {
        String sql = "select city, students from distribute";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Distribute>(Distribute.class));
    }

    @Override
    public Distribute mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}
