package cz.cvut.fsv.webgama.dao.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcUserDaoTests {

	private User user;

	private UserDao userDao;
	
	private ApplicationContext context;

	@Before
	public void setUp() {

		user = new User();
		user.setId(32);
		user.setUsername("ferda007");
		user.setPassword("fuckoff");
		user.setFirstName("Mallory");
		user.setLastName("Evans");
		user.setEmail("foo@bar.com");
		user.setTelephone("777111222");
		user.setStreet("Kennedyho 45");
		user.setCity("Prague");
		user.setZipCode("51805");
		user.setState("Czech Republic");
		
		context = new ClassPathXmlApplicationContext("test-context.xml");
		
		userDao = (UserDao) context.getBean("userDao");

	}

	@Test
	public void testInsert() {

		userDao.insert(user);

		assertEquals(new Integer(32), user.getId());
	}
	
	@Test
	public void testUserList() {
		
		List<User> list = userDao.getUserList();
		
		for (User user : list) {
			System.out.println(user);
		}
		
	}
	
	@Ignore("need some data in table")
	@Test
	public void testDropUserById() {
		
		userDao.dropLastUser();
		
		
	}

}
