package com.dsj.application;

import com.dsj.application.mapper.UserMapper;
import com.dsj.application.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityProjectSpringboot110ApplicationTests {

    @Autowired
    private UserMapper userMapper;

//    @Test
//        // 添加数据
//    void insertUserData() {
//        // 创建实体类对象
//        User user = new User();
////        user.setId(3);
//        user.setUsername("123");
//        user.setPassword("123456");
//        user.setRepassword("123456");
//        userMapper.insert(user);
//    }
//
//    // 查询所有
//    @Test
//    void queryAll() {
//        List<User> list = userMapper.selectList(null);
//        System.out.println(list);
//    }
//
//
//    // 分页查询（一定要有分页拦截器）
//    @Test
//    void page() {
//        //第一个参数代表要查询第几页，第二个参数代表那一页有多少条数据
//        IPage page = new Page(1, 2);
//        userMapper.selectPage(page, null);
//        System.out.println("当前页码值：" + page.getCurrent());
//        System.out.println("显示的数据：" + page.getSize());
//        System.out.println("一共有多少页：" + page.getPages());
//        System.out.println("一共有多少条数据：" + page.getTotal());
//        System.out.println("数据：" + page.getRecords());
//    }
//
//    @Test
//        // 条件查询
//    void selectCondition() {
//        /*
//        // 方式一：用wrapper对象来查询
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("password","123456");
//        List<User> list = userDao.selectList(wrapper);
//        System.out.println(list);
//
//        // 方式二：用lambda表达式
//        QueryWrapper<User> wrapper2 = new QueryWrapper<User>();
//        wrapper2.lambda().eq(User::getPassword ,"123456");
//        List<User> list2 = userDao.selectList(wrapper2);
//        System.out.println(list2);
//         */
//
//
//        // 方式三：lambda表达式
//        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(User::getPassword, "123456").select(User::getPassword);
//        List<User> list3 = userMapper.selectList(queryWrapper);
//        User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", "666"));
//        System.out.println(list3);
//        System.out.println(user);
//
//
//
//    }

    @Test
   void doLogin() {
//       User user = userMapper.userByUsernameAndPassword("3340165724", "123456");
//       System.out.println(user);

        Integer user1 = userMapper.userEnroll("456", "123", "123");
        System.out.println(user1);
    }
}
