package com.liu.dao;

import com.liu.pojo.Outschool;

import java.util.List;

public interface IOutschoolDao extends IGeneralDao<Outschool>{
    // 出校人数
    public Integer queryOutDate(String date);

    List<Integer> queryOutDate7(String date);

    // 返校人数
    public Integer queryReturnDate(String date);

    List<Integer> queryReturnDate7(String date);
}
