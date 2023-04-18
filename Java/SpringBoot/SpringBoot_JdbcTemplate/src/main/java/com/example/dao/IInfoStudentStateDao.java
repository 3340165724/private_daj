package com.example.dao;

import com.example.pojo.InfoStudentState;
import java.util.List;

public interface IInfoStudentStateDao extends IGeneralDao<InfoStudentState>{

    public List<InfoStudentState> queryList(String date );
}
