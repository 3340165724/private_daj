package com.ynnz.controller;

import com.github.pagehelper.PageInfo;
import com.ynnz.pojo.Employee;
import com.ynnz.servlet.EmployeeServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeServlet employeeServlet;

    //��ҳ��ѯ
    @RequestMapping(value = "/employee/page/{pageNum}", method=RequestMethod.GET)
    public  String getEmployeePage(@PathVariable("pageNum") Integer pageNum,Model model){
        //��ȡԱ���ķ�ҳ��Ϣ
        PageInfo<Employee> page = employeeServlet.getEmployeePage(pageNum);
        //����ҳ���ݹ�����������
        model.addAttribute("page", page);
        //��ת��employee_list.html
        return "employee_list";
    }

    //ȫ��
    @RequestMapping(value = "/employee",method = RequestMethod.GET)
    public String getAllEmployee(Model model){
        //��ѯ���е�Ա����Ϣ
       List<Employee> list = employeeServlet.getAllEmployee();
       //��Ա����Ϣ���������й���
        model.addAttribute("list",list);
        //��ת��employee_list.html
        return "employee_list";
    }
}
