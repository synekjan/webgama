package cz.cvut.fsv.webgama.controller;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import cz.cvut.fsv.webgama.domain.ProcessOutput;
import cz.cvut.fsv.webgama.form.UploadForm;
import cz.cvut.fsv.webgama.service.ActivityManager;
import cz.cvut.fsv.webgama.service.AdjustmentManager;
import cz.cvut.fsv.webgama.validator.UploadValidator;

@Controller
public class ImportController extends MultiActionController {

	@Inject
	private AdjustmentManager adjustmentManager;

	@Inject
	private ActivityManager activityManager;

	@Inject
	private UploadValidator uploadValidator;

	private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

	@InitBinder
	public void initBinder(final DataBinder binder) {
		binder.setValidator(uploadValidator);
	}

	@RequestMapping(value = "/import/xml", method = RequestMethod.GET)
	protected ModelAndView showUploadForm() {

		return new ModelAndView("/import/xml", "uploadForm", new UploadForm());
	}

	@RequestMapping(value = "/import/xml", method = RequestMethod.POST)
	protected ModelAndView uploadXML(@Valid @ModelAttribute("uploadForm") UploadForm uploadForm, BindingResult result,
			HttpServletRequest request, Locale locale) {

		String username = request.getUserPrincipal().getName();

		// upload file check
		if (result.hasErrors()) {
			return new ModelAndView("/import/xml");
		}

		MultipartFile file = uploadForm.getFile();
		ProcessOutput processOutput = adjustmentManager.adjustFromFile(file, username, locale);

		// GNU Gama errors check
		if (processOutput.getExitValue() != 0) {
			return new ModelAndView("/import/xml", "gamaError", processOutput.getErrorMessage());
		}

		activityManager.recordActivity(username, "activity.xml.feed.imported");
		logger.info(username + " uploaded and adjusted file: " + file.getOriginalFilename());
		return new ModelAndView("redirect:/calculations");
	}

}
