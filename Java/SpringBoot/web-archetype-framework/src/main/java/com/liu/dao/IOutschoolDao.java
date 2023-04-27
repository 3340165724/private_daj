package com.liu.dao;

import com.liu.pojo.Outschool;

public interface IOutschoolDao extends IGeneralDao<Outschool>{
    // 出校人数
    public Integer queryOutDate(String date);

    // 返校人数
    public Integer queryReturnDate(String date);

}
