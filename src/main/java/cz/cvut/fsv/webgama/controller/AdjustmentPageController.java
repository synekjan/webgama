package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.Angle;
import cz.cvut.fsv.webgama.domain.Direction;
import cz.cvut.fsv.webgama.domain.Distance;
import cz.cvut.fsv.webgama.domain.Input;
import cz.cvut.fsv.webgama.domain.Observation;
import cz.cvut.fsv.webgama.domain.Point;
import cz.cvut.fsv.webgama.domain.SlopeDistance;
import cz.cvut.fsv.webgama.domain.ZenithAngle;
import cz.cvut.fsv.webgama.exception.ResourceNotFoundException;
import cz.cvut.fsv.webgama.form.AdjustmentPageForm;
import cz.cvut.fsv.webgama.service.AdjustmentManager;

@Controller
@RequestMapping("/adjustment/onepage")
public class AdjustmentPageController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	private static final Logger logger = LoggerFactory
			.getLogger(AdjustmentPageController.class);

	@RequestMapping(value = { "", "new" }, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm();

		mav.addObject("input", adjustmentForm);

		return mav;
	}

	@RequestMapping(value = { "", "new" }, method = RequestMethod.POST)
	protected ModelAndView postNewInput(
			@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm,
			BindingResult result, HttpServletRequest request) {

		Point point = new Point();
		point.setName("test");

		if (result.hasErrors()) {
			String username = request.getUserPrincipal().getName();
			logger.info("User[" + username + "] had errors["
					+ result.getErrorCount() + "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new");
		}

		System.out.println(adjustmentForm.getSigmaAct());
		System.out.println(adjustmentForm.getDescription());
		int i = 0;
		for (Observation form : adjustmentForm.getObservations()) {
			i++;
			System.out.println(i);

			for (Direction direction : form.getDirections()) {
				System.out.println(direction.getTo());

			}
			for (Distance direction : form.getDistances()) {
				System.out.println(direction.getTo());

			}
			for (Angle direction : form.getAngles()) {
				System.out.println(direction.getBs());

			}
			for (SlopeDistance direction : form.getSlopeDistances()) {
				System.out.println(direction.getTo());

			}
			for (ZenithAngle direction : form.getZenithAngles()) {
				System.out.println(direction.getTo());

			}
		}

		return new ModelAndView("redirect:/calculations");
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	protected ModelAndView editInput(@PathVariable Long id,
			HttpServletRequest request) {
		
		boolean b = adjustmentManager.isInputIdInDB(id);
		
		System.out.println(b);
		
		//Check if path variable is in database otherwise throw 404 HTTP Status code
		if (id <= 0 || !b)  {
			throw new ResourceNotFoundException();
		}

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		Input input = adjustmentManager.getInputById(id);

		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm(input);

		mav.addObject("input", adjustmentForm);

		return mav;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	protected ModelAndView postEditedInput(@PathVariable Long id,
			@Valid @ModelAttribute("input") AdjustmentPageForm adjustmentForm,
			BindingResult result, HttpServletRequest request) {

		if (result.hasErrors()) {
			String username = request.getUserPrincipal().getName();
			logger.info("User[" + username + "] had errors["
					+ result.getErrorCount() + "] in adjustment one page form!");
			return new ModelAndView("/adjustment/onepage/new");
		}

		return new ModelAndView("redirect:/calculations");
	}

}
