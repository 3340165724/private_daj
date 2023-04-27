package com.liu.dao;

import com.liu.pojo.AcademyRelease;
import com.liu.pojo.LateReturn;

import java.util.List;

public interface ILateReturnDao extends IGeneralDao<LateReturn>{

    public List<LateReturn> queryLateReturn();
}
