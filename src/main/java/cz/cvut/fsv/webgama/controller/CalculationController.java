package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping(value = "/calculation")
public class CalculationController extends MultiActionController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected ModelAndView calculationList(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/calculation/calculation");
		return mav;
	}

}
