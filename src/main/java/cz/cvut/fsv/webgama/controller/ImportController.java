package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.service.ActivityManager;
import cz.cvut.fsv.webgama.service.AdjustmentManager;

@Controller
public class ImportController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;
	
	@Inject 
	private ActivityManager activityManager;
	
	private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

	@RequestMapping(value = "/import/xml", method = RequestMethod.GET)
	protected ModelAndView showUploadForm() {

		return new ModelAndView("/import/xml");
	}

	@RequestMapping(value = "/import/xml", method = RequestMethod.POST)
	protected ModelAndView uploadXML(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {

		if (!file.isEmpty() || file.getSize() < 1000000) {

			
			String username = request.getUserPrincipal().getName();

			String result = adjustmentManager.adjustFromFile(file, username);
			ModelAndView mav = new ModelAndView("/import/result");
			mav.addObject("result", result);
			activityManager.recordActivity(username, "activity.xml.feed.imported");
			logger.info(username + " uploaded file: " + file.getOriginalFilename());
			return mav;
		} else {
			return new ModelAndView("/import/xml");
		}
	}
	
	
	/*@RequestMapping(value = "/import/result", method = RequestMethod.GET)
	protected ModelAndView success(HttpServletRequest request) {
		
		return new ModelAndView("/import/result");
	}*/
	

}
