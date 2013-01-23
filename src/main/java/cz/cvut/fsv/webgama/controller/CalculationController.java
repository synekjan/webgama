package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.service.AdjustmentManager;

@Controller
@RequestMapping(value = "/calculation")
public class CalculationController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected ModelAndView calculationList(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/calculation/calculation");

		mav.addObject("inputList", adjustmentManager.getInputsbyUsername(request
				.getUserPrincipal().getName()));

		return mav;
	}

}