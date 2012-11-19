package cz.cvut.fsv.webgama.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.form.UserForm;

public class UserValidator implements Validator {

	/* doplnit v service-context.xml
	 * 
	 * private UserManager userManager;

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}*/

	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

	}

}
