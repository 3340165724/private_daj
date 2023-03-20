package com.dsj.application.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dsj.application.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
