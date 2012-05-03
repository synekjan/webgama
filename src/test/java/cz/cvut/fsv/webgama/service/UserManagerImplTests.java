package cz.cvut.fsv.webgama.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.cvut.fsv.webgama.domain.User;

public class UserManagerImplTests {

	private static UserManager userManager;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"test-context.xml");

		userManager = (UserManager) context.getBean("userManager");

	}

	@Test
	public void testUsers() {
		
		List<User> list = userManager.getUsersByUsername("admin");
		
		if (list.isEmpty())
			System.out.println("list is empty!");
		
		for (User user : list) {
			System.out.println(user);
		}
		
	}
	
	
	@Test
	public void testAdminRights() {
		
		Boolean b = userManager.hasUserAdminRights("user");
		
		System.out.println(b);
		
	}
	
	

}
