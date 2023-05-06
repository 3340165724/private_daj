package com.ynnz.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;

/**
 *  Mybatis:工具类
 * @author liKaihusing
 * @version 1.0
 * @since 1.0
 */
public class SqlSessionUtil {
    //构造方法私有化
    private  SqlSessionUtil(){};
    
    // 全局 SqlSessionFactory
    private static SqlSessionFactory sqlSessionFactory;
    
    static{
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //ThreaderLocal
    private static ThreadLocal<SqlSession> local = new ThreadLocal<>();
    
    //通过ThreaderLocaer 构造出SqllSession对象
    public static SqlSession openSqlSession(){
        SqlSession sqlSession = local.get();
        //判断是否是第一次创建对象
        if (sqlSession == null) {
             sqlSession = sqlSessionFactory.openSession();

             //把sqlSession 添加到本地线程
            local.set(sqlSession);
        }
        return  sqlSession;
    }

    // 解除线程绑定
    public void close(SqlSession sqlSession){
        if (sqlSession != null) {
            sqlSession.close();
            //解绑
            local.remove();
        }
    }
    
}
