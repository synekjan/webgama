package cz.cvut.fsv.webgama.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Locale;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.CharStreams;

import cz.cvut.fsv.webgama.dao.CalculationDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Output;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;
import cz.cvut.fsv.webgama.parser.InputParser;
import cz.cvut.fsv.webgama.service.ActivityManager;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.ProcessManager;
import cz.cvut.fsv.webgama.util.Generator;

public class AdjustmentManagerImpl implements AdjustmentManager,Serializable {

	private static final long serialVersionUID = 3079679944045465378L;

	private static final Logger logger = LoggerFactory.getLogger(AdjustmentManagerImpl.class);

	private CalculationDao calculationDao;

	private InputParser inputParser;

	private UserDao userDao;

	private ProcessManager processManager;

	private ActivityManager activityManager;

	public void setCalculationDao(CalculationDao calculationDao) {
		this.calculationDao = calculationDao;
	}

	public void setInputParser(InputParser inputParser) {
		this.inputParser = inputParser;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setProcessManager(ProcessManager processManager) {
		this.processManager = processManager;
	}

	public void setActivityManager(ActivityManager activityManager) {
		this.activityManager = activityManager;
	}

	@Override
	@Transactional
	public ProcessOutput adjustFromFile(MultipartFile file, String username, Locale locale) {
		Input input = new Input();
		ProcessOutput processOutput = null;

		try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), "UTF-8")) {

			// Google Guava InputStream to String
			String stringFromStream = CharStreams.toString(inputStreamReader);

			Calculation calculation = new Calculation();
			calculation.setUser(userDao.findUserByUsername(username));
			calculation.setName("Import-" + file.getOriginalFilename());
			calculation.setAlgorithm("svd");
			calculation.setAngUnits(400);
			calculation.setLatitude(50.0);

			// calculate directly from file content
			input.setXmlContent(stringFromStream);
			calculation.setInput(input);

			// choose default language
			if (locale.getLanguage().equals(new Locale("cs").getLanguage())) {
				calculation.setLanguage("cz");
			} else {
				calculation.setLanguage("en");
			}

			// run GNU Gama calculation
			processOutput = processManager.runExternalGama(calculation, username);

			if (processOutput.getExitValue() != 0)
				return processOutput;

			input = inputParser.parseInput(file.getInputStream());
			input.setXmlContent(stringFromStream);
			calculation.setInput(input);

			Output output = new Output();
			output.setXmlContent(processOutput.getXmlResult());
			output.setHtmlContent(processOutput.getHtmlResult());
			output.setSvgContent(processOutput.getSvgResult());
			output.setTextContent(processOutput.getTextResult());
			output.setRunningTime(processOutput.getRunningTime());
			calculation.setOutput(output);
			calculation.setProgress("calculated");

			calculationDao.insert(calculation);

		} catch (IOException e) {
			logger.error("Error during converting MultipartFile to InputStream");
		}

		return processOutput;
	}

	@Transactional
	@Override
	public void insertNewCalculation(AdjustmentPageForm adjustmentForm, String username, Locale locale) {

		Calculation calculation = new Calculation();
		Input input = new Input();
		Network network = new Network();

		network.setAxesXY(adjustmentForm.getAxesXY());
		network.setAngles(adjustmentForm.getAngles());
		network.setEpoch(adjustmentForm.getEpoch());
		network.setDescription(adjustmentForm.getDescription());
		network.setSigmaApr(adjustmentForm.getSigmaApr());
		network.setConfPr(adjustmentForm.getConfPr());
		network.setTolAbs(adjustmentForm.getTolAbs());
		network.setSigmaAct(adjustmentForm.getSigmaAct());
		network.setUpdateCC(adjustmentForm.getUpdateCC());
		network.setDirectionStdev(adjustmentForm.getDirectionStdev());
		network.setDistanceStdev(adjustmentForm.getDistanceStdev());
		network.setAngleStdev(adjustmentForm.getAngleStdev());
		network.setZenithAngleStdev(adjustmentForm.getZenithAngleStdev());
		network.setPoints(adjustmentForm.getPoints());
		network.setClusters(adjustmentForm.getClusters());
		input.setNetwork(network);
		input.setVersion("2.0");

		// convert OutputStream to String and update xml_content in inputs table
		OutputStream baos = new ByteArrayOutputStream(10000);
		inputParser.composeInput(baos, input);
		input.setXmlContent(baos.toString());

		calculation.setInput(input);

		// choose default language
		if (locale.getLanguage().equals(new Locale("cs").getLanguage())) {
			calculation.setLanguage("cz");
		} else {
			calculation.setLanguage("en");
		}

		// delete output -- need to be recalculated again
		calculation.setOutput(null);
		calculation.setProgress("not-calculated");
		calculation.setUser(userDao.findUserByUsername(username));
		calculation.setName(Generator.generateCalculationName(locale));

		calculationDao.insert(calculation);
		activityManager.recordActivity(username, "activity.calculation.created");
	}

	@Transactional
	@Override
	public void updateInputInCalculation(AdjustmentPageForm adjustmentForm, Calculation calculation) {

		Input input = calculation.getInput();
		input.setTime(new DateTime());
		Network network = input.getNetwork();
		network.setAxesXY(adjustmentForm.getAxesXY());
		network.setAngles(adjustmentForm.getAngles());
		network.setEpoch(adjustmentForm.getEpoch());
		network.setDescription(adjustmentForm.getDescription());
		network.setSigmaApr(adjustmentForm.getSigmaApr());
		network.setConfPr(adjustmentForm.getConfPr());
		network.setTolAbs(adjustmentForm.getTolAbs());
		network.setSigmaAct(adjustmentForm.getSigmaAct());
		network.setUpdateCC(adjustmentForm.getUpdateCC());
		network.setDirectionStdev(adjustmentForm.getDirectionStdev());
		network.setDistanceStdev(adjustmentForm.getDistanceStdev());
		network.setAngleStdev(adjustmentForm.getAngleStdev());
		network.setZenithAngleStdev(adjustmentForm.getZenithAngleStdev());
		network.setPoints(adjustmentForm.getPoints());
		network.setClusters(adjustmentForm.getClusters());
		input.setNetwork(network);

		// convert OutputStream to String and update xml_content in inputs table
		OutputStream baos = new ByteArrayOutputStream(10000);
		inputParser.composeInput(baos, input);
		input.setXmlContent(baos.toString());
		calculation.setInput(input);

		// delete output -- need to be recalculated again
		calculation.setOutput(null);
		calculation.setProgress("not-calculated");

		calculation.setTime(new DateTime());
		calculationDao.update(calculation);
	}

	@Transactional
	@Override
	public void handleWizardForm(Input input, String username, Locale locale) {

		Calculation calculation = null;
		if (input.getId() == null) {
			calculation = new Calculation();
			calculation.setUser(userDao.findUserByUsername(username));
		} else {
			calculation = calculationDao.findCalculationByInputId(input.getId());
		}
		input.setVersion("2.0");

		// convert OutputStream to String and update xml_content in inputs table
		OutputStream baos = new ByteArrayOutputStream(10000);
		inputParser.composeInput(baos, input);
		input.setXmlContent(baos.toString());

		calculation.setInput(input);

		// choose default language
		if (locale.getLanguage().equals(new Locale("cs").getLanguage())) {
			calculation.setLanguage("cz");
		} else {
			calculation.setLanguage("en");
		}

		// delete output -- need to be recalculated again
		calculation.setOutput(null);
		calculation.setProgress("not-calculated");
		calculation.setTime(new DateTime());

		if (input.getId() == null) {
			calculation.setName(Generator.generateCalculationName(locale));
			calculationDao.insert(calculation);
			activityManager.recordActivity(username, "activity.calculation.created");
		} else {
			calculationDao.update(calculation);
		}
	}
}
