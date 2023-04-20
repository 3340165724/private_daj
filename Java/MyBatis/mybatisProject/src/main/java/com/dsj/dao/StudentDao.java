package com.dsj.dao;

import com.dsj.pojo.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentDao {
    @Select("select * from student")
   public List<Student> queryAll();
}
