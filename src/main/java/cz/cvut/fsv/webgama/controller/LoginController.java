package cz.cvut.fsv.webgama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class LoginController extends MultiActionController {

    /*
     * @Autowired private LoginManager loginManager;
     */

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() throws Exception {

	return new ModelAndView("login");
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public ModelAndView loginFailed() throws Exception {

	return new ModelAndView("login", "error", true);
    }

}
