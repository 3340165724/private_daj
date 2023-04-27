package com.liu.dao;

import com.liu.pojo.InfoStudentState;

import java.util.List;

public interface IInfoStudentStateDao extends IGeneralDao<InfoStudentState> {

    List<InfoStudentState> queryList(String date);

    // 累计请假
    public Integer countQJStudent();

    // 累计销假
    public Integer countXJStudent();

    // 当日请假
    Integer countQJDay(String date);

    // 当日销假
    Integer countXJDay(String date);
}
