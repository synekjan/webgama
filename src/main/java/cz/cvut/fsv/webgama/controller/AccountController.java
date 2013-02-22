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
import cz.cvut.fsv.webgama.service.AdjustmentManager;
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
	private AdjustmentManager adjustmentManager;

	@Inject
	private UserPasswordChangeValidator passwordValidator;

	// @Inject
	// private UserValidator userValidator;

	private static final Logger logger = LoggerFactory
			.getLogger(AccountController.class);

	@RequestMapping(value = { "", "/info" }, method = RequestMethod.GET)
	public ModelAndView accountInfo(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/account/info");
		String username = request.getUserPrincipal().getName();
		User user = userManager.getUser(username);
		mav.addObject("user", user);
		mav.addObject("userInputCount", adjustmentManager.getInputCountbyUsername(username));

		logger.info("User[" + username + "] checked own account information");
		return mav;
	}

	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public ModelAndView modifyUserForm(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView("/account/personal/personal");
		String username = request.getUserPrincipal().getName();
		UserForm userForm = new UserForm(userManager.getUser(username));
		mav.addObject("user", userForm);

		logger.info("User[" + username + "] checked own personal settings");
		return mav;
	}

	@RequestMapping(value = { "/personal" }, method = RequestMethod.POST)
	public ModelAndView modifyUser(
			@Valid @ModelAttribute("user") UserForm userForm,
			BindingResult result, HttpServletRequest request) {

		// userValidator.validate(userForm, result);

		String username = request.getUserPrincipal().getName();
		userForm.setUsername(username);

		if (result.hasErrors()) {

			logger.info("User[" + username
					+ "] was unsuccessful in editing personal informations");
			return new ModelAndView("/account/personal/personal");
		}

		userManager.updateUser(userForm);

		logger.info("User[" + username
				+ "] successfully updated personal information");
		return new ModelAndView("redirect:/account/personal?success=true");
	}

/*	@RequestMapping(value = "/personal/success", method = RequestMethod.GET)
	public ModelAndView successUserChange(HttpServletRequest request,
			Locale locale) {

		ModelAndView mav = new ModelAndView("/account/personal/success");
		String username = request.getUserPrincipal().getName();
		UserForm userForm = new UserForm(userManager.getUser(username));
		mav.addObject("user", userForm);

		return mav;
	}*/

	// CHANGING PASSWORD
	@RequestMapping(value = "/password/change", method = RequestMethod.GET)
	public ModelAndView changePasswordForm(HttpServletRequest request,
			Model model, Locale locale) {

		ModelAndView mav = new ModelAndView("/account/password/change");
		String username = request.getUserPrincipal().getName();
		mav.addObject("user", new UserPasswordChangeForm());

		logger.info("User[" + username + "] thought about changing password");
		return mav;
	}

	@RequestMapping(value = { "/password/change" }, method = RequestMethod.POST)
	public ModelAndView changePassword(
			@Valid @ModelAttribute("user") UserPasswordChangeForm userForm,
			BindingResult result, HttpServletRequest request) {

		String username = request.getUserPrincipal().getName();
		userForm.setUsername(username);
		passwordValidator.validate(userForm, result);

		if (result.hasErrors()) {
			logger.info("User[" + username
					+ "] was unsuccessful in changing his/her password");
			return new ModelAndView("/account/password/change");
		}

		userManager.changeUserPassword(userForm);

		logger.info("User[" + username
				+ "] successfully changed account password");
		return new ModelAndView("redirect:/account/password/change?success=true");
	}

/*	@RequestMapping(value = "/password/change/success", method = RequestMethod.GET)
	public ModelAndView successPasswordChange(HttpServletRequest request,
			Model model, Locale locale) {

		ModelAndView mav = new ModelAndView("/account/password/success");
		String username = request.getUserPrincipal().getName();
		mav.addObject("user", new UserPasswordChangeForm());

		logger.info("User[" + username + "] thought about changing password");
		return mav;
	}*/

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

		ModelAndView mav = new ModelAndView("/account/delete/delete");
		String username = request.getUserPrincipal().getName();
		
		mav.addObject("userInputCount", adjustmentManager.getInputCountbyUsername(username));
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
