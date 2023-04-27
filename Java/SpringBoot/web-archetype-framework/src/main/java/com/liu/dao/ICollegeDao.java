package com.liu.dao;

import com.liu.pojo.College;

import java.util.List;

public interface ICollegeDao extends IGeneralDao<College>{
    //学院信息
    public List<College> queryCollege();

    // 返校信息
    public List<College> queryReturnCollege();

    // 学生总数
    public Integer querySumStudent();

    // 已返校人数
    public Integer queryReturnSumStudent();

}
