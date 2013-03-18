package cz.cvut.fsv.webgama.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileEraser {

	private static final Logger logger = LoggerFactory.getLogger(FileEraser.class);

	public int eraseTemporaryFiles() {

		int fileCount = 0;

		File directory = new File("/tmp");

		for (File file : directory.listFiles()) {
			if (file.getName().startsWith("webgama")
					&& (file.getName().endsWith(".wxml") || file.getName().endsWith(".whtml")
							|| file.getName().endsWith(".wsvg") || file.getName().endsWith(".wtxt"))) {
				file.delete();
				fileCount++;
			}
		}

		logger.info(fileCount + " temporary files have been erased from /tmp directory");
		return fileCount;
	}

}
