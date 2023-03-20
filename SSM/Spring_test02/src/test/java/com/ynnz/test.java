package com.ynnz;

import com.alibaba.druid.pool.DruidDataSource;
import com.ynnz.pojo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

public class test {

    @Test
    public void te() throws SQLException {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println(ioc.getBean("Student"));
        System.out.println(ioc.getBean("student01"));
//        DruidDataSource druidDataSource = ioc.getBean(DruidDataSource.class);
//        System.out.println(druidDataSource.getConnection());

    }

    @Test
    public void testDataSource() throws SQLException {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        DruidDataSource dataSource = ioc.getBean(DruidDataSource.class);
        System.out.println(dataSource.getConnection());
    }
}
