package com.liu;

import com.liu.dao.IAcademyReleaseDao;
import com.liu.dao.IInfoStudentStateDao;
import com.liu.dao.ILateReturnDao;
import com.liu.dao.IOutschoolDao;
import com.liu.pojo.AcademyRelease;
import com.liu.pojo.InfoStudentState;
import com.liu.pojo.LateReturn;
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
@ComponentScan(basePackages = "com.liu")
// 指定基础资源配置文件
@PropertySource(value = {"classpath:application.properties"})
// 指定本类为 JUnit 单元测试类
@RunWith(SpringRunner.class)
// 指定本类为 Spring 单元测试类，集合 Spring 测试组件
@SpringBootTest(classes = {WebArchetypeFrameworkApplicationTests.class})
class WebArchetypeFrameworkApplicationTests {

    @Autowired
    private IInfoStudentStateDao infoStudentStateDao;

    @Autowired
    private IAcademyReleaseDao academyReleaseDao;

    @Autowired
    private ILateReturnDao lateReturnDao;

    @Autowired
    protected IOutschoolDao outschoolDao;

    @Test
    void contextLoads() {
    }

    @Test
    void test_1() {

        List<InfoStudentState> list = infoStudentStateDao.queryList("2023-04-14");
        list.forEach(o -> {

            System.out.println(o.getCountQj() + o.getId() + " " + o.getDate());
        });
    }

    @Test
    void test_2() {

        outschoolDao.queryOutDate7("2022-06-06").forEach(System.out::println);
    }

    @Test
    void queryAcademyRelease() {
        List<AcademyRelease> list = academyReleaseDao.queryAcademyRelease();
        list.forEach(o -> {
            System.out.println(o.getDate() + " " + o.topic + " ");
        });
    }

    @Test
    void queryLateReturn() {
        List<LateReturn> list = lateReturnDao.queryLateReturn();
        list.forEach(o -> {
            System.out.println(o.getDate() + " " + o.class_name + " ");
        });
    }

    @Test
    void queryOutDate() {
        Integer i=outschoolDao.queryOutDate("2022-06-13");
        System.out.println(i);
    }
}
