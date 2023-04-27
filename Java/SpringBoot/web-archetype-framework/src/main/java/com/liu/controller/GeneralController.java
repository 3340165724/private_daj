package com.liu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    @Autowired
    protected IAcademyReleaseDao academyReleaseDao;

    @Autowired
    protected ILateReturnDao lateReturnDao;

    @Autowired
    protected IInfoStudentStateDao infoStudentStateDao;

    @Autowired
    protected IStudentDao studentDao;

    @Autowired
    protected IOutschoolDao outschoolDao;

    @Autowired
    protected IDistributeDao distributeDao;

    protected static ObjectMapper jsonMapper = new ObjectMapper();

    @Autowired
    protected ICollegeDao collegeDao;

    @Autowired
    protected INewsDao newsDao;

}
