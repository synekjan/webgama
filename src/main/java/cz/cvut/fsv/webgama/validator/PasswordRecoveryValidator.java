package cz.cvut.fsv.webgama.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.PasswordRecoveryForm;
import cz.cvut.fsv.webgama.service.UserManager;

public class PasswordRecoveryValidator implements Validator {

    private UserManager userManager;

    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
	return PasswordRecoveryForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	PasswordRecoveryForm userForm = (PasswordRecoveryForm) target;

	List<User> list = userManager
		.getUsersByUsername(userForm.getUsername());

	if (list.isEmpty()) {
	    errors.rejectValue("username", "NotFound", "username not found");
	}

    }

}
