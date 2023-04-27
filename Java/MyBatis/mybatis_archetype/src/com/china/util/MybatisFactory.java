package com.china.util;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisFactory {

	public static SqlSession getSqlSession() {

		try (InputStream is = Resources.getResourceAsStream("mybatis-config.xml")) {

			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			return sqlSessionFactory.openSession(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}