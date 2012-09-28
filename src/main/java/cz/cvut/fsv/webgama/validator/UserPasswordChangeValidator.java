package cz.cvut.fsv.webgama.validator;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserPasswordChangeForm;
import cz.cvut.fsv.webgama.service.UserManager;

public class UserPasswordChangeValidator implements Validator {

    private UserManager userManager;

    public void setUserManager(UserManager userManager) {
	this.userManager = userManager;
    }

    @Override
    public boolean supports(Class<?> clazz) {
	return UserPasswordChangeForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

	UserPasswordChangeForm userForm = (UserPasswordChangeForm) target;

	User user = userManager.getUser(userForm.getUsername());
	StandardPasswordEncoder encoder = new StandardPasswordEncoder();

	if (!encoder.matches(userForm.getOldPassword(), user.getPassword())) {
	    errors.rejectValue("oldPassword", "NotMatch",
		    "password does not match");
	}

	if (!userForm.getNewPassword().equals(userForm.getConfirmNewPassword())) {
	    errors.rejectValue("newPassword", "NotMatch",
		    "passwords do not match");
	    errors.rejectValue("confirmNewPassword", "NotMatch",
		    "passwords do not match");
	}

    }

}
