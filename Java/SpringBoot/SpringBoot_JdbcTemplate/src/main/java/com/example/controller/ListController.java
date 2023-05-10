package com.example.controller;

import com.example.pojo.Student;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
// 类级别控制转发
@RequestMapping("/list")
public class ListController extends GeneralController {

    /*
     *
     * [
     * [1,"213510111","张三","计网2","男","2002-04-05 09:15:05"],
     * [2,"213510112","李四","计应1","女","2002-04-06 09:15:18"],
     * [3,"213510113","王五","计应2","男","2002-05-02 09:15:41"],
     * [4,"213510114","赵六","计网1","女","2002-04-24 09:15:51"],
     * [5,"213510115","孙风","计网2","男","2002-05-01 09:15:57"]
     * ]
     *
     * */
    @RequestMapping("/query_student_all")
    public @ResponseBody
    String queryStudentAll() throws JsonProcessingException {
        List<Student> queryStudentAll = studentDao.queryStudentAll();

        // TODO CASE1
     /*   Object[] objects = queryStudentAll.stream().map(o ->{
            Object[] object = new Object[6];
            object[0] = o.getId();
            object[1] = o.getStudentNumber();
            object[2] = o.getStudentName();
            object[3] = o.getStudentClass();
            object[4] = o.getGender();
            object[5] = o.getBirth();
            return object;
        }).toArray();*/

        // TODO case2
        Object[] objects = new Object[queryStudentAll.size()];
        for (int i = 0; i < queryStudentAll.size(); i++) {
            Object[] object = new Object[6];
            object[0] = i + 1;
            object[1] = queryStudentAll.get(i).getStudentNumber();
            object[2] = queryStudentAll.get(i).getStudentName();
            object[3] = queryStudentAll.get(i).getStudentClass();
            object[4] = queryStudentAll.get(i).getGender();
            object[5] = queryStudentAll.get(i).getBirth();
            objects[i] = object;
        }

        return jsonMapper.writeValueAsString(objects);
    }


}
