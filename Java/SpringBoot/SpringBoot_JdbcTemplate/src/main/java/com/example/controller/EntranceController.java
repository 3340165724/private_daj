package com.example.controller;


import com.example.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
// 类级别控制转发
@RequestMapping("/")
public class EntranceController extends GeneralController {

    /*
     *TODO
     * 转发到登录页面
     *
     *  ModelAndView => MV/C
     * Model : Request + Response
     * View  : Page
     */
    @RequestMapping("/login_form")
    public ModelAndView login() {
        return new ModelAndView("login_form");
    }

    @RequestMapping("/login_axios")
    public ModelAndView login_() {
        return new ModelAndView("login_axios");
    }


    /*
     *TODO
     * 登录功能 form提交
     * 两种方式 全表查询和带参查询
     *
     * - @ResponseBody : 将 Java 对象转换为不同的响应体格式 (不需要返回一个视图，而是直接返回一些数据)
     * - @RequestBody : 绑定并自动解析页面 JSON 数据（如果请求体中是 JSON格式的数据，将 JSON 数据解析为对应的 Java 对象来处理）
     * - @PathVariable : 从URL路径中提取参数
     *
     * */
    @RequestMapping(value = "/1-doLogin-form")
    public ModelAndView doLogin_form_1(@RequestBody User user) throws JsonProcessingException {
        // 查询
        List<User> list = userDao.queryUser();
        // 得到输入框中的值
        String username = user.getUsername();
        String password = user.getPassword();

        for (int i = 0; i < list.size(); i++) {
            if (username.equals(list.get(i).getUsername())) {
                if (password.equals(list.get(i).getPassword())) {
                    System.out.println(list.get(i).getUsername());
                    System.out.println(list.get(i).getPassword());
                    session.setAttribute("username", username);
                    return new ModelAndView("index_form");
                } else {
                    return new ModelAndView("login_form");
                }
            } else {
                return new ModelAndView("login_form");
            }
        }
        return null;
    }

    @RequestMapping(value = "/2-doLogin-form")
    public @ResponseBody
    ModelAndView doLogin_form_2(@RequestBody User user) throws JsonProcessingException {

        User user_ = userDao.queryByUsername(user.getUsername());
        System.out.println(user_);
        System.out.println(user.getUsername());
        if (null != user_) {
            if (user_.getPassword().equals(user.getPassword())) {
                session.setAttribute("username", user.getUsername());
                return new ModelAndView("index_form");
            } else {
                return new ModelAndView("login_form");
            }
        } else {
            return new ModelAndView("login_form");
        }
    }


    /*
     *
     * TODO
     *  登录功能 axios提交
     *  两种方式 全表查询和带参查询
     *
     * */
    @RequestMapping(value = "/1-doLogin-axios")
    public @ResponseBody String doLogin_axios_1(@RequestBody User user) throws JsonProcessingException {
        // 查询
        List<User> list = userDao.queryUser();
        // 得到输入框中的值
        String username = user.getUsername();
        String password = user.getPassword();

        for (int i = 0; i < list.size(); i++) {
            if (username.equals(list.get(i).getUsername())) {
                if (password.equals(list.get(i).getPassword())) {
                    System.out.println(list.get(i).getUsername());
                    System.out.println(list.get(i).getPassword());
                    session.setAttribute("username", username);
                    return "{\"result\" : true }";
                } else {
                    return "{\"result\" : false }";
                }
            } else {
                return "{\"result\" : false }";
            }
        }
        return null;
    }

    @RequestMapping(value = "/2-doLogin-axios")
    public @ResponseBody
    String doLogin_axios_2(@RequestBody User user) throws JsonProcessingException {
        User user_ = userDao.queryByUsername(user.getUsername());
        System.out.println(user_);

        if (null != user_) {
            if (user_.getPassword().equals(user.getPassword())) {
                session.setAttribute("username", user.getUsername());
                return "{\"result\" : true }";
            } else {
                return "{\"result\" : false }";
            }
        } else {
            return "{\"result\" : false }";
        }
    }

    /*
     * TODO
     *  转发到首页页面
     *
     * */
    @RequestMapping("/index_form")
    public ModelAndView index() {
        return new ModelAndView("index_form");
    }

    @RequestMapping("/index_axios")
    public ModelAndView index_() {
        return new ModelAndView("index_axios");
    }

}
