package cz.cvut.fsv.webgama.controller;

import java.util.Locale;

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
import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Point;
import cz.cvut.fsv.webgama.exception.PermissionDeniedException;
import cz.cvut.fsv.webgama.exception.ResourceNotFoundException;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;
import cz.cvut.fsv.webgama.service.ActivityManager;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.CalculationManager;

@Controller
@RequestMapping("/adjustment/onepage")
public class AdjustmentPageController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	@Inject
	private ActivityManager activityManager;
	
	@Inject
	private CalculationManager calculationManager;

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
		adjustmentForm.getPoints().add(new Point());
		Cluster cluster = new Cluster();
		cluster.setTagname("obs");
		cluster.setObservation(new Observation());
		adjustmentForm.getClusters().add(cluster);

		mav.addObject("input", adjustmentForm);

		return mav;
	}

	@RequestMapping(value = { "", "new" }, method = RequestMethod.POST)
	protected ModelAndView postNewInput(@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm,
			BindingResult result, HttpServletRequest request, Locale locale) {

		String username = request.getUserPrincipal().getName();
		if (result.hasErrors()) {

			logger.info("User[" + username + "] had errors[" + result.getErrorCount()
					+ "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new", "errorCount", result.getErrorCount());
		}

		adjustmentManager.insertNewCalculation(adjustmentForm, username, locale);

		activityManager.recordActivity(username, "activity.calculation.created");
		logger.info("User[" + username + "] successfully insert NEW calculation by one page form!");
		return new ModelAndView("redirect:/calculations");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ModelAndView editInput(@PathVariable Long id, HttpServletRequest request) {

		// Check if path variable is in database otherwise throw 404 HTTP Status
		// code
		if (id <= 0 || !adjustmentManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		String username = request.getUserPrincipal().getName();
		// Check if user has permission to edit
		if (!calculationManager.hasUserPrivilegeToCalculation(id, username)) {
			throw new PermissionDeniedException();
		}

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		Calculation calculation = adjustmentManager.getCalculationById(id);
		Input input = calculation.getInput();
		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm(input);

		mav.addObject("input", adjustmentForm);
		mav.addObject("calculationName", calculation.getName());

		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	protected ModelAndView postEditedInput(@PathVariable Long id,
			@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm, BindingResult result,
			HttpServletRequest request) {

		String username = request.getUserPrincipal().getName();
		if (result.hasErrors()) {
			logger.info("User[" + username + "] had errors[" + result.getErrorCount()
					+ "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new", "errorCount", result.getErrorCount());
		}
		Calculation calculation = adjustmentManager.getCalculationById(id);
		adjustmentManager.updateInputInCalculation(adjustmentForm, calculation);

		logger.info("User[" + username + "] successfully update input of calculation[" + id
				+ "] adjustment one page form!");
		return new ModelAndView("redirect:/calculations");
	}

}
