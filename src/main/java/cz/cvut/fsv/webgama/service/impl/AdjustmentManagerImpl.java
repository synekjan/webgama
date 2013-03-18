package cz.cvut.fsv.webgama.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.CharStreams;

import cz.cvut.fsv.webgama.dao.CalculationDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Output;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.parser.InputParser;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.ProcessManager;

public class AdjustmentManagerImpl implements AdjustmentManager {

	private static final Logger logger = LoggerFactory.getLogger(AdjustmentManagerImpl.class);

	private CalculationDao calculationDao;

	private InputParser inputParser;

	private UserDao userDao;

	private ProcessManager processManager;

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

	@Override
	@Transactional
	public String adjustFromFile(MultipartFile file, String username) {
		Input input = null;
		ProcessOutput processOutput = null;

		try (InputStreamReader inputStreamReader = new InputStreamReader(file.getInputStream(), "UTF-8")) {

			// Google Guava InputStream to String
			String stringFromStream = CharStreams.toString(inputStreamReader);

			input = inputParser.parseInput(file.getInputStream());
			input.setXmlContent(stringFromStream);

			Calculation calculation = new Calculation();
			calculation.setUser(userDao.findUserByUsername(username));
			calculation.setName("Import-" + file.getOriginalFilename());
			calculation.setProgress("not-calculated");
			calculation.setLanguage("en");
			calculation.setAlgorithm("svd");
			calculation.setAngUnits(400);
			calculation.setLatitude(0.0);
			calculation.setInput(input);
			
			//run GNU Gama calculation 
			processOutput = processManager.runExternalGama(calculation, username);

			if (processOutput.getExitValue() != 0)
				return processOutput.getErrorMessage();
			
			Output output = new Output();
			output.setXmlContent(processOutput.getXmlResult());
			output.setHtmlContent(processOutput.getHtmlResult());
			output.setSvgContent(processOutput.getSvgResult());
			output.setTextContent(processOutput.getTextResult());
			calculation.setOutput(output);

			calculationDao.insert(calculation);

		} catch (IOException e) {
			logger.error("Error during converting MultipartFile to InputStream");
		}

		return processOutput.getXmlResult();
	}

	@Transactional
	@Override
	public List<Calculation> getCalculationsbyUsername(String username) {

		return calculationDao.findCalculationsByUser(userDao.findUserByUsername(username));
	}

	@Transactional
	@Override
	public long getCalculationCountbyUsername(String username) {

		return calculationDao.getCalculationCountByUser(userDao.findUserByUsername(username));
	}

	@Override
	public Calculation getCalculationById(long id) {

		return calculationDao.findCalculationById(id);
	}

	@Override
	public boolean isCalculationIdInDB(Long id) {

		return calculationDao.isCalculationIdInDB(id);
	}

}
