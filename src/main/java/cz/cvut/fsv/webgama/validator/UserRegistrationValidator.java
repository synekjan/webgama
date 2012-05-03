package cz.cvut.fsv.webgama.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.service.UserManager;

public class UserRegistrationValidator implements Validator {

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserRegistrationForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserRegistrationForm userForm = (UserRegistrationForm) target;

		
		List<User> list = userManager.getUsersByUsername(userForm.getUsername());

		if (!list.isEmpty()) {
			errors.rejectValue("username", "Used",
					"username is already used");
		}

		// checks if passwords are same
		if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
			errors.rejectValue("password", "NotMatch", "passwords are not same");
			errors.rejectValue("confirmPassword", "NotMatch", "passwords are not same");
		}

	}

}
