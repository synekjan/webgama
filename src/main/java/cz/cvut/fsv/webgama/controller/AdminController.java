package cz.cvut.fsv.webgama.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.UserManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Inject
	UserManager userManager;

	@RequestMapping(value = {"", "/users"}, method = RequestMethod.GET)
	public ModelAndView showUsers(HttpServletRequest request, Model model) {

		ModelAndView mav = new ModelAndView("/admin/users/users");
		List<User> list = userManager.getUserList();

		mav.addObject("userList", list);

		return mav;
	}
	
	@RequestMapping(value = "/users/delete", method = RequestMethod.GET)
	public ModelAndView deleteUsers(HttpServletRequest request, Model model) {

		ModelAndView mav = new ModelAndView("/admin/users/delete");

		return mav;
	}

}
