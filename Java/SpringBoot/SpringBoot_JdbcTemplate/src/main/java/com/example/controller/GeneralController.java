package com.example.controller;

import com.example.dao.IStudentDao;
import com.example.dao.IUserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 *
 * - 常用的方法属性和注入
 *
 * */
public class GeneralController {
    @Autowired
    protected HttpSession session;

    @Autowired
    protected IUserDao userDao;

    @Autowired
    protected IStudentDao studentDao;

    protected static ObjectMapper jsonMapper = new ObjectMapper();

    protected static ModelAndView modelAndView = new ModelAndView();

}
