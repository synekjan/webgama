package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.User;

public interface UserDao {

	public void insert(User user);
	
	public void delete(User user);
	
	public void update(User user);
	
	public List<User> getUserList();
	
	public User findUserById(int id);
	
	public User findUserByUsername(String username);
	
	public void dropLastUser();
	
	

}
