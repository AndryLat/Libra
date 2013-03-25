package com.netcracker.libra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.netcracker.libra.dao.UserPreferences;


@Controller
public class MainController {
	
	@Autowired
	UserPreferences userPreferences;
	
	@RequestMapping("index")
	public String redirect() {
		if (userPreferences.accessLevel==0)
			return "redirect:/register/welcome.html";
		if (userPreferences.accessLevel==1)
			return "redirect:/hr/index.html";
		if (userPreferences.accessLevel==2)
			return "redirect:/tech/index.html";
		if (userPreferences.accessLevel==3)
			return "redirect:/admin/index.html";
		else
			return "redirect:/";
	}
	
	@RequestMapping("welcome")
	public String showWelcome() {
		return "welcome";
	}
	
	@RequestMapping(value = "showAppForm/{userID}", method=RequestMethod.GET)
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
