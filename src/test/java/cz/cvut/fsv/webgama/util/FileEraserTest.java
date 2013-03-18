package cz.cvut.fsv.webgama.util;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class FileEraserTest {

	private FileEraser fileEraser;
	
	@Before
	public void setUp() throws Exception {
		
		fileEraser = new FileEraser();
		fileEraser.eraseTemporaryFiles();
		
		for (int i = 0; i < 10; i++) {
			
			File file = new File("/tmp/" + Generator.generateInputFilename("user"));
			file.createNewFile();
			
		}
	}

	@Test
	public void testEraseTemporaryFiles() {
		int i = fileEraser.eraseTemporaryFiles();
		assertEquals(10, i);
	}

}
