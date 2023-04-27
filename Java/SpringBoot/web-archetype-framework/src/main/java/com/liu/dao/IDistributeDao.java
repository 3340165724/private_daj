package com.liu.dao;

import com.liu.pojo.Distribute;

import java.util.List;

public interface IDistributeDao extends IGeneralDao<Distribute> {
    public List<Distribute> queryDistribute();
}
