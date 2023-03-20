package com.dsj.application.mapper;

import com.dsj.application.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper_axios {
    @Select("select id ,username, password from tb_user where username=#{username}")
    public User queryUsernamr(@Param("username") String username);
}
