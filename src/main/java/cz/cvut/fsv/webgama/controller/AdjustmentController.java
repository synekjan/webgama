package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.service.UserManager;

@Controller
@RequestMapping("/adjustment")
public class AdjustmentController extends MultiActionController {

	@Inject
	private UserManager userManager;

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected ModelAndView adjust(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/adjustment/adjustment");

		return mav;
	}

	/*
	 * @RequestMapping(value = "/{name}", method = RequestMethod.GET) protected
	 * @ResponseBody User getJSON(@PathVariable String name) {
	 * 
	 * User user = userManager.getUser(name);
	 * 
	 * return user; }
	 */
}
