package cz.cvut.fsv.webgama.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
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
	
	private Input input;

	@RequestMapping(value = {"", "new"}, method = RequestMethod.GET)
	protected ModelAndView newInput(HttpServletRequest request) {

		long startTime = System.nanoTime();
		ModelAndView mav = new ModelAndView("/adjustment/wizard/new");

		double time = (double) (System.nanoTime() - startTime) / 1000000;
		mav.addObject("time", time);
		
		input = new Input();
		input.setAlgorithm(request.getUserPrincipal().getName());

		return mav;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	protected ModelAndView index(HttpServletRequest request, Locale locale, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("adjustment/wizard/step2");
		
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
		String dateTime = df.format(date);

		mav.addObject("date", dateTime);
		
		DateTime date1 = new DateTime();
		mav.addObject("dateTime", date1);
		
		double cislo = Math.random();
		mav.addObject("cislo", cislo);
		
		mav.addObject("input", input);
		
		return mav;
	}

}
