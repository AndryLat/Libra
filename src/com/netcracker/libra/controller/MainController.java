package com.netcracker.libra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.netcracker.libra.util.security.SessionToken;


@Controller
@SessionAttributes("LOGGEDIN_USER")
public class MainController {
	
	@RequestMapping("/welcome")
	public String redirect(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
		if(token.getUserAccessLevel()==1) {
			return "hr/index";
		}
		if(token.getUserAccessLevel()==2) {
			return "tech/index";
		}
		if(token.getUserAccessLevel()==3) {
			return "admin/index";
		}
		if(token.getUserAccessLevel()==0) {
			return "forward:/register/welcome.html";
		}
		else
			return "index";
	}
	
	@RequestMapping("/index")
	public String showWelcome() {
		return "index2";
	}
	
	@RequestMapping(value = "/showAppForm/{userID}", method=RequestMethod.GET)
	public String showAppForm(@PathVariable Integer userID, ModelMap map) {
		return "showAppForm";
	}
	
	@RequestMapping("/hr/index")
	public String showHRWelcomePage() {
		return "hr/index";
	}
	
	@RequestMapping("/admin/index")
	public String showAdminWelcomePage() {
		return "admin/index";
	}
	
	@RequestMapping("/tech/index")
	public String showTechWelcomePage() {
		return "tech/index";
	}
}
