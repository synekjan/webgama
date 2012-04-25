package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.StandardPasswordEncoder;

import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserForm;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.service.UserManager;

public class UserManagerImpl implements UserManager {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void insertUser(User user) {

		userDao.insert(user);
	}

	@Override
	public User getUser(int id) {

		User user = userDao.findUserById(id);

		return user;
	}

	@Override
	public User getUser(String username) {

		return userDao.findUserByUsername(username);
	}

	@Override
	public List<User> getUserList() {

		return userDao.getUserList();
	}

	@Override
	public void updateUser(UserForm userForm) {
		
		User user = userDao.findUserByUsername(userForm.getUsername());
		
		user.setFirstName(userForm.getFirstName());
		user.setLastName(userForm.getLastName());
		user.setEmail(userForm.getEmail());
		user.setTelephone(userForm.getTelephone());
		user.setStreet(userForm.getStreet());
		user.setNumber(userForm.getNumber());
		user.setCity(userForm.getCity());
		user.setZipCode(userForm.getZipCode());
		user.setState(userForm.getState());
				
		userDao.update(user);
		
	}

	@Override
	public void registerUser(UserRegistrationForm userForm) {
		
		User user = new User();
		
		user.setUsername(userForm.getUsername());
		//encodes password with salted-hash
		user.setPassword(new StandardPasswordEncoder().encode(userForm.getPassword()));
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

	}

}
