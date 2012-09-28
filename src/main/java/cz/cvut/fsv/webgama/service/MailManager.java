package cz.cvut.fsv.webgama.service;

import cz.cvut.fsv.webgama.form.PasswordRecoveryForm;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.form.UsernameRecoveryForm;

public interface MailManager {

    public void sendConfirmationEmail(UserRegistrationForm userForm,
	    String uuid, String URL);

    public void recoverPassword(PasswordRecoveryForm userForm);

    public void recoverUsername(UsernameRecoveryForm userForm);

}
