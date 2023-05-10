package com.example.dao;

import com.example.pojo.Student;

import java.util.List;

public interface IStudentDao extends IGeneralDao<Student>{

    // 全查
    public List<Student> queryStudentAll();
}
