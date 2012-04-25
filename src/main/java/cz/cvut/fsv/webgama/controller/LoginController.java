package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
@RequestMapping("/login")
public class LoginController extends AbstractController {

	/*@Autowired
	private LoginManager loginManager;*/

	@Override
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("login");
	}

	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView showForm(HttpServletRequest request) {

		// loginManager.login("pepa", request.getRemoteAddr(), true);

		System.out.println("LOGIN POST");

		return new ModelAndView("redirect:/");
	}

}
