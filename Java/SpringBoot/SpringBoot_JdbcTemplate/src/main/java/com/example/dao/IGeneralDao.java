package com.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * 通用 Dao 封装公共属性常量
 *
 */

public interface IGeneralDao<T> {

    public static final String PRIMARY_KEY = "id";
    public static final String USER = "user";
    public static final String STUDENT = "student";

    public T mapResult(final ResultSet rs) throws SQLException;

}
