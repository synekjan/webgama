package cz.cvut.fsv.webgama.controller;

import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.Calculation;
import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.CalculationManager;
import cz.cvut.fsv.webgama.service.UserManager;
import cz.cvut.fsv.webgama.util.JsonResponse;
import cz.cvut.fsv.webgama.util.TimeFormatter;

@Controller
public class CalculationsController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	@Inject
	private TimeFormatter timeFormatter;

	@Inject
	private CalculationManager calculationManager;

	@Inject
	private UserManager userManager;

	@Inject
	private MessageSource messageSource;

	@RequestMapping(value = "/calculations", method = RequestMethod.GET)
	protected ModelAndView calculationList(HttpServletRequest request, Locale locale) {

		ModelAndView mav = new ModelAndView("/calculations/calculations");
		String username = request.getUserPrincipal().getName();

		mav.addObject("myCalculations", adjustmentManager.getCalculationsbyUsername(username));

		mav.addObject("sharedCalculations", adjustmentManager.getSharedCalculationsbyUsername(username));

		mav.addObject("locale", locale);
		mav.addObject("timeFormatter", timeFormatter);
		return mav;
	}

	@RequestMapping(value = "/calculation/delete", method = RequestMethod.POST)
	protected @ResponseBody
	String deleteCalculation(@RequestParam Long id, HttpServletRequest request) {

		calculationManager.deleteCalculation(id);

		return "OK";
	}

	@RequestMapping(value = "/calculation/calculate", method = RequestMethod.POST)
	protected @ResponseBody
	JsonResponse calculateCalculation(@RequestParam String language, @RequestParam String algorithm,
			@RequestParam Integer angUnits, @RequestParam Long id, HttpServletRequest request,
			HttpServletResponse response) {

		String username = request.getUserPrincipal().getName();
		Calculation calculation = adjustmentManager.getCalculationById(id);
		calculation.setLanguage(language);
		calculation.setAlgorithm(algorithm);
		calculation.setAngUnits(angUnits);

		JsonResponse jsonResponse = new JsonResponse();

		ProcessOutput processOutput = calculationManager.calculate(calculation, username);
		jsonResponse.setRunningTime(processOutput.getRunningTime());

		if (processOutput.getExitValue() != 0) {
			jsonResponse.setError(true);
			String errorStreamMessage = processOutput.getErrorMessage();
			if (errorStreamMessage == null || "".equals(errorStreamMessage)) {
				jsonResponse.setMessage(processOutput.getXmlResult());
			} else {
				jsonResponse.setMessage(errorStreamMessage);
			}
			return jsonResponse;
		}

		jsonResponse.setMessage("OK");
		return jsonResponse;
	}

	@RequestMapping(value = "/calculation/share", method = RequestMethod.POST)
	protected @ResponseBody
	JsonResponse shareCalculation(@RequestParam String user, @RequestParam Long id, Locale locale,
			HttpServletRequest request, HttpServletResponse response) {

		String username = request.getUserPrincipal().getName();
		JsonResponse jsonResponse = new JsonResponse();

		//I cant share to myself
		if (username.equals(user)) {
			jsonResponse.setError(true);
			jsonResponse.setMessage(messageSource
					.getMessage("privilege.error.has.access", new Object[] { user }, locale));
			return jsonResponse;
		}
		
		Long privilegeId = calculationManager.insertUserPrivelegeToCalculation(id, user);

		//user not in db
		if (privilegeId == -1L) {
			jsonResponse.setError(true);
			jsonResponse.setMessage(messageSource
					.getMessage("privilege.error.not.found", new Object[] { user }, locale));
			return jsonResponse;
		}
		//user already has access
		if (privilegeId == -2L) {
			jsonResponse.setError(true);
			jsonResponse.setMessage(messageSource
					.getMessage("privilege.error.has.access", new Object[] { user }, locale));
			return jsonResponse;
		}

		jsonResponse.setError(false);
		jsonResponse.setRunningTime(privilegeId.doubleValue());
		jsonResponse.setMessage("OK");
		return jsonResponse;
	}

	@RequestMapping(value = "/calculation/privilege/delete", method = RequestMethod.POST)
	protected @ResponseBody
	String deleteCalculationPrivilege(@RequestParam Long id, HttpServletRequest request) {

		calculationManager.deleteCalculationPrivilege(id);

		return "OK";
	}

	@RequestMapping(value = "/calculation/user/find", method = RequestMethod.GET)
	protected @ResponseBody
	List<String> getUserList(@RequestParam("term") String term, HttpServletRequest request) {

		term.toLowerCase();
		List<String> list = userManager.getUsernamesByTerm(term);

		return list;
	}

	/*
	 * @RequestMapping(value = "/calculation/check", method =
	 * RequestMethod.POST) protected @ResponseBody String
	 * checkProgress(@RequestParam Long id, HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * String username = request.getUserPrincipal().getName();
	 * adjustmentManager.getCalculationById(id);
	 * 
	 * return "calculated"; }
	 */

}
