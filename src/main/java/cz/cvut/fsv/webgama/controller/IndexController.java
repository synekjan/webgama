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
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.service.UserManager;
import cz.cvut.fsv.webgama.util.TimeFormatter;

@Controller
public class IndexController {
	
	@Inject
	private UserManager userManager;
	
	@Inject
	private ActivityManager activityManager;
	
	@Inject
	private AdjustmentManager adjustmentManager;
	
	@Inject
	private TimeFormatter timeFormatter;

	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request, Locale locale) {

		if (request.getUserPrincipal() != null
				&& request.isUserInRole("ROLE_USER")) {

			String username = request.getUserPrincipal().getName();
			Date date = new Date();
			DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
			String dateTime = df.format(date);

			ModelAndView mav = new ModelAndView("index");
			mav.addObject("date", dateTime);
			
			mav.addObject("ownCalculationCount", adjustmentManager.getInputCountbyUsername(username));
			
			mav.addObject("locale", locale);
			mav.addObject("timeFormatter", timeFormatter);
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

		String username = request.getUserPrincipal().getName();
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
		String dateTime = df.format(date);

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("date", dateTime);
		
		mav.addObject("ownCalculationCount", adjustmentManager.getInputCountbyUsername(username));
		
		mav.addObject("locale", locale);
		mav.addObject("timeFormatter", timeFormatter);
		mav.addObject("activities", activityManager.getRecentActivitiesByUsername(username));
		
		return mav;
	}

}
