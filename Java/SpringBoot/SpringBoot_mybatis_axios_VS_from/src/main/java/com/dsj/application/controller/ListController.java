package com.dsj.application.controller;

import com.dsj.application.mapper.UserInfoMapper;

import com.dsj.application.model.UserInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;


@RestController
@RequestMapping("/list")
public class ListController {
    @Autowired
   private  UserInfoMapper userInfoMapper;

    @RequestMapping("/")
    public ModelAndView queryList(){

        List<UserInfo> list = userInfoMapper.queryList();
        ModelAndView mv = new ModelAndView("index");
        // addObject()方法用于将对象添加到模型
        mv.addObject("list", list);

        return mv;
    }


}
