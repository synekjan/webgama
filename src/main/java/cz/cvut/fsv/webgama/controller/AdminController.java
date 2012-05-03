package cz.cvut.fsv.webgama.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cz.cvut.fsv.webgama.domain.User;
import cz.cvut.fsv.webgama.service.UserManager;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	@Autowired
	UserManager userManager;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getUserList(HttpServletRequest request, Model model) {

		List<User> list = userManager.getUserList();

		model.addAttribute("userList", list);

		return "/admin/userlist";
	}

}
