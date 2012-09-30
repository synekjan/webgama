package cz.cvut.fsv.webgama.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserForm;
import cz.cvut.fsv.webgama.service.UserManager;

public class UserValidator implements Validator {

	private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		UserForm userForm = (UserForm) target;

		List<User> list = userManager.getUsersByEmail(userForm.getEmail());
		if (!list.isEmpty() /*
							 * &&
							 * !userForm.getEmail().equals(list.get(0).getEmail
							 * ())
							 */) {
			errors.rejectValue("email", "Used", "email address is already used");
		}

	}

}
