package cz.cvut.fsv.webgama.dao.jdbc;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class JdbcInputDaoTest {
	
	//private EmbeddedDatabase db;

	@Before
	public void setUp() throws Exception {
		
		//db = new EmbeddedDatabaseBuilder().addDefaultScripts().build(); //TODO HSQL db
		
	}

	@Test
	public void testInsert() {
	
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(db); //TODO Spring JDBC doc uplne dole
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInputList() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindInputsByUser() {
		fail("Not yet implemented");
	}

}
