package cz.cvut.fsv.webgama.controller;

import java.security.Principal;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserForm;
import cz.cvut.fsv.webgama.form.UserPasswordChangeForm;
import cz.cvut.fsv.webgama.service.LoginManager;
import cz.cvut.fsv.webgama.service.UserManager;
import cz.cvut.fsv.webgama.validator.UserPasswordChangeValidator;

@Controller
@RequestMapping("/account")
public class AccountController extends MultiActionController {

	@Inject
	private UserManager userManager;

	@Inject
	private LoginManager loginManager;

	@Inject
	private UserPasswordChangeValidator passwordValidator;

	// @Inject
	// private UserValidator userValidator;

	private static final Logger logger = LoggerFactory
			.getLogger(AccountController.class);

	@RequestMapping(value = { "", "/info" }, method = RequestMethod.GET)
	public ModelAndView accountInfo(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView("/account/info");
		String username = request.getUserPrincipal().getName();

		logger.info("User[" + username + "] checked own account information");
		return mav;
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView modifyUserForm(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView("/account/user");
		String username = request.getUserPrincipal().getName();
		User user = userManager.getUser(username);
		mav.addObject("user", user);

		logger.info("User[" + username + "] checked own personal settings");
		return mav;
	}

	@RequestMapping(value = "/personal", method = RequestMethod.POST)
	public ModelAndView modifyUser(
			@Valid @ModelAttribute("user") UserForm userForm,
			BindingResult result, HttpServletRequest request) {

		// userValidator.validate(userForm, result);

		String username = request.getUserPrincipal().getName();

		if (result.hasErrors()) {

			logger.info("User[" + username
					+ "] was unsuccessful in editing personal informations");
			return new ModelAndView("/account/user");
		}

		userManager.updateUser(userForm);

		logger.info("User[" + username
				+ "] successfully updated personal information");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/password/change", method = RequestMethod.GET)
	public ModelAndView changePasswordForm(HttpServletRequest request,
			Model model, Locale locale) {

		ModelAndView mav = new ModelAndView("/account/changepass");
		String username = request.getUserPrincipal().getName();
		UserPasswordChangeForm userForm = new UserPasswordChangeForm();
		userForm.setUsername(username);
		mav.addObject("user", userForm);

		logger.info("User[" + username + "] thought about changing password");
		return mav;
	}

	@RequestMapping(value = "/password/change", method = RequestMethod.POST)
	public ModelAndView changePassword(
			@Valid @ModelAttribute("user") UserPasswordChangeForm userForm,
			BindingResult result, HttpServletRequest request) {

		passwordValidator.validate(userForm, result);
		String username = request.getUserPrincipal().getName();

		if (result.hasErrors()) {
			logger.info("User[" + username
					+ "] was unsuccessful in changing his/her password");
			return new ModelAndView("/account/changepass");
		}

		userManager.changeUserPassword(userForm);

		logger.info("User[" + username
				+ "] successfully changed account password");
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = { "/logins", "/logins/show" }, method = RequestMethod.GET)
	public ModelAndView showLogins(HttpServletRequest request,
			Principal principal) {

		ModelAndView mav = new ModelAndView("/account/loginslist");
		String username = request.getUserPrincipal().getName();

		mav.addObject("loginList",
				loginManager.getLoginList(principal.getName()));

		logger.info("User[" + username + "] checks own loging status");
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUserForm(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/account/delete");
		String username = request.getUserPrincipal().getName();

		logger.info("User[" + username + "] thought about deleting account");
		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ModelAndView deleteUser(HttpServletRequest request) {

		User user = userManager.getUser(request.getUserPrincipal().getName());
		String username = request.getUserPrincipal().getName();

		userManager.deleteUser(user);

		logger.info("User[" + username + "] DELETED ACCOUNT with success");
		return new ModelAndView("redirect:/logout");
	}

}