package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.Point;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;

@Controller
@RequestMapping("/adjustment/onepage")
public class AdjustmentPageController extends MultiActionController {

	@RequestMapping(value = { "", "new" }, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm();
		
		Point point = new Point();
		point.setX(15.0);
		point.setY(30.0);
		
		adjustmentForm.getPoints().add(new Point());
		adjustmentForm.getPoints().add(point);
		adjustmentForm.getPoints().add(new Point());
		
		
		mav.addObject("input", adjustmentForm);
		

		return mav;
	}

	@RequestMapping(value = { "", "new" }, method = RequestMethod.POST)
	protected ModelAndView postNewInput(
			@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm,
			BindingResult result, HttpServletRequest request) {
		
		if (result.hasErrors()) {
			String username = request.getUserPrincipal().getName();
			logger.info("User[" + username
					+ "] had errors[" + result.getErrorCount() + "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new");
		}
		
		for (Point point : adjustmentForm.getPoints()) {
			
			System.out.println(point.getX());
			System.out.println(point.getY());
			System.out.println(point.getZ());
			System.out.println("----------------------------");
		}

		return new ModelAndView("redirect:/calculation");
	}

}
