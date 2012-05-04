package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.form.UserRegistrationForm;

public interface MailManager {
	
	public void sendConfirmationEmail(UserRegistrationForm userForm);
	
	public void recoverPassword();
	
	public void recoverUsername();

}
