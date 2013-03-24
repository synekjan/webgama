package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.exception.PermissionDeniedException;
import cz.cvut.fsv.webgama.exception.ResourceNotFoundException;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;
import cz.cvut.fsv.webgama.service.AdjustmentManager;

@Controller
@RequestMapping("/adjustment/onepage")
public class AdjustmentPageController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	private static final Logger logger = LoggerFactory.getLogger(AdjustmentPageController.class);

	// Convert empty and whitespaces to null
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	@RequestMapping(value = { "", "new" }, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm();

		mav.addObject("input", adjustmentForm);

		return mav;
	}

	@RequestMapping(value = { "", "new" }, method = RequestMethod.POST)
	protected ModelAndView postNewInput(@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm,
			BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			String username = request.getUserPrincipal().getName();
			logger.info("User[" + username + "] had errors[" + result.getErrorCount()
					+ "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new", "errorCount", result.getErrorCount());
		}

		System.out.println(adjustmentForm.getSigmaAct());
		System.out.println(adjustmentForm.getDescription());

		return new ModelAndView("redirect:/calculations");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ModelAndView editInput(@PathVariable Long id, HttpServletRequest request) {

		// Check if path variable is in database otherwise throw 404 HTTP Status
		// code
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}

		Calculation calculation = adjustmentManager.getCalculationById(id);
		// Check if user has permission to edit
		if (!request.getUserPrincipal().getName().equals(calculation.getUser().getUsername())) {
			throw new PermissionDeniedException();
		}

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		Input input = calculation.getInput();
		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm(input);

		mav.addObject("input", adjustmentForm);

		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	protected ModelAndView postEditedInput(@PathVariable Long id,
			@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm, BindingResult result,
			HttpServletRequest request) {

		if (result.hasErrors()) {
			String username = request.getUserPrincipal().getName();

			logger.info("User[" + username + "] had errors[" + result.getErrorCount()
					+ "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new", "errorCount", result.getErrorCount());
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		adjustmentManager.updateInputInCalculation(adjustmentForm, calculation);

		/*
		 * for (Point point : adjustmentForm.getPoints()) {
		 * System.out.print(point.getId() + " ");
		 * System.out.print(point.getName() + " ");
		 * 
		 * System.out.print(point.getX() + " "); System.out.print(point.getY() +
		 * " "); System.out.println(point.getZ() + " ");
		 * 
		 * System.out.println("--------"); }
		 */

		return new ModelAndView("redirect:/calculations");
	}

}
