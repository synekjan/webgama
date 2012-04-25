package cz.cvut.fsv.webgama.service.impl;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.MailManager;

public class MailManagerImpl implements MailManager {

	private MailSender mailSender;

	// private UserDao userDao;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	/*
	 * public void setUserDao(UserDao userDao) { this.userDao = userDao; }
	 */

	@Override
	public void sendConfirmationEmail(User user) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("do-not-reply@gmail.com");
		message.setTo(user.getEmail());
		message.setSubject("Complete Registration!");
		message.setText("Tohle je potvrzujici email pro uzivatele "
				+ user.getUsername() + " s emailovou adresou: "
				+ user.getEmail());

		mailSender.send(message);

	}

	@Override
	public void recoverPassword() {
		// TODO Auto-generated method stub

	}

}
