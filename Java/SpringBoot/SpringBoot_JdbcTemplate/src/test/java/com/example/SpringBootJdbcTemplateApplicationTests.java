package com.example;

import com.example.dao.IStudentDao;
import com.example.dao.IUserDao;
import com.example.pojo.Student;
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
	@Autowired
	protected IStudentDao studentDao;

	@Test
	void queryUser() {
		List<User> list =  userDao.queryUser();
		System.out.println(list);

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




	// TODO
	@Test
	public void  queryStudentAll(){
		List<Student> queryStudentAll = studentDao.queryStudentAll();
		queryStudentAll.forEach(System.out::println);
	}




}
