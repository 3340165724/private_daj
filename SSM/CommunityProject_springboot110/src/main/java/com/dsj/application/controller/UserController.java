package com.dsj.application.controller;

import com.dsj.application.mapper.UserMapper;
import com.dsj.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

// @Controller // 接收客户端请求 返回界面
@RestController // 接收客户端请求 返回数据
//@RequestMapping("/")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    protected HttpSession session;


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
//        QueryWrapper<User> qw = new QueryWrapper<>();
//        User users = userMapper.selectOne(qw.eq("username", username).eq("password", password));
        User user = userMapper.userByUsernameAndPassword(username, password);
        System.out.println(user);
        if (user != null) {
            return new ModelAndView("index");
        } else {
            return new ModelAndView("login");
        }
    }


    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }


    @RequestMapping("/enroll")
    public ModelAndView enroll() {
        return new ModelAndView("enroll");
    }


    @RequestMapping("/doEnroll")
    public ModelAndView doEnroll(String username, String password, String repassword) {
        // 判断密码和确认密码是否相同

        if (password != null && repassword != null) {
            if(password.equals(repassword)){
                Integer result = userMapper.userEnroll(username,password,repassword);
                if(result > 0 ){
                    return new ModelAndView("login");
                }
                return new ModelAndView("enroll");
            }
        }
        return new ModelAndView("enroll");
    }

}

