package com.example;

import com.example.dao.IUserDao;
import com.example.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@SpringBootTest
class SpringBootJdbcTemplateApplicationTests {
	@Autowired
	protected IUserDao userDao;

	@Test
	void queryUser() {
		List<User> list =  userDao.queryUser();
		System.out.println(list);
		System.out.println(list.stream().map(o -> o.getUsername().toString()).toString());

		for(int i = 0 ; i < list.size(); i++){
			if("root".equals(list.get(i).getUsername())){
				System.out.println(list.get(i).getUsername());
			}
		}
	}


	@Test
	void queryByUsername() {
		User user_ = userDao.queryByUsername("root");
		System.out.println(user_);
		if( "root".equals(user_.getUsername())){
			if( "123456".equals(user_.getPassword())){
//				session.setAttribute("username",username);
				System.out.println("55555555555555555");

			}
		}else {
			System.out.println("7777777777777777");
		}
	}

}
