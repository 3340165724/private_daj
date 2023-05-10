package com.example.dao;

import com.example.pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * 面向接口定义 DAO 的 abstract 方法
 * 由 Impl 类实现这些方法是项目开发规范
 *
 * - @Autowired 声明注入时使用 IUserDao 接口类型
 *
 * */
public interface IUserDao extends IGeneralDao<User>{
    public List<User> queryUser();

    public User queryByUsername(String username);


    /**
     *
     * - 处理结果集
     *
     *  - 没有处理结果集， userDao.queryByUsername("root").getUsername()得不到值，会出现java.lang.NullPointerException
     *
     * */
    default User mapResult(final ResultSet rs) throws SQLException {
        User o = new User();
        o.setId(rs.getInt("id"));
        o.setUsername(rs.getString("username"));
        o.setPassword(rs.getString("password"));
        return o;
    }
}
