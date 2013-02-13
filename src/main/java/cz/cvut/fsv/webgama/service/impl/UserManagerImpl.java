package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fsv.webgama.dao.AuthorityDao;
import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.Authority;
import cz.cvut.fsv.webgama.domain.Confirmation;
import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserForm;
import cz.cvut.fsv.webgama.form.UserPasswordChangeForm;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.service.MailManager;
import cz.cvut.fsv.webgama.service.UserManager;
import cz.cvut.fsv.webgama.util.Generator;

public class UserManagerImpl implements UserManager {

	private UserDao userDao;

	private AuthorityDao authorityDao;

	private MailManager mailManager;

	@Override
	public void insertUser(User user) {

		userDao.insert(user);
	}

	@Override
	public void deleteUser(User user) {

		userDao.delete(user);
	}

	@Override
	public User getUser(int id) {

		User user = userDao.findUserById(id);

		return user;
	}

	@Override
	public User getUser(String username) {

		User user = userDao.findUserByUsername(username);

		return user;
	}

	@Override
	public List<User> getUserList() {

		return userDao.getUserList();
	}

	@Override
	public List<User> getUsersByUsername(String username) {

		List<User> list = userDao.findUsersByUsername(username);

		return list;
	}

	@Override
	public List<User> getUsersByEmail(String email) {

		List<User> list = userDao.findUsersByEmail(email);

		return list;
	}

	@Override
	@Transactional
	public void updateUser(UserForm userForm) {

		User user = userDao.findUserByUsername(userForm.getUsername());

		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setTelephone(userForm.getTelephone());
		user.setStreet(userForm.getStreet());
		user.setNumber(userForm.getNumber());
		user.setCity(userForm.getCity());
		user.setZipCode(userForm.getZipCode());
		user.setState(userForm.getState());
		user.setModified(new DateTime());

		userDao.update(user);

	}

	@Override
	@Transactional
	public void registerUser(UserRegistrationForm userForm,
			HttpServletRequest request) {

		User user = new User();

		user.setUsername(userForm.getUsername());
		// encodes password with salted-hash
		user.setPassword(new StandardPasswordEncoder().encode(userForm
				.getPassword()));
		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setEmail(userForm.getEmail());
		user.setTelephone(userForm.getTelephone());
		user.setStreet(userForm.getStreet());
		user.setNumber(userForm.getNumber());
		user.setCity(userForm.getCity());
		user.setZipCode(userForm.getZipCode());
		user.setState(userForm.getState());

		userDao.insert(user);

		String uuid = Generator.generateConfirmationID();
		String URL = request.getRequestURL().toString()
				.replace("/register", "");

		Long user_id = userDao.findUserByUsername(userForm.getUsername())
				.getId();
		userDao.insertConfirmationID(uuid, user_id);

		mailManager.sendConfirmationEmail(userForm, uuid, URL);

	}

	@Override
	@Transactional
	public Boolean hasUserAdminRights(String username) {

		User user = userDao.findUserByUsername(username);

		List<Authority> list = authorityDao.getUserAuthorities(user);

		for (Authority authority : list) {

			if ("ROLE_ADMIN".equals(authority.getRole().getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public void changeUserPassword(UserPasswordChangeForm userForm) {

		User user = userDao.findUserByUsername(userForm.getUsername());

		StandardPasswordEncoder encoder = new StandardPasswordEncoder();

		user.setPassword(encoder.encode(userForm.getNewPassword()));
		user.setModified(new DateTime());

		userDao.updatePassword(user);

	}

	@Override
	@Transactional
	public boolean isConfirmationIDinDB(String uuid) {

		List<Confirmation> list = userDao.findConfirmationsByUUID(uuid);

		if (list.isEmpty())
			return false;

		return true;
	}

	@Override
	@Transactional
	public void confirmEmailAddress(String uuid) {

		List<Confirmation> list = userDao.findConfirmationsByUUID(uuid);
		Confirmation conf = list.get(0);
		User user = conf.getUser();
		user.setEnabled(true);

		userDao.deleteConfirmationID(uuid);
		userDao.updateEnabled(user);
	}
	
	@Override
	@Transactional
	public int getUserCount() {

		return userDao.getUserCount() + 126;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAuthorityDao(AuthorityDao authorityDao) {
		this.authorityDao = authorityDao;
	}

	public void setMailManager(MailManager mailManager) {
		this.mailManager = mailManager;
	}


}
