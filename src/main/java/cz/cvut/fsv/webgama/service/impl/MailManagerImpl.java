package cz.cvut.fsv.webgama.service.impl;

import java.util.List;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import cz.cvut.fsv.webgama.dao.UserDao;
import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.PasswordRecoveryForm;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.form.UsernameRecoveryForm;
import cz.cvut.fsv.webgama.service.MailManager;
import cz.cvut.fsv.webgama.util.Generator;

public class MailManagerImpl implements MailManager {

	private String addressFrom;

	private MailSender mailSender;

	private UserDao userDao;

	@Override
	public void sendConfirmationEmail(UserRegistrationForm userForm,
			String uuid, String URL) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(addressFrom);
		message.setTo(userForm.getEmail());
		message.setSubject("[WebGama] Confirm your email address");

		message.setText("Hello "
				+ userForm.getUsername()
				+ ",\n\nyou recently registered new account into WebGama.\n\nTo confirm your email address please click on link below:\n\n"
				+ URL + "/confirm/email/" + uuid
				+ "\n\nBest regards,\nWebGama Team");

		mailSender.send(message);

	}

	@Override
	@Transactional
	public void recoverPassword(PasswordRecoveryForm userForm) {

		List<User> list = userDao.findUsersByUsername(userForm.getUsername());

		User user = list.get(0);
		String password = Generator.generatePassword();

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(addressFrom);
		message.setTo(user.getEmail());
		message.setSubject("[WebGama] Password Reset");

		message.setText("Hello,\n\nyou recently requested for the password reset.\n\nThe username for your account is: "
				+ user.getUsername()
				+ "\n\nNew generated password is: "
				+ password + "\n\nBest regards,\nWebGama Team");

		mailSender.send(message);

		user.setPassword(new StandardPasswordEncoder().encode(password));

		userDao.updatePassword(user);

	}

	@Override
	@Transactional
	public void recoverUsername(UsernameRecoveryForm userForm) {

		List<User> list = userDao.findUsersByEmail(userForm.getEmail());

		User user = list.get(0);

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(addressFrom);
		message.setTo(userForm.getEmail());
		message.setSubject("[WebGama] Username Reminder");

		message.setText("Hello,\n\nyou recently requested for the username you registered with your account.\n\nThe username for your account is: "
				+ user.getUsername() + "\n\nBest regards,\nWebGama Team");

		mailSender.send(message);
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setAddressFrom(String addressFrom) {
		this.addressFrom = addressFrom;
	}

}
