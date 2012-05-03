package cz.cvut.fsv.webgama.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

@Controller
public class AdjustmentController extends AbstractController {

	@Override
	@RequestMapping(value = "/adjustment", method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		long startTime = System.nanoTime();

		ModelAndView mav = new ModelAndView("/adjustment/adjustment");

		request.isUserInRole("ROLE_ADMIN");
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		
		mav.addObject("date", date);
		mav.addObject("cal", cal.get(Calendar.MILLISECOND));

		double time = (double) (System.nanoTime() - startTime) / 1000000;

		mav.addObject("time", time);

		return mav;

	}
	
	@RequestMapping(value = "/adjustment/xml", method = RequestMethod.GET)
	protected ModelAndView uploadXML() {
		
		return new ModelAndView("/adjustment/xml/upload");
	}
	

}
