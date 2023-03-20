package com.dsj.application.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dsj.application.mapper.UserMapper;
import com.dsj.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

// @Controller // 接收客户端请求 返回界面
@RestController // 接收客户端请求 返回数据
//@RequestMapping("/")
public class UserController {

    @Autowired
    private UserMapper userMapper;


    @RequestMapping("/")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    /*
    @PostMapping("/login") 注解将该方法映射到 POST 请求的 /login 路径上
    该方法使用 @RequestParam 注解获取了用户名和密码的值
     */
    @RequestMapping("/doLogin")
    public ModelAndView doLogin(String username, String password) {
        // 判断登录页面输入框中输入的内容与数据库是否相等
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        System.out.println(qw.select(User::getUsername));
        if (qw.select(User::getUsername).equals(username)) {
            System.out.println("1111111111111111111111111");
            if (qw.select(User::getPassword).equals(password)) {
                System.out.println("===============");
                return new ModelAndView("index");
            } else {
                System.out.println("2222222222222222222");
                return new ModelAndView("login");
            }
        }

        System.out.println("3333333333333");
        return new ModelAndView("login");
    }

}

