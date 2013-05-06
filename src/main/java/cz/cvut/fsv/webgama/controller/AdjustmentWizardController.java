package cz.cvut.fsv.webgama.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Scope;
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

import cz.cvut.fsv.webgama.domain.Cluster;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Network;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Point;
import cz.cvut.fsv.webgama.exception.PermissionDeniedException;
import cz.cvut.fsv.webgama.exception.ResourceNotFoundException;
import cz.cvut.fsv.webgama.form.ClustersWizardForm;
import cz.cvut.fsv.webgama.form.NetworkDefinitionWizardForm;
import cz.cvut.fsv.webgama.form.NetworkParametersWizardForm;
import cz.cvut.fsv.webgama.form.PointsWizardForm;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.CalculationManager;

@Controller
@Scope("session")
@RequestMapping("/adjustment/wizard")
public class AdjustmentWizardController extends MultiActionController implements Serializable {

	@Inject
	private AdjustmentManager adjustmentManager;

	@Inject
	private CalculationManager calculationManager;

	private static final long serialVersionUID = 5171649431095330184L;

	// Convert empty and whitespaces to null
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	private Input input = new Input();

	@RequestMapping(value = { "", "new" }, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/adjustment/wizard/wizard");
		// create new input
		input = new Input();
		input.setNetwork(new Network());
		input.getNetwork().getPoints().add(new Point());
		Cluster cluster = new Cluster();
		cluster.setTagname("obs");
		cluster.setObservation(new Observation());
		input.getNetwork().getClusters().add(cluster);

		NetworkDefinitionWizardForm form = new NetworkDefinitionWizardForm(input);
		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ModelAndView editInput(@PathVariable Long id, HttpServletRequest request) {

		// Check if path variable is in database otherwise throw 404 HTTP Status
		// code
		if (id <= 0L || !calculationManager.isCalculationIdInDB(id)) {
			throw new ResourceNotFoundException();
		}
		String username = request.getUserPrincipal().getName();
		// Check if user has permission to edit
		if (!calculationManager.hasUserPrivilegeToCalculation(id, username)) {
			throw new PermissionDeniedException();
		}

		ModelAndView mav = new ModelAndView("/adjustment/wizard/wizard");
		this.input = calculationManager.getCalculationById(id).getInput();
		NetworkDefinitionWizardForm form = new NetworkDefinitionWizardForm(input);
		mav.addObject("input", form);
		return mav;
	}

	@RequestMapping(value = "/step1", method = RequestMethod.POST, params = "methodType=next")
	protected ModelAndView step1(@Valid @ModelAttribute("input") NetworkDefinitionWizardForm networkDefinitionForm,
			BindingResult result, HttpServletRequest request, Locale locale, HttpServletResponse response) {

		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step1", "errorCount", result.getErrorCount());
		}
		networkDefinitionForm.enrichInput(this.input);

		ModelAndView mav = new ModelAndView("adjustment/wizard/step2");
		NetworkParametersWizardForm form = new NetworkParametersWizardForm(this.input);
		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/step2", method = RequestMethod.POST, params = "methodType=previous")
	protected ModelAndView step2Previous(
			@Valid @ModelAttribute("input") NetworkParametersWizardForm networkParametersForm, BindingResult result,
			HttpServletRequest request, Locale locale, HttpServletResponse response) {

		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step2", "errorCount", result.getErrorCount());
		}
		networkParametersForm.enrichInput(this.input);

		ModelAndView mav = new ModelAndView("adjustment/wizard/step1");
		NetworkDefinitionWizardForm form = new NetworkDefinitionWizardForm(input);

		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/step2", method = RequestMethod.POST, params = "methodType=next")
	protected ModelAndView step2Next(@Valid @ModelAttribute("input") NetworkParametersWizardForm networkParametersForm,
			BindingResult result, HttpServletRequest request, Locale locale, HttpServletResponse response) {

		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step2", "errorCount", result.getErrorCount());
		}
		networkParametersForm.enrichInput(this.input);

		ModelAndView mav = new ModelAndView("adjustment/wizard/step3");
		PointsWizardForm form = new PointsWizardForm(input);

		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/step3", method = RequestMethod.POST, params = "methodType=previous")
	protected ModelAndView step3Previous(@Valid @ModelAttribute("input") PointsWizardForm pointsForm,
			BindingResult result, HttpServletRequest request, Locale locale, HttpServletResponse response) {

		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step3", "errorCount", result.getErrorCount());
		}
		pointsForm.enrichInput(this.input);

		ModelAndView mav = new ModelAndView("adjustment/wizard/step2");
		NetworkParametersWizardForm form = new NetworkParametersWizardForm(this.input);

		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/step3", method = RequestMethod.POST, params = "methodType=next")
	protected ModelAndView step3Next(@Valid @ModelAttribute("input") PointsWizardForm pointsForm, BindingResult result,
			HttpServletRequest request, Locale locale, HttpServletResponse response) {

		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step3", "errorCount", result.getErrorCount());
		}
		pointsForm.enrichInput(this.input);

		ModelAndView mav = new ModelAndView("adjustment/wizard/step4");
		ClustersWizardForm form = new ClustersWizardForm(input);

		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/step4", method = RequestMethod.POST, params = "methodType=previous")
	protected ModelAndView step4Previous(@Valid @ModelAttribute("input") ClustersWizardForm clustersForm,
			BindingResult result, HttpServletRequest request, Locale locale, HttpServletResponse response) {

		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step4", "errorCount", result.getErrorCount());
		}
		clustersForm.enrichInput(this.input);

		ModelAndView mav = new ModelAndView("adjustment/wizard/step3");
		PointsWizardForm form = new PointsWizardForm(input);

		mav.addObject("input", form);

		return mav;
	}

	@RequestMapping(value = "/step4", method = RequestMethod.POST, params = "methodType=submit")
	protected ModelAndView step4Next(@Valid @ModelAttribute("input") ClustersWizardForm clustersForm,
			BindingResult result, HttpServletRequest request, Locale locale, HttpServletResponse response) {

		String username = request.getUserPrincipal().getName();
		if (result.hasErrors()) {
			return new ModelAndView("/adjustment/wizard/step4", "errorCount", result.getErrorCount());
		}
		clustersForm.enrichInput(this.input);

		adjustmentManager.handleWizardForm(this.input, username, locale);

		ModelAndView mav = new ModelAndView("/adjustment/wizard/stepFinish");
		return mav;
	}

}
