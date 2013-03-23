package cz.cvut.fsv.webgama.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.CalculationManager;
import cz.cvut.fsv.webgama.util.TimeFormatter;

@Controller
public class CalculationsController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;
	
	@Inject
	private TimeFormatter timeFormatter;
	
	@Inject
	private CalculationManager calculationManager;

	@RequestMapping(value = "/calculations", method = RequestMethod.GET)
	protected ModelAndView calculationList(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView("/calculations/calculations");
		
		mav.addObject("myCalculations", adjustmentManager.getCalculationsbyUsername(request
				.getUserPrincipal().getName()));
		
		/*mav.addObject("sharedCalculations", adjustmentManager.getInputsbyUsername("gita"));*/

		mav.addObject("locale", locale);
		mav.addObject("timeFormatter", timeFormatter);
		return mav;
	}
	
	@RequestMapping(value = "/calculation/delete", method = RequestMethod.POST)
	protected @ResponseBody String deleteCalculation(@RequestParam Long id, HttpServletRequest request) {
		
		calculationManager.deleteCalculation(id);
			
		return "OK";
	}
	
	@RequestMapping(value = "/calculation/calculate", method = RequestMethod.POST)
	protected @ResponseBody String calculateCalculation(@RequestParam Long id, HttpServletRequest request) {
		
		System.out.println(id);
		String username = request.getUserPrincipal().getName();
		System.out.println(username);
		calculationManager.calculate(id, username);
			
		return "OK";
	}

}
