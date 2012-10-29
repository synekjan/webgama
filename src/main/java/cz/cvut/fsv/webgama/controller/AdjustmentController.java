package cz.cvut.fsv.webgama.controller;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.service.AdjustmentManager;

@Controller
public class AdjustmentController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

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

	@RequestMapping(value = "/adjustment/xml", method = RequestMethod.GET)
	protected ModelAndView showUploadForm() {

		return new ModelAndView("/adjustment/xml/upload");
	}

	@RequestMapping(value = "/adjustment/xml", method = RequestMethod.POST)
	protected ModelAndView uploadXML(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {

		if (!file.isEmpty()) {

			String username = request.getUserPrincipal().getName();

			adjustmentManager.adjustFromFile(file, username);

			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("/adjustment/xml/upload");
		}
	}

}
