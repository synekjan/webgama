package cz.cvut.fsv.webgama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/recover")
public class RecoverController extends MultiActionController {

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public ModelAndView showUsernameRecovery() {

		return new ModelAndView("/recover/username");
	}

	@RequestMapping(value = "/username", method = RequestMethod.POST)
	public ModelAndView recoverUsername() {

		return new ModelAndView("redirect:/recover/username/success");
	}
	
	@RequestMapping(value = "/username/success", method = RequestMethod.GET)
	public ModelAndView showUsernameRecoverySuccess() {

		return new ModelAndView("/recover/usernameSuccess");
	}


	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView showPasswordRecovery() {

		return new ModelAndView("/recover/password");
	}
	
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public ModelAndView recoverPassword() {

		return new ModelAndView("redirect:/recover/password/success");
	}
	
	@RequestMapping(value = "/password/success", method = RequestMethod.GET)
	public ModelAndView showPasswordRecoverySuccess() {

		return new ModelAndView("/recover/passwordSuccess");
	}
	

}
