package com.ynnz.jdbc.test;

import com.ynnz.mybatis.mapper.EmployeeMapper;
import com.ynnz.mybatis.pojo.Employee;
import com.ynnz.mybatis.utils.SqlSessionUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class EmployeeTest {
    //查询所有
    @Test
    public void testQueryList(){
        //获取SQLSession对象
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        List<Employee> list = mapper.querList();
        list.forEach(System.out::println);
        sqlSession.commit();
        sqlSession.close();
    }

    // 根据ID进行查询
    @Test
    public void testQueryById(){
        //获取SQLSession对象
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = mapper.queryById(1);
        System.out.println(employee);
    }

    //新增语句
    @Test
    public void testInsert(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee(null,"陆良-天够","男","小陆良"," 陆良 --扛把子" );
        int count = mapper.insert(employee);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();

    }

    //删除语句
    @Test
    public void testDeleteById(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        int count = mapper.deleteById(6);
        System.out.println("一共删除:"+count+"条数据");
        sqlSession.commit();
        sqlSession.close();


    }
    
    //修改语句
    @Test
    public void testUpdate(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee(1236,"大理洱海海主","男","大理-渣男","大理-苍山-洱海-扛把子");
        int count = mapper.update(employee);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();
    }

    //修改语句 2.0
    @Test
    public void testUpdateTo(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        Employee employee = new Employee(4,"洱海海主",null,null,null);
        int count = mapper.updateTo(employee);
        System.out.println(count);
        sqlSession.commit();
        sqlSession.close();

    }

    //修改语句3.0
    @Test
    public void testUpdateSin3(){
        SqlSession sqlSession = SqlSessionUtil.openSqlSession();
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        //根据id进行查询

        Employee em = mapper.queryById(5);
        em.setName("老赖 -- 文权");
        int update = mapper.update(em);
        System.out.println(update);
        sqlSession.commit();
        sqlSession.close();


    }


}
