package cz.cvut.fsv.webgama.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

@Controller
public class ExportController extends MultiActionController {

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	protected ModelAndView exportForm(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView("/export/export");
	}
}
