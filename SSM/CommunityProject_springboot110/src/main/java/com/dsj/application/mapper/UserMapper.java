package com.dsj.application.mapper;


import com.dsj.application.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    // 登录验证
    @Select("select username,`password` from tb_user where username=#{username} and password=#{password}")
    public User userByUsernameAndPassword(@Param("username") String username,@Param("password") String password);

    // 注册
    @Insert("INSERT INTO tb_user VALUES(null,#{username},#{password},#{repassword})")
    public Integer userEnroll(@Param("username") String username,@Param("password") String password,@Param("repassword") String repassword);

}
