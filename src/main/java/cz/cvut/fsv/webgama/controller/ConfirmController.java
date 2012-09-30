package cz.cvut.fsv.webgama.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cvut.fsv.webgama.service.UserManager;

@Controller
@RequestMapping("/confirm/**")
public class ConfirmController {

	@Inject
	private UserManager userManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ConfirmController.class);

	@RequestMapping(value = "/email/{uuid}", method = RequestMethod.GET)
	public ModelAndView confirmEmail(@PathVariable String uuid,
			HttpServletRequest request) {

		if (!userManager.isConfirmationIDinDB(uuid)) {
			logger.info("Email confirmation was failed from IP:"
					+ request.getRemoteAddr());
			return new ModelAndView("/confirm/email/failure");
		}

		userManager.confirmEmailAddress(uuid);

		logger.info("Email confirmation was successful from IP:"
				+ request.getRemoteAddr());
		return new ModelAndView("/confirm/email/success");
	}

}
