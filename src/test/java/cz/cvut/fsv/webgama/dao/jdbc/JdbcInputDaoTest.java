package cz.cvut.fsv.webgama.dao.jdbc;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class JdbcInputDaoTest {
	
	//private EmbeddedDatabase db;

	@Before
	public void setUp() throws Exception {
		
		//db = new EmbeddedDatabaseBuilder().addDefaultScripts().build();
		
	}

	@Test
	public void testInsert() {
	
		//JdbcTemplate jdbcTemplate = new JdbcTemplate(db);
	}

	@Test
	@Ignore
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testGetInputList() {
		fail("Not yet implemented");
	}

	@Test
	@Ignore
	public void testFindInputsByUser() {
		fail("Not yet implemented");
	}

}
