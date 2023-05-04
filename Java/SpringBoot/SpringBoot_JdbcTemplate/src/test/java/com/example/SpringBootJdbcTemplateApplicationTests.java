package com.example;


import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


// 使用注解方式注册配置文件
@Configuration
// 开启组件扫描，注意包路径
@ComponentScan(basePackages = "com.example")
// 指定基础资源配置文件
@PropertySource(value = { "classpath:application.properties" })
// 指定本类为 JUnit 单元测试类
@RunWith(SpringRunner.class)
// 指定本类为 Spring 单元测试类，集合 Spring 测试组件
@SpringBootTest(classes = { SpringBootJdbcTemplateApplicationTests.class })
class SpringBootJdbcTemplateApplicationTests {

//    @Autowired
//    private IInfoStudentStateDao infoStudentStateDao;

//    @Test
//    void contextLoads() {
//    }

//    @Test
//    void queryList(){
//        List<InfoStudentState> list = infoStudentStateDao.queryList("2023-04-14");
//        list.forEach(System.out::println);
//    }
}
