package com.dsj.application.controller;

import com.dsj.application.mapper.UserMapper;
import com.dsj.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
//@RequestMapping("/")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public ModelAndView login_() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public @ResponseBody
    String doLogin_(@RequestBody User user) {
        System.out.println(user.getUsername());
        User user_ = userMapper.queryUsername(user.getUsername());
        System.out.println(user_);
        boolean rs = user_ != null && user_.getPassword().equals(user.getPassword());
        System.out.println(rs);
        //  向前端返回 JSON "{\"result\" : false }"
        if(rs){
            return  "{\"result\" : true }";
        }else {
            return  "{\"result\" : false }";
        }
    }



    @RequestMapping("/enroll")
    public ModelAndView enroll() {
        return new ModelAndView("enroll");
    }

    @RequestMapping("/doEnroll")
    public @ResponseBody String doEnroll(@RequestBody User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        String repassword = user.getRepassword();
        Integer result = userMapper.addUsername(username, password, repassword);
        if(result > 0){
            return  "{\"result\" : true }";
        }else {
            return  "{\"result\" : false }";
        }
    }
}
