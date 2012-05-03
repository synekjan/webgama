package cz.cvut.fsv.webgama.dao.jdbc;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.cvut.fsv.webgama.dao.AuthorityDao;
import cz.cvut.fsv.webgama.domain.Authority;
import cz.cvut.fsv.webgama.domain.User;

public class JdbcAuthorityDaoTests {

	private static AuthorityDao authorityDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("test-context.xml");
		
		authorityDao = (AuthorityDao) context.getBean("authorityDao");
		
	}

	@Test
	public void test() {
		
		User user = new User();
		user.setUsername("admin");
		user.setId(100);
		
		List<Authority> list = authorityDao.getUserAuthorities(user);
		
		for (Authority authority : list) {
			System.out.println(authority.getRole().getName());
			
		}
		
	}

}
