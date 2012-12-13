package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/adjustment/wizard")
public class AdjustmentWizardController extends MultiActionController {
	
	@RequestMapping(value = {"", "new"}, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		long startTime = System.nanoTime();
		ModelAndView mav = new ModelAndView("/adjustment/wizard/new");

		double time = (double) (System.nanoTime() - startTime) / 1000000;
		mav.addObject("time", time);

		return mav;
	}

}
