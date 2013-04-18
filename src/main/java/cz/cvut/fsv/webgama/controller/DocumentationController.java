package cz.cvut.fsv.webgama.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
@RequestMapping("/documentation")
public class DocumentationController extends MultiActionController {

	@RequestMapping(value = "", method = RequestMethod.GET)
	protected ModelAndView showDocumentation() {

		return new ModelAndView("/documentation/documentation");
	}

}
