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
import cz.cvut.fsv.webgama.form.AngleForm;
import cz.cvut.fsv.webgama.form.DirectionForm;
import cz.cvut.fsv.webgama.form.DistanceForm;
import cz.cvut.fsv.webgama.form.ObservationForm;
import cz.cvut.fsv.webgama.form.PointForm;
import cz.cvut.fsv.webgama.form.SlopeDistanceForm;
import cz.cvut.fsv.webgama.form.ZenithAngleForm;

@Controller
@RequestMapping("/adjustment/onepage")
public class AdjustmentPageController extends MultiActionController {

	@RequestMapping(value = { "", "new" }, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/adjustment/onepage/new");

		AdjustmentPageForm adjustmentForm = new AdjustmentPageForm();

		PointForm point = new PointForm();
		point.setName("A");
		point.setX(15.0);
		point.setY(30.0);
		point.setX(45.0);

		adjustmentForm.getPoints().add(point);
		adjustmentForm.getPoints().add(point);
		adjustmentForm.getPoints().add(point);
		
		ObservationForm form = new ObservationForm();
		form.setFrom("Test");
		form.setOrientation("Homo");
		form.setFromDh(85.6);
		DirectionForm dirForm = new DirectionForm();
		dirForm.setTo("Ahoj");
		DirectionForm dirForm2 = new DirectionForm();
		dirForm2.setTo("Nazdar");
		
		form.getDirections().add(dirForm);
		form.getDirections().add(dirForm2);
		form.getDistances().add(new DistanceForm());
		form.getAngles().add(new AngleForm());
		form.getSlopeDistances().add(new SlopeDistanceForm());
		form.getZenithAngles().add(new ZenithAngleForm());

		adjustmentForm.getObservations().add(form);

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
		for (ObservationForm form : adjustmentForm.getObservations()) {
			i++;
			System.out.println(i);
			
			for (DirectionForm direction : form.getDirections()) {
				System.out.println(direction.getTo());
				
			}
			for (DistanceForm direction : form.getDistances()) {
				System.out.println(direction.getTo());
				
			}
			for (AngleForm direction : form.getAngles()) {
				System.out.println(direction.getBs());
				
			}
			for (SlopeDistanceForm direction : form.getSlopeDistances()) {
				System.out.println(direction.getTo());
				
			}
			for (ZenithAngleForm direction : form.getZenithAngles()) {
				System.out.println(direction.getTo());
				
			}
		}

		return new ModelAndView("redirect:/calculation");
	}

}
