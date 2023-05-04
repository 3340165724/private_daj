package com.liu.dao.impl;

import com.liu.dao.IInfoStudentStateDao;
import com.liu.pojo.InfoStudentState;
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

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(InfoStudentState.class));
    }


    // 累计请假
    @Override
    public Integer countQJStudent() {
        String sql = "select sum(count_qj) from " + INFO_STUDENT_STATE;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 累计销假
    @Override
    public Integer countXJStudent() {
        String sql = "select sum(count_xj) from " + INFO_STUDENT_STATE;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 当日请假
    @Override
    public Integer countQJDay(String date) {
        String sql = "select count(count_qj) \n" +
                "from " + INFO_STUDENT_STATE +
                " where date(date)='" + date + "'";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    // 当日销假
    @Override
    public Integer countXJDay(String date) {
        String sql = "select count(count_xj) \n" +
                "from " + INFO_STUDENT_STATE +
                " where date(date)='" + date + "'";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public InfoStudentState mapResult(ResultSet rs) throws SQLException {
        return null;
    }
}
