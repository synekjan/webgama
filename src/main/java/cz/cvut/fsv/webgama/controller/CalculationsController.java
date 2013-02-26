package cz.cvut.fsv.webgama.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.util.TimeFormatter;

@Controller
public class CalculationsController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;
	
	@Inject
	private TimeFormatter timeFormatter;

	@RequestMapping(value = "/calculations", method = RequestMethod.GET)
	protected ModelAndView calculationList(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView("/calculations/calculations");
		
		mav.addObject("myCalculations", adjustmentManager.getInputsbyUsername(request
				.getUserPrincipal().getName()));
		
		mav.addObject("sharedCalculations", adjustmentManager.getInputsbyUsername("gita"));

		mav.addObject("locale", locale);
		mav.addObject("timeFormatter", timeFormatter);
		return mav;
	}

}
