package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.PasswordRecoveryForm;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.form.UsernameRecoveryForm;
import cz.cvut.fsv.webgama.service.MailManager;
import cz.cvut.fsv.webgama.util.Generator;

public class MailManagerImpl implements MailManager {

	private MailSender mailSender;

	private UserDao userDao;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void sendConfirmationEmail(UserRegistrationForm userForm) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("do-not-reply@gmail.com");
		message.setTo(userForm.getEmail());
		message.setSubject("Confirm your email address");

		message.setText("Tohle je potvrzujici email pro uzivatele "
				+ userForm.getUsername() + " s emailovou adresou: "
				+ userForm.getEmail());

		mailSender.send(message);

	}

	@Override
	public void recoverPassword(PasswordRecoveryForm userForm) {
		
		List<User> list = userDao.findUsersByUsername(userForm.getUsername());
		
		User user = list.get(0);
		String password = Generator.generatePassword();
		
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("not-reply@gmail.com");
		message.setTo(user.getEmail());
		message.setSubject("WebGama: Password Reset");
		
		message.setText("Hello,\n\nyou recently requested for the password reset.\n\nThe username for your account is: "
				+ user.getUsername() + "\n\nNew generated password is: "  + password + "\n\nBest regards\nWebGama Team");

		mailSender.send(message);
		
		user.setPassword(new StandardPasswordEncoder().encode(password));
		
		userDao.updatePassword(user);
		
	}

	@Override
	public void recoverUsername(UsernameRecoveryForm userForm) {

		List<User> list = userDao.findUsersByEmail(userForm.getEmail());

		User user = list.get(0);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("not-reply@gmail.com");
		message.setTo(userForm.getEmail());
		message.setSubject("WebGama: Username Reminder");

		message.setText("Hello,\n\nyou recently requested for the username you registered with your account.\n\nThe username for your account is: "
				+ user.getUsername() + "\n\nBest regards\nWebGama Team");

		mailSender.send(message);
	}
}
