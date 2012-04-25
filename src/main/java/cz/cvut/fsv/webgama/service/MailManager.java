package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.domain.User;

public interface MailManager {
	
	public void sendConfirmationEmail(User user);
	
	public void recoverPassword();

}
