package com.dsj.application.mapper;

import com.dsj.application.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    // 登录
    @Select("select id ,username, password from tb_user where username=#{username}")
    public User queryUsername(String username);

    // 注册
    @Insert("insert into tb_user values(null,#{username},#{password},#{repassword})")
    public Integer addUsername(String username,String password, String repassword);
}
