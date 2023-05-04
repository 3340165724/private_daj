package com.liu.dao.impl;

import com.liu.dao.IOutschoolDao;
import com.liu.pojo.Outschool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository("outschoolDao")
public class OutschoolDaoImpl implements IOutschoolDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 出校人数
    @Override
    public Integer queryOutDate(String date) {
        String sql = "select count(id) \n" +
                "from " + OUTSCHOOL +
                " where date(outdate) = " + "'" + date + "'";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Integer> queryOutDate7(String date) {
        String sql =
                "select \n" +
                    "\to.count_\n" +
                "from (\n" +
                    "\tselect \n" +
                    "\t\tdate(outdate) date,\n" +
                    "\t\tcount(id) count_\n" +
                    "\tfrom outschool\n" +
                    "\tgroup by date(outdate)\n" +
                ") o\n" +
                "where \n" +
                    "\tdate(o.date) >= '" + date +"'\n" +
                "and\n" +
                    "\tdate(o.date) < date('" + date + "') + 7";

        return jdbcTemplate.queryForList(sql, Integer.class);
    }

    // 返校人数
    @Override
    public Integer queryReturnDate(String date) {
        String sql = "select count(id) from " + OUTSCHOOL + " where date(returndate)= " + "'" + date + "'";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    @Override
    public List<Integer> queryReturnDate7(String date) {

        return jdbcTemplate.queryForList(
                "select \n" +
                        "\to.count_\n" +
                    "from (\n" +
                        "\tselect \n" +
                            "\t\tdate(returndate) date,\n" +
                            "\t\tcount(id) count_\n" +
                        "\tfrom outschool\n" +
                        "\tgroup by date(returndate)\n" +
                        ") o\n" +
                    "where \n" +
                        "\tdate(o.date) >= '" + date + "'\n" +
                    "and\n" +
                        "\tdate(o.date) < date('" + date + "') + 7",
                Integer.class
        );
    }

    @Override
    public Outschool mapResult(ResultSet rs) throws SQLException {
        return null;
    }

}
