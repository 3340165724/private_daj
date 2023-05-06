package com.example.dao;

import com.example.pojo.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * - @Autowired 声明注入时使用 IUserDao 接口类型
 *
 * */
public interface IUserDao extends IGeneralDao<User>{
    public List<User> queryUser();

    public User queryByUsername(String username);

    default User mapResult(final ResultSet rs) throws SQLException {

        User o = new User();
        o.setId(rs.getInt("id"));
        o.setUsername(rs.getString("username"));
        o.setPassword(rs.getString("password"));

        return o;
    }
}
