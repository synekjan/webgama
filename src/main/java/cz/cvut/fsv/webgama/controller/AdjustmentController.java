package cz.cvut.fsv.webgama.controller;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.UserManager;


@Controller
@RequestMapping("/adjustment")
public class AdjustmentController extends MultiActionController {

	@Inject
	private UserManager userManager;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	protected ModelAndView adjust(HttpServletRequest request) {

		long startTime = System.nanoTime();
		ModelAndView mav = new ModelAndView("/adjustment/adjustment");
		Calendar cal = Calendar.getInstance();

		Date date = new Date();
		mav.addObject("date", date);
		mav.addObject("cal", cal.get(Calendar.MILLISECOND));

		double time = (double) (System.nanoTime() - startTime) / 1000000;
		mav.addObject("time", time);

		return mav;
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	protected @ResponseBody User getJSON(@PathVariable String name) {
		
		User user = userManager.getUser(name);
		
		return user;
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	protected ModelAndView index(HttpServletRequest request, Locale locale, HttpServletResponse response) {
		
		ModelAndView mav = new ModelAndView("adjustment/test");
		
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
		String dateTime = df.format(date);

		mav.addObject("date", dateTime);
		
		DateTime date1 = new DateTime();
		mav.addObject("dateTime", date1);
		
		double cislo = Math.random();
		mav.addObject("cislo", cislo);
		
		return mav;
	}
	
	
}
