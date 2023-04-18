package com.example.controller;

import com.example.dao.IInfoStudentStateDao;
import com.example.pojo.InfoStudentState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class InfoStudentStateController {
    @Autowired
    private IInfoStudentStateDao infoStudentStateDao;

    @RequestMapping("/list")
    public @ResponseBody String queryList() throws JsonProcessingException {
        List<InfoStudentState> list = infoStudentStateDao.queryList("2023-04-14");
        return new ObjectMapper().writeValueAsString(list);
    }

}
