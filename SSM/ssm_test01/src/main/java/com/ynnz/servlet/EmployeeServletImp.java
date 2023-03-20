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

    //��ҳ
    @Override
    public PageInfo<Employee> getEmployeePage(Integer pageNum) {
        //������ҳ����
        PageHelper.startPage(pageNum,3);
        //��ѯ���е�Ա����Ϣ
        List<Employee> list = employeeMapper.getAllEmployee();
        //��ȡ��ҳ�������
        PageInfo<Employee> page = new PageInfo<Employee>(list,5);
        return page;
    }

    //ȫ��
    @Override
    public List<Employee> getAllEmployee() {
        return employeeMapper.getAllEmployee();
    }



}
