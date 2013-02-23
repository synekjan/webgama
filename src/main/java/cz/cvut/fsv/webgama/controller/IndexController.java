package cz.cvut.fsv.webgama.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.cvut.fsv.webgama.service.ActivityManager;
import cz.cvut.fsv.webgama.service.UserManager;

@Controller
public class IndexController {
	
	@Inject
	private UserManager userManager;
	
	@Inject
	private ActivityManager activityManager;

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request, Locale locale) {

		if (request.getUserPrincipal() != null
				&& request.isUserInRole("ROLE_USER")) {
			long startTime = System.nanoTime();

			String username = request.getUserPrincipal().getName();
			Date date = new Date();
			DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
			String dateTime = df.format(date);

			ModelAndView mav = new ModelAndView("index");
			mav.addObject("date", dateTime);
			double time = (double) (System.nanoTime() - startTime) / 1000000;
			mav.addObject("time", time);
			
			mav.addObject("activities", activityManager.getRecentActivitiesByUsername(username));
			return mav;

		} else {
			
			int userCount = userManager.getUserCount();

			logger.info("Welcome page was requested from IP: "
					+ request.getRemoteHost());
			return new ModelAndView("welcome", "userCount", userCount);
		}
	}

	@RequestMapping("/overview")
	public ModelAndView overview(HttpServletRequest request, Locale locale) {

		long startTime = System.nanoTime();

		String username = request.getUserPrincipal().getName();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
		String dateTime = df.format(date);

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("date", dateTime);
		double time = (double) (System.nanoTime() - startTime) / 1000000;
		mav.addObject("time", time);
		
		mav.addObject("activities", activityManager.getRecentActivitiesByUsername(username));
		
		return mav;
	}

}
