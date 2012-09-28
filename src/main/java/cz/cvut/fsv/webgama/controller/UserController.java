package cz.cvut.fsv.webgama.controller;

import java.security.Principal;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
@RequestMapping("/user")
public class UserController extends MultiActionController {

    @Inject
    private UserManager userManager;

    @Inject
    private LoginManager loginManager;

    @Inject
    private UserPasswordChangeValidator passwordValidator;

    // @Inject
    // private UserValidator userValidator;

    /*
     * private static final Logger logger =
     * LoggerFactory.getLogger(UserController.class);
     */

    @RequestMapping(value = { "", "/info" }, method = RequestMethod.GET)
    public String user(HttpServletRequest request, Model model, Locale locale,
	    Principal principal) {

	User user = userManager.getUser(principal.getName());

	model.addAttribute("user", user);

	return "/user/user";
    }

    @RequestMapping(value = { "", "/info" }, method = RequestMethod.POST)
    public ModelAndView modify(
	    @Valid @ModelAttribute("user") UserForm userForm,
	    BindingResult result) {

	// userValidator.validate(userForm, result);

	if (result.hasErrors()) {

	    return new ModelAndView("/user/user");
	}

	userManager.updateUser(userForm);

	return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/password/change", method = RequestMethod.GET)
    public ModelAndView changePasswordForm(HttpServletRequest request,
	    Model model, Locale locale, Principal principal) {

	ModelAndView mav = new ModelAndView("/user/changepass");

	UserPasswordChangeForm userForm = new UserPasswordChangeForm();
	userForm.setUsername(principal.getName());

	mav.addObject("user", userForm);

	return mav;
    }

    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public String changePassword(
	    @Valid @ModelAttribute("user") UserPasswordChangeForm userForm,
	    BindingResult result) {

	passwordValidator.validate(userForm, result);

	if (result.hasErrors()) {
	    return "/user/changepass";
	}

	userManager.changeUserPassword(userForm);

	return "redirect:/";
    }

    @RequestMapping(value = { "/logins", "/logins/show" }, method = RequestMethod.GET)
    public ModelAndView showLogins(HttpServletRequest request,
	    Principal principal) {

	ModelAndView mav = new ModelAndView("/user/loginslist");

	mav.addObject("loginList",
		loginManager.getLoginList(principal.getName()));
	// mav.addObject("lastlogin",
	// loginManager.getLastLogin(principal.getName()));

	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView deleteUserForm(HttpServletRequest request,
	    Principal principal) {

	ModelAndView mav = new ModelAndView("/user/delete");

	return mav;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteUser(Principal principal) {

	User user = userManager.getUser(principal.getName());

	userManager.deleteUser(user);

	ModelAndView mav = new ModelAndView("redirect:/logout");

	return mav;
    }

}
