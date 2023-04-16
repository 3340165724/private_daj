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

    //分页查询
    @RequestMapping(value = "/employee/page/{pageNum}", method=RequestMethod.GET)
    public  String getEmployeePage(@PathVariable("pageNum") Integer pageNum,Model model){
        //获取员工的分页信息
        PageInfo<Employee> page = employeeServlet.getEmployeePage(pageNum);
        //将分页数据共享到请求域中
        model.addAttribute("page", page);
        //跳转到employee_list.html
        return "employee_list";
    }

    //全查
    @RequestMapping(value = "/employee",method = RequestMethod.GET)
    public String getAllEmployee(Model model){
        //查询所有的员工信息
       List<Employee> list = employeeServlet.getAllEmployee();
       //将员工信息在请求域中共享
        model.addAttribute("list",list);
        //跳转到employee_list.html
        return "employee_list";
    }
}
