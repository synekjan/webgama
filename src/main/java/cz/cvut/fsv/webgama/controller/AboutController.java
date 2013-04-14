package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/about")
public class AboutController extends MultiActionController {

	@RequestMapping(value = { "", "/project" }, method = RequestMethod.GET)
	protected ModelAndView showAboutProject(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/about/about");

		mav.addObject("ip", request.getRemoteAddr());
		mav.addObject("host", request.getRemoteHost());
		mav.addObject("port", request.getRemotePort());
		mav.addObject("encoding", request.getCharacterEncoding());

		return mav;
	}

	@RequestMapping(value = "/cvut", method = RequestMethod.GET)
	protected ModelAndView showAboutCVUT() {

		return new ModelAndView("/about/cvut");
	}

	@RequestMapping(value = "/author", method = RequestMethod.GET)
	protected ModelAndView showAboutAuthor() {

		return new ModelAndView("/about/author");
	}
	
	@RequestMapping(value = "/contact", method = RequestMethod.GET)
	protected ModelAndView showContact() {

		return new ModelAndView("/about/contact");
	}
	
	@RequestMapping(value = "/thanks", method = RequestMethod.GET)
	protected ModelAndView showThanks() {

		return new ModelAndView("/about/thanks");
	}

}
