package com.ynnz.servlet;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ynnz.mapper.EmployeeMapper;
import com.ynnz.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServletImp implements EmployeeServlet{

    @Autowired
    private EmployeeMapper employeeMapper;

    //分页
    @Override
    public PageInfo<Employee> getEmployeePage(Integer pageNum) {
        //开启分页功能
        PageHelper.startPage(pageNum,3);
        //查询所有的员工信息
        List<Employee> list = employeeMapper.getAllEmployee();
        //获取分页相关数据
        PageInfo<Employee> page = new PageInfo<Employee>(list,5);
        return page;
    }

    //全查
    @Override
    public List<Employee> getAllEmployee() {
        return employeeMapper.getAllEmployee();
    }



}
