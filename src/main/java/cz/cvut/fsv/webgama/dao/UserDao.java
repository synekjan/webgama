package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Confirmation;
import cz.cvut.fsv.webgama.domain.User;

public interface UserDao {

	public void insert(User user);

	public void delete(User user);

	public void update(User user);

	public void updatePassword(User user);

	public void updateEnabled(User user);

	public List<User> getUserList();
	
	public Long getUserCount();

	public List<User> findUsersByUsername(String username);

	public List<User> findUsersByEmail(String email);

	public User findUserById(long id);

	public User findUserByUsername(String username);

	public void insertConfirmationID(String uuid, Long user_id);

	public void deleteConfirmationID(String uuid);

	public List<Confirmation> findConfirmationsByUUID(String uuid);

	public void clearConfirmations();

}
