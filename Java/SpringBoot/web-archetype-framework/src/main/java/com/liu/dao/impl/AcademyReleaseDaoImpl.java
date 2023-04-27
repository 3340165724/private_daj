package com.liu.dao.impl;

import com.liu.dao.IAcademyReleaseDao;
import com.liu.pojo.AcademyRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("academyReleaseDao")
public class AcademyReleaseDaoImpl implements IAcademyReleaseDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<AcademyRelease> queryAcademyRelease() {
        String sql = "select id, date, topic, unit \n" +
                "from  \n" + ACADEMY_RELEASE +
                " order by id desc \n" +
                "limit 7;";

        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<AcademyRelease>(AcademyRelease.class));
    }

    @Override
    public AcademyRelease mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}
