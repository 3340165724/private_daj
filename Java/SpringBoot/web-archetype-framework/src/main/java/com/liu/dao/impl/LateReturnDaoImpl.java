package com.liu.dao.impl;

import com.liu.dao.ILateReturnDao;
import com.liu.pojo.InfoStudentState;
import com.liu.pojo.LateReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("lateReturnDao")
public class LateReturnDaoImpl implements ILateReturnDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<LateReturn> queryLateReturn() {
        String sql = "select  id ,date, student_name,class_name\n" +
                " from  \n" + LATE_RETURN +
                " order by date desc \n" +
                " limit 7;\n";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<LateReturn>(LateReturn.class));

    }

    @Override
    public LateReturn mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}
