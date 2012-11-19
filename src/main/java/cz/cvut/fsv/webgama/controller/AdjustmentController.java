package cz.cvut.fsv.webgama.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class AdjustmentController extends MultiActionController {

	@RequestMapping(value = "/adjustment", method = RequestMethod.GET)
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
}
