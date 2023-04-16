package com.dsj.application.mapper;

import com.dsj.application.model.UserInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserInfoMapper_axios {
    @Select("select id, announce, `like`, comments, uid, feedback from tb_userinfo")
    public List<UserInfo> queryList();
}
