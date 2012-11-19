package cz.cvut.fsv.webgama.controller;

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
public class ImportController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	@RequestMapping(value = "/import/xml", method = RequestMethod.GET)
	protected ModelAndView showUploadForm() {

		return new ModelAndView("/import/xml");
	}

	@RequestMapping(value = "/import/xml", method = RequestMethod.POST)
	protected ModelAndView uploadXML(@RequestParam("file") MultipartFile file,
			HttpServletRequest request) {

		if (!file.isEmpty()) {

			String username = request.getUserPrincipal().getName();

			adjustmentManager.adjustFromFile(file, username);

			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("/import/xml");
		}
	}

}
