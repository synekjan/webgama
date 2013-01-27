package cz.cvut.fsv.webgama.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.CharStreams;

import cz.cvut.fsv.webgama.dao.InputDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.parser.InputParser;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.ProcessManager;

public class AdjustmentManagerImpl implements AdjustmentManager {

	private static final Logger logger = LoggerFactory
			.getLogger(AdjustmentManagerImpl.class);

	private InputDao inputDao;
	private InputParser inputParser;
	private UserDao userDao;
	private ProcessManager processManager;

	@Override
	@Transactional
	public String adjustFromFile(MultipartFile file, String username) {
		Input input = null;
		try {
			input = inputParser.parseInput(file.getInputStream());
			input.setUser(userDao.findUserByUsername(username));
			// Google Guava InputStream to String
			String stringFromStream = CharStreams
					.toString(new InputStreamReader(file.getInputStream(),
							"UTF-8"));
			input.setName("Import-" + file.getOriginalFilename());
			input.setFileContent(stringFromStream);
			input.setFilename(file.getOriginalFilename());
			input.setAlgorithm("svd");
			input.setAngUnits(400);
			input.setLatitude(0.0);

			inputDao.insert(input);

		} catch (IOException e) {
			logger.error("Error during converting MultipartFile to InputStream");
		}

		return processManager.runExternalGama(input, username);
	}

	@Transactional
	@Override
	public List<Input> getInputsbyUsername(String username) {

		return inputDao.findInputsByUser(userDao.findUserByUsername(username));
	}

	public void setInputDao(InputDao inputDao) {
		this.inputDao = inputDao;
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

	@Transactional
	@Override
	public int getInputCountbyUsername(String username) {

		return inputDao.getInputCountByUser(userDao
				.findUserByUsername(username));
	}

}
