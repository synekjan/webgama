package cz.cvut.fsv.webgama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/report")
public class ReportController extends MultiActionController {
	
	@RequestMapping(value = "/bug", method = RequestMethod.GET)
	protected ModelAndView showAboutCVUT() {

		return new ModelAndView("/report/bug");
	}

}
