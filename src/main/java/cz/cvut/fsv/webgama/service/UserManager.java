package cz.cvut.fsv.webgama.service;

import java.util.List;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserForm;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;

public interface UserManager {
	
	public void insertUser(User user);
	
	public User getUser(int id);
	
	public User getUser(String username);
	
	public List<User> getUserList();
	
	public void updateUser(UserForm userForm);
	
	public void registerUser(UserRegistrationForm user);

}
