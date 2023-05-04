package com.liu.dao;

import com.liu.pojo.ChinaMap;

import java.util.List;

public interface IMapDao extends IGeneralDao<ChinaMap> {
    List<ChinaMap> queryList();
}
