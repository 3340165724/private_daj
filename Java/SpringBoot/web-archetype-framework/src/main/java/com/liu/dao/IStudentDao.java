package com.liu.dao;

import com.liu.pojo.InfoStudentState;
import com.liu.pojo.Student;

import java.util.List;

public interface IStudentDao extends IGeneralDao<Student>{

    // 学生人数
    public Integer countStudent();

}
