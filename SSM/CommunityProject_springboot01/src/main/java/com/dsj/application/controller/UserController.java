package com.dsj.application.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dsj.application.dao.UserDao;
import com.dsj.application.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller // 接收客户端请求 返回界面
// @RestController // 接收客户端请求 返回数据
public class UserController {
    @Autowired
    private UserDao userDao;

    /*
    @PostMapping("/login") 注解将该方法映射到 POST 请求的 /login 路径上
    该方法使用 @RequestParam 注解获取了用户名和密码的值
     */
    @RequestMapping("/") // web访问时的路径：localhost:8088/login
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(String username, String password) {
        // 判断登录页面输入框中输入的内容与数据库是否相等
        if (username != null || password != null) {
            LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
            if (qw.select(User::getUsername).equals(username)) {
                if (qw.select(User::getPassword).equals(password)) {
                    return "index";
                } else {
                    return "密码错误";
                }
            }
        }
        return null;
    }

    // 注册功能
    @RequestMapping("enroll")
    public String enroll(String username, String password, String repassword) {
        System.out.println(username);
        System.out.println(password);


        return null;
    }
}
