package cz.cvut.fsv.webgama.dao.jdbc;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cz.cvut.fsv.webgama.dao.RoleDao;
import cz.cvut.fsv.webgama.domain.Role;

public class JdbcRoleDaoTests {

	private static RoleDao roleDao;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"test-context.xml");

		roleDao = (RoleDao) context.getBean("roleDao");

	}

	@Test
	public void test() {

		for (int i = 1; i < 5; i++) {
			Role role = roleDao.findRoleById(i);

			System.out.println(role.getName());

		}
		
		
	}
}
