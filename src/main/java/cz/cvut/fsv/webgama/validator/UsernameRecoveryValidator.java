package cz.cvut.fsv.webgama.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UsernameRecoveryForm;
import cz.cvut.fsv.webgama.service.UserManager;

public class UsernameRecoveryValidator implements Validator {

	private UserManager userManager;
	
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return UsernameRecoveryForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		UsernameRecoveryForm userForm = (UsernameRecoveryForm) target;
		
		List<User> list = userManager.getUsersByEmail(userForm.getEmail());
		
		if (list.isEmpty()) {
			errors.rejectValue("email", "NotFound", "email address not found");
		}
		

	}

}
