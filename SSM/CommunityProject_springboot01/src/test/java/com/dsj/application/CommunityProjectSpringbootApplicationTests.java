package com.dsj.application;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dsj.application.controller.UserController;
import com.dsj.application.dao.UserDao;
import com.dsj.application.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityProjectSpringbootApplicationTests {

    @Autowired
    private UserDao userDao;

    @Test
    // 添加数据
    void insertUserData(){
        // 创建实体类对象
        User user = new User();
        user.setId(2);
        user.setUsername("666");
        user.setPassword("123456");
        user.setRepassword("123456");
        userDao.insert(user);
    }

    // 查询所有
    @Test
    void queryAll() {
        List<User> list = userDao.selectList(null);
        System.out.println(list);
    }


    // 分页查询（一定要有分页拦截器）
    @Test
    void page(){
        //第一个参数代表要查询第几页，第二个参数代表那一页有多少条数据
        IPage page = new Page(1,2);
        userDao.selectPage(page,null);
        System.out.println("当前页码值：" + page.getCurrent());
        System.out.println("显示的数据：" + page.getSize());
        System.out.println("一共有多少页：" + page.getPages());
        System.out.println("一共有多少条数据：" + page.getTotal());
        System.out.println("数据：" + page.getRecords());
    }

    @Test
    // 条件查询
    void selectCondition(){
        /*
        // 方式一：用wrapper对象来查询
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("password","123456");
        List<User> list = userDao.selectList(wrapper);
        System.out.println(list);

        // 方式二：用lambda表达式
        QueryWrapper<User> wrapper2 = new QueryWrapper<User>();
        wrapper2.lambda().eq(User::getPassword ,"123456");
        List<User> list2 = userDao.selectList(wrapper2);
        System.out.println(list2);
         */


        // 方式三：lambda表达式
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
       queryWrapper.eq(User::getPassword, "123456");
        List<User> list3 = userDao.selectList(queryWrapper);
        System.out.println(list3);
    }


}
