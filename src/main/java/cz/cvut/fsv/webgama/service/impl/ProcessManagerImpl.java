package cz.cvut.fsv.webgama.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Files;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.service.ProcessManager;
import cz.cvut.fsv.webgama.util.Generator;

public class ProcessManagerImpl implements ProcessManager {

	private String gamaFilePath;

	private static final Logger logger = LoggerFactory.getLogger(ProcessManagerImpl.class);

	@Override
	public ProcessOutput runExternalGama(Calculation calculation, String username) {

		// builds command sequence
		List<String> commands = new ArrayList<String>(20);
		// adds executable Gama program
		commands.add(gamaFilePath);

		// creates temporary filename for input file
		String filename = Generator.generateInputFilename(username);
		String filePath = "/tmp/" + filename;

		// creates temporary files (input, output(txt,html,svg)
		try {
			Files.append(calculation.getInput().getXmlContent(), new File(filePath), Charset.forName("UTF-8"));
		} catch (IOException e) {
			logger.error("error during creating temporary files");
		}
		// adds input xml file path to command sequence
		commands.add(filePath);

		// send xml output to std::out
		commands.add("--xml");
		commands.add("-");
		// adds output in text format
		commands.add("--text");
		String textOutputFilePath = "/tmp/" + Generator.generateTextOutputFilename(username);
		commands.add(textOutputFilePath);
		// adds output in text format
		commands.add("--html");
		String htmlOutputFilePath = "/tmp/" + Generator.generateHtmlOutputFilename(username);
		commands.add(htmlOutputFilePath);
		// adds output in text format
		commands.add("--svg");
		String svgOutputFilePath = "/tmp/" + Generator.generateSvgOutputFilename(username);
		commands.add(svgOutputFilePath);

		// adds optional runtime arguments
		if (calculation.getLanguage() != null) {
			commands.add("--language");
			commands.add(calculation.getLanguage());
		}

		if (calculation.getAlgorithm() != null) {
			commands.add("--algorithm");
			commands.add(calculation.getAlgorithm());
		}

		if (calculation.getAngUnits() != null) {
			commands.add("--angles");
			commands.add(calculation.getAngUnits().toString());
		}

		if (calculation.getLatitude() != null) {
			commands.add("--latitude");
			commands.add(calculation.getLatitude().toString());
		}

		if (calculation.getEllipsoid() != null) {
			commands.add("--ellipsoid");
			commands.add(calculation.getEllipsoid());
		}

		ProcessOutput processOutput = new ProcessOutput();

		// Create process
		ProcessBuilder pb = new ProcessBuilder(commands);
		pb.directory(new File("/tmp"));

		InputStreamReader inputStream = null;
		InputStreamReader errorStream = null;

		try {
			Process process = pb.start();
			inputStream = new InputStreamReader(process.getInputStream(), "UTF-8");
			errorStream = new InputStreamReader(process.getErrorStream(), "UTF-8");

			String result = CharStreams.toString(inputStream);
			String errorString = CharStreams.toString(errorStream);
			int exitValue = process.waitFor();

			processOutput.setExitValue(exitValue);
			processOutput.setXmlResult(result);
			processOutput.setErrorMessage(errorString);

		} catch (IOException | InterruptedException e) {
			logger.error("error during starting process");
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (errorStream != null) {
				try {
					errorStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (processOutput.getExitValue() != 0)
			return processOutput;

		try {
			processOutput.setTextResult(Files.toString(new File(textOutputFilePath), Charsets.UTF_8));
			processOutput.setHtmlResult(Files.toString(new File(htmlOutputFilePath), Charsets.UTF_8));
			processOutput.setSvgResult(Files.toString(new File(svgOutputFilePath), Charsets.UTF_8));
		} catch (IOException e) {
			logger.error("error during reading result files");
			e.printStackTrace();
		}

		return processOutput;
	}

	public void setGamaFilePath(String gamaFilePath) {
		this.gamaFilePath = gamaFilePath;
	}

}
