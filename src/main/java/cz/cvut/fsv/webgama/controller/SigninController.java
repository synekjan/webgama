package cz.cvut.fsv.webgama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class SigninController extends MultiActionController {

	/*
	 * @Autowired private LoginManager loginManager;
	 */

	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public ModelAndView login() throws Exception {

		return new ModelAndView("signin");
	}

	@RequestMapping(value = "/signin-failed", method = RequestMethod.GET)
	public ModelAndView loginFailed() throws Exception {

		return new ModelAndView("signin", "error", true);
	}

}
