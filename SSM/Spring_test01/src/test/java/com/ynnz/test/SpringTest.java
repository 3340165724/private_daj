package com.ynnz.test;

import com.ynnz.spring.HelloWord;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {
    @Test
    public  void test(){
        //获取IOC容器
        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
        //获取IOC容器中的bean
        HelloWord helloworld = (HelloWord) ioc.getBean("hello");
        helloworld.hello();
    }
}
