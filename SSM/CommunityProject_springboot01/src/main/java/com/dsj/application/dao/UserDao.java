package com.dsj.application.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsj.application.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {
}
