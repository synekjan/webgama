package cz.cvut.fsv.webgama.dao;

import java.util.List;

import cz.cvut.fsv.webgama.domain.Login;
import cz.cvut.fsv.webgama.domain.User;

public interface LoginDao {

	public void insert(Login login);

	public List<Login> getLoginList(User user);

	public Login getLastLogin(User user);

}
