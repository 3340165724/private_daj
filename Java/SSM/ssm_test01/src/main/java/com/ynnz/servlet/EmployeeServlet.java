package com.ynnz.servlet;

import com.github.pagehelper.PageInfo;
import com.ynnz.pojo.Employee;

import java.util.List;


public interface EmployeeServlet {
    //��ҳ��ѯ
    PageInfo<Employee> getEmployeePage(Integer pageNum);

    //ȫ��
    List<Employee> getAllEmployee();


}
