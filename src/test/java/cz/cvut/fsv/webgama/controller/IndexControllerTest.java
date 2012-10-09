package cz.cvut.fsv.webgama.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
public class IndexControllerTest {

	@Test
	public void test() {

		IndexController controller = new IndexController();
		assertNotNull(controller);

	}

}
