package com.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IGeneralDao<T> {
    // 常量
    public static final String PRIMARY_KEY = "id";
    public static final String INFO_STUDENT_STATE = "info_student_state";

    public T mapResult(final ResultSet rs) throws SQLException;
}
