package com.liu.dao;

import com.liu.pojo.AcademyRelease;

import java.util.List;

public interface IAcademyReleaseDao extends  IGeneralDao<AcademyRelease>{
    public List<AcademyRelease> queryAcademyRelease();
}
