package com.ynnz.mybatis.print;

import com.ynnz.mybatis.dao.EmployeeDao;
import com.ynnz.mybatis.pojo.Employee;

import java.util.List;

public class EmployeePrint {
    public static void main(String[] args) {
        EmployeeDao employeeDao = new EmployeeDao();
//        Employee employee = new Employee(null,"黄龙","男","文山--8号技师","China");
//        int insert = employeeDao.insert(employee);
//        System.out.println(insert);

        List<Employee> list = employeeDao.queryList();
        list.forEach(System.out::println);

        System.out.println("-------------------------------------");


        List<Employee> list1 = employeeDao.queryById(1);
        list1.forEach(System.out::println);


    }
}
