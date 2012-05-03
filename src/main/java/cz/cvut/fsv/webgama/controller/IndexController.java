package cz.cvut.fsv.webgama.controller;

import java.security.Principal;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.cvut.fsv.webgama.service.LoginManager;

@Controller
public class IndexController {
	
	@Autowired
	LoginManager loginManager;	
	
	private static final Logger logger = LoggerFactory
			.getLogger(IndexController.class);

	@RequestMapping("/")
	public String index(HttpServletRequest request, Model model, Locale locale, Principal principal) {

		long startTime = System.nanoTime();
		
		//model.addAttribute("lastlogin", loginManager.getLastLogin(principal.getName()));
		
		Date date = new Date();
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL,locale);
		String dateTime = df.format(date);
		
		
		model.addAttribute("date", dateTime);

		logger.info("Welcome in WebGama application!");

		double time = (double) (System.nanoTime() - startTime) / 1000000;

		model.addAttribute("time", time);

		return "index";
	}
}
