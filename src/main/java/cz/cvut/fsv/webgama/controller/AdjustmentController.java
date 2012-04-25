package cz.cvut.fsv.webgama.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.UserManager;

@Controller
public class AdjustmentController extends AbstractController {

	@Autowired
	private UserManager userManager;
	
	@DateTimeFormat
	private Date date;

	@Override
	@RequestMapping(value = "/adjustment", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long startTime = System.nanoTime();

		ModelAndView mav = new ModelAndView("/adjustment/adjustment");
		
		User user = userManager.getUser(5);

		boolean perm = request.isUserInRole("ROLE_ADMIN");

		if (perm) {
			System.out.println("ANO");
		} else {
			System.out.println("NE");
		}

		String firstName = user.getFirstName();
		String lastName = user.getLastName();
		
		date = new Date();
		mav.addObject("date", date);

		mav.addObject("firstName", firstName);
		mav.addObject("lastName", lastName);
		mav.addObject("admin", request.isUserInRole("ROLE_ADMIN"));

		double time = (double) (System.nanoTime() - startTime) / 1000000;

		mav.addObject("time", time);

		return mav;

	}
	
	@RequestMapping(value = "/adjustment/xml", method = RequestMethod.GET)
	protected ModelAndView uploadXML() {
		
		return new ModelAndView("/adjustment/xml/upload");
	}
	

}
