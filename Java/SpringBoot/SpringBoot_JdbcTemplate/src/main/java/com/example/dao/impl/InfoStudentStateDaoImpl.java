package com.example.dao.impl;

import com.example.dao.IInfoStudentStateDao;
import com.example.pojo.InfoStudentState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("infoStudentStateDao")
public class InfoStudentStateDaoImpl implements IInfoStudentStateDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<InfoStudentState> queryList(String date) {
        String sql =
                "select " +
                        "id, " +
                        "class_name, " +
                        "class_teacher, " +
                        "count, " +
                        "count_qj, " +
                        "count_xj, " +
                        "date " +
                        " from " + INFO_STUDENT_STATE +
                        " where date(date)='" + date + "'";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<InfoStudentState>(InfoStudentState.class));
    }

    @Override
    public InfoStudentState mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}
