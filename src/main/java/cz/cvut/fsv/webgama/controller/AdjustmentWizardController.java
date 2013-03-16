package cz.cvut.fsv.webgama.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.Input;

@Controller
@Scope("session")
@RequestMapping("/adjustment/wizard")
public class AdjustmentWizardController extends MultiActionController implements Serializable {

	private static final long serialVersionUID = 5171649431095330184L;

	private Input input = new Input();

	@RequestMapping(value = { "", "new" }, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		long startTime = System.nanoTime();
		ModelAndView mav = new ModelAndView("/adjustment/wizard/wizard");

		double time = (double) (System.nanoTime() - startTime) / 1000000;
		mav.addObject("time", time);

		input = new Input();

		return mav;
	}

	@RequestMapping(value = "/step1", method = RequestMethod.POST)
	protected ModelAndView step1(HttpServletRequest request, Locale locale, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("adjustment/wizard/step1");

		mav.addObject("input", input);

		return mav;
	}

	@RequestMapping(value = "/step2", method = RequestMethod.POST)
	protected ModelAndView step2(HttpServletRequest request, Locale locale, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("adjustment/wizard/step2");

		mav.addObject("input", input);

		return mav;
	}

	@RequestMapping(value = "/step3", method = RequestMethod.POST)
	protected ModelAndView step3(HttpServletRequest request, Locale locale, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("adjustment/wizard/step3");

		mav.addObject("input", input);

		return mav;
	}

	@RequestMapping(value = "/step4", method = RequestMethod.POST)
	protected ModelAndView step4(HttpServletRequest request, Locale locale, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("adjustment/wizard/step4");

		mav.addObject("input", input);

		return mav;
	}

}
