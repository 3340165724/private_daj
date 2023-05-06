package com.example.controller;

import com.example.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;

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
}
