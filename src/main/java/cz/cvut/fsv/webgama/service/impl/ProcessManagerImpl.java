package cz.cvut.fsv.webgama.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;
import com.google.common.io.Files;

import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.service.ProcessManager;
import cz.cvut.fsv.webgama.util.Generator;

public class ProcessManagerImpl implements ProcessManager {

	private String gamaFilePath;

	private static final Logger logger = LoggerFactory
			.getLogger(ProcessManagerImpl.class);

	@Override
	public ProcessOutput runExternalGama(String feed, String username) {

		// builds command sequence
		List<String> commands = new ArrayList<String>(30);
		// adds executable Gama program
		commands.add(gamaFilePath);

		// creates temporary filename
		String filename = Generator.generateFilename(username);
		String filePath = "/tmp/" + filename;

		// creates temporary file
		try {
			Files.append(feed, new File(filePath), Charset.forName("UTF-8"));
		} catch (IOException e) {
			logger.error("error during creating temporary input file");
		}
		// adds input xml file path to command sequence
		commands.add(filePath);

		// adds optional runtime arguments
		/*
		 * if (input.getAlgorithm() != null) { commands.add("--algorithm");
		 * commands.add(input.getAlgorithm()); }
		 * 
		 * if (input.getAngUnits() != null) { commands.add("--angles");
		 * commands.add(input.getAngUnits().toString()); }
		 * 
		 * if (input.getLatitude() != null) { commands.add("--latitude");
		 * commands.add(input.getLatitude().toString()); }
		 * 
		 * if (input.getEllipsoid() != null) { commands.add("--ellipsoid");
		 * commands.add(input.getEllipsoid()); }
		 */

		ProcessOutput processOutput = new ProcessOutput();

		// Create process
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File("/tmp"));

		try {
			Process p = pb.start();
			String result = CharStreams.toString(new InputStreamReader(p
					.getInputStream(), "UTF-8"));
			String errorString = CharStreams.toString(new InputStreamReader(p
					.getErrorStream(), "UTF-8"));
			int exitValue = p.waitFor();

			processOutput.setExitValue(exitValue);
			processOutput.setResult(result);
			processOutput.setErrorMessage(errorString);

		} catch (IOException | InterruptedException e) {
			logger.error("error during starting process");
		}

		return processOutput;
	}

	public void setGamaFilePath(String gamaFilePath) {
		this.gamaFilePath = gamaFilePath;
	}

}
