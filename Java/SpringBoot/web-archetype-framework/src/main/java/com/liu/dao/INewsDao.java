package com.liu.dao;

import com.liu.pojo.News;

import java.util.List;

public interface INewsDao extends IGeneralDao<News>{
    public List<News> queryNews();
}
