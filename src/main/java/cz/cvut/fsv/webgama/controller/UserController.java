package cz.cvut.fsv.webgama.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.form.UserForm;
import cz.cvut.fsv.webgama.service.LoginManager;
import cz.cvut.fsv.webgama.service.UserManager;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserManager userManager;

	@Autowired
	private LoginManager loginManager;

	private static final Logger logger = LoggerFactory
			.getLogger(UserController.class);

	@RequestMapping(value = { "", "/info" }, method = RequestMethod.GET)
	public String user(HttpServletRequest request, Model model, Locale locale,
			Principal principal) {

		User user = userManager.getUser(principal.getName());

		model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));
		model.addAttribute("user", user);

		logger.info("User (" + principal.getName() + ") processed from IP: "
				+ request.getRemoteAddr());

		return "/user/user";
	}

	@RequestMapping(value = { "", "/info" }, method = RequestMethod.POST)
	public String modify(@Valid @ModelAttribute UserForm userForm,
			BindingResult result) {

		if (result.hasErrors()) {
			return "redirect:/user";
		}

		userManager.updateUser(userForm);

		return "redirect:/";
	}

	@RequestMapping(value = "/password/change", method = RequestMethod.GET)
	public String changePasswordForm(HttpServletRequest request, Model model,
			Locale locale, Principal principal) {

		// User user = userManager.getUser(principal.getName());

		model.addAttribute("name", principal.getName());
		model.addAttribute("admin", request.isUserInRole("ROLE_ADMIN"));

		return "/user/changepass";
	}

	@RequestMapping(value = "/password/change", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute User user, BindingResult result) {

		// User user = userManager.getUser(principal.getName());

		// logger.info("User (" + principal.getName() + ") processed from IP: "
		// + request.getRemoteAddr());

		return "redirect:/user";
	}

	@RequestMapping(value = { "/logins", "/logins/show" }, method = RequestMethod.GET)
	public ModelAndView showLogins(HttpServletRequest request,
			Principal principal) {

		ModelAndView mav = new ModelAndView("/user/loginslist");

		mav.addObject("loginList",
				loginManager.getLoginList(principal.getName()));
		mav.addObject("admin", request.isUserInRole("ROLE_ADMIN"));
		// mav.addObject("lastlogin",
		// loginManager.getLastLogin(principal.getName()));

		return mav;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request,
			Principal principal) {

		ModelAndView mav = new ModelAndView("/user/delete");

		mav.addObject("admin", request.isUserInRole("ROLE_ADMIN"));

		return mav;
	}

}
