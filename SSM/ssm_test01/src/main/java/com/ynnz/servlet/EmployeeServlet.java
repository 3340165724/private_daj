package com.ynnz.servlet;

import com.github.pagehelper.PageInfo;
import com.ynnz.pojo.Employee;

import java.util.List;


public interface EmployeeServlet {
    //分页查询
    PageInfo<Employee> getEmployeePage(Integer pageNum);

    //全查
    List<Employee> getAllEmployee();


}
