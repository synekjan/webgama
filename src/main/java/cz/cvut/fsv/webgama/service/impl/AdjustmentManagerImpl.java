package cz.cvut.fsv.webgama.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;

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

public class AdjustmentManagerImpl implements AdjustmentManager {

	private static final Logger logger = LoggerFactory
			.getLogger(AdjustmentManagerImpl.class);

	private InputDao inputDao;
	private InputParser inputParser;
	private UserDao userDao;

	public void setInputDao(InputDao inputDao) {
		this.inputDao = inputDao;
	}

	public void setInputParser(InputParser inputParser) {
		this.inputParser = inputParser;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@Transactional
	public void adjustFromFile(MultipartFile file, String username) {

		try {
			Input input = inputParser.parseInput(file.getInputStream());
			input.setUser(userDao.findUserByUsername(username));
			//Google Guava InputStream to String
			String stringFromStream = CharStreams.toString(new InputStreamReader(file.getInputStream(),"UTF-8"));
			input.setFileContent(stringFromStream);
			input.setFilename(file.getOriginalFilename());
			input.setAlgorithm("svd");
			input.setAngUnits(400);
			input.setLatitude(0.0);

			inputDao.insert(input);

		} catch (IOException e) {
			logger.error("Error during converting MultipartFile to InputStream");
			e.printStackTrace();
		}
	}
}