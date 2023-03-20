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
@RequestMapping("/list_axios")
public class ListController_axios {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping("list")
    public ModelAndView list(){
        return new ModelAndView("index_axios");
    }


    // json格式的数据
    @RequestMapping("/jsonList")
    public  @ResponseBody
    String  jsonList() throws JsonProcessingException {
        List<UserInfo> list = userInfoMapper.queryList();
        // 创建一个实例ObjectMapper并使用它的writeValueAsString()方法将列表序列化为 JSON 字符串
        return new ObjectMapper().writeValueAsString(list);
    }

}
