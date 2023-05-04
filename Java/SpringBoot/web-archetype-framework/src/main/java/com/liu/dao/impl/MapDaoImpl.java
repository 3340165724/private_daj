package com.liu.dao.impl;

import com.liu.dao.IMapDao;
import com.liu.pojo.ChinaMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("mapDao")
public class MapDaoImpl implements IMapDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ChinaMap mapResult(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public List<ChinaMap> queryList() {
        return jdbcTemplate.query("select name, value from map", new BeanPropertyRowMapper<>(ChinaMap.class));
    }
}
