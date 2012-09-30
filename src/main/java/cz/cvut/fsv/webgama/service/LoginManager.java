package cz.cvut.fsv.webgama.service;

import java.util.List;

import org.joda.time.DateTime;

import cz.cvut.fsv.webgama.domain.Login;

public interface LoginManager {

	public void login(String user, String ip, Boolean success);

	public List<Login> getLoginList(String username);

	public DateTime getLastLogin(String username);

}
