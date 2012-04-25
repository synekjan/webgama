package cz.cvut.fsv.webgama.service;

import java.util.Date;
import java.util.List;

import cz.cvut.fsv.webgama.domain.Login;

public interface LoginManager {
	
	public void login(String user, String ip, Boolean success);
	
	public List<Login> getLoginList(String username);
	
	public Date getLastLogin(String username);
	

}
