package org.alekha.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

@Controller
public class WelcomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLoginPage() {
		return "login";
	}

	@RequestMapping(value = "Logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		if (session != null)
			request.removeAttribute("name");
		request.getSession().invalidate();
		return "redirect:/";
	}

	@RequestMapping(value = "Dashboard", method = RequestMethod.GET)
	public String handleLoginRequest(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String name = (String) session.getAttribute("name");
		model.put("name", name);
		return "list";
	}
}