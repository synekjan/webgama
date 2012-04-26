package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import cz.cvut.fsv.webgama.form.UserRegistrationForm;
import cz.cvut.fsv.webgama.service.UserManager;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	@Autowired
	private UserManager userManager;
	
	//@Autowired
	//private MailManager mailManager;
	
	
	@Override
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("/register/register");
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView register(HttpServletRequest request,@ModelAttribute UserRegistrationForm userForm, BindingResult result) {
		
		if (result.hasErrors())
			return new ModelAndView("/register");
		
		userManager.registerUser(userForm);
		
		//mailManager.sendConfirmationEmail(user);
		
		return new ModelAndView("redirect:/register/success");
	}
	
	@RequestMapping(value="/success", method = RequestMethod.GET)
	protected ModelAndView registerSuccess() {
		
		return new ModelAndView("/register/success");
	}
	

}
