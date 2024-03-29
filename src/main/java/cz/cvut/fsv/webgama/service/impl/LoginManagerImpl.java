package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fsv.webgama.dao.LoginDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Login;
import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.LoginManager;

public class LoginManagerImpl implements LoginManager {

	private UserDao userDao;

	private LoginDao loginDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}

	@Override
	@Transactional
	public void login(String username, String ip, Boolean success) {

		User user = userDao.findUserByUsername(username);

		Login login = new Login();
		login.setUser(user);
		login.setIp(ip);
		login.setSuccess(success);
		loginDao.insert(login);

	}

	@Override
	@Transactional
	public List<Login> getLoginList(String username) {

		return loginDao.getLoginList(userDao.findUserByUsername(username));
	}

	@Override
	@Transactional
	public DateTime getLastLogin(String username) {

		Login login = loginDao.getLastLogin(userDao
				.findUserByUsername(username));

		return login.getTime();

	}

}
