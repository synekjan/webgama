package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.form.PasswordRecoveryForm;
import cz.cvut.fsv.webgama.form.UsernameRecoveryForm;
import cz.cvut.fsv.webgama.service.ActivityManager;
import cz.cvut.fsv.webgama.service.MailManager;
import cz.cvut.fsv.webgama.validator.PasswordRecoveryValidator;
import cz.cvut.fsv.webgama.validator.UsernameRecoveryValidator;

@Controller
@RequestMapping("/recover")
public class RecoverController extends MultiActionController {

	@Inject
	private MailManager mailManager;

	@Inject
	private PasswordRecoveryValidator passwordValidator;

	@Inject
	private UsernameRecoveryValidator usernameValidator;
	
	@Inject
	private ActivityManager activityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(RecoverController.class);

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public ModelAndView showUsernameRecovery() {

		UsernameRecoveryForm userForm = new UsernameRecoveryForm();

		return new ModelAndView("/recover/username", "user", userForm);
	}

	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public ModelAndView recoverUsername(
			@Valid @ModelAttribute("user") UsernameRecoveryForm userForm,
			BindingResult result) {

		usernameValidator.validate(userForm, result);

		if (result.hasErrors()) {
			return new ModelAndView("/recover/username");
		}

		mailManager.recoverUsername(userForm);

		return new ModelAndView("redirect:/recover/username/success");
	}

	@RequestMapping(value = "/username/success", method = RequestMethod.GET)
	public ModelAndView showUsernameRecoverySuccess() {

		return new ModelAndView("/recover/usernameSuccess");
	}

	// PASSWORD RECOVERY
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView showPasswordRecovery() {

		PasswordRecoveryForm userForm = new PasswordRecoveryForm();
		
		return new ModelAndView("/recover/password", "user", userForm);
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ModelAndView recoverPassword(
			@Valid @ModelAttribute("user") PasswordRecoveryForm userForm,
			BindingResult result) {

		passwordValidator.validate(userForm, result);

		if (result.hasErrors()) {
			return new ModelAndView("/recover/password");
		}

		mailManager.recoverPassword(userForm);
		
		activityManager.recordActivity(userForm.getUsername(), "activity.password.resetted");
		logger.info("User[" + userForm.getUsername() + "] RESETTED his password");
		return new ModelAndView("redirect:/recover/password/success");
	}

	@RequestMapping(value = "/password/success", method = RequestMethod.GET)
	public ModelAndView showPasswordRecoverySuccess() {

		return new ModelAndView("/recover/passwordSuccess");
	}

}
