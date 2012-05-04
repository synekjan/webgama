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
	
	@RequestMapping(value = "/password", method = RequestMethod.GET)
	public ModelAndView showPasswordRecovery() {
		
		return new ModelAndView("/recover/password");
	}
	

}
