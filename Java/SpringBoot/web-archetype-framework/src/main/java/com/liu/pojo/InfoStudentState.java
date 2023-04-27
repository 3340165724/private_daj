package com.liu.pojo;

import java.sql.Date;

/**
 * 班级学生去留情况
 */
public class InfoStudentState extends Bean {

    public String className;
    public String classTeacher;
    public Integer count;
    public String countQj;
    public String countXj;
    public Date date;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public String getClassTeacher() {
        return classTeacher;
    }

    public void setClassTeacher(String classTeacher) {
        this.classTeacher = classTeacher;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCountQj() {
        return countQj;
    }

    public void setCountQj(String countQj) {
        this.countQj = countQj;
    }

    public String getCountXj() {
        return countXj;
    }

    public void setCountXj(String countXj) {
        this.countXj = countXj;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
