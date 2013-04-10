/*
 * This is a main controller of entire application. It serves as router for requests.
 * Also implements some security checks such as user access level.
 * 
 * @author Konstantin Kuyun
 */

package com.netcracker.libra.controller;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.netcracker.libra.model.DisplayAF;
import com.netcracker.libra.util.security.SessionToken;


@Controller
@SessionAttributes("LOGGEDIN_USER")
public class MainController {
	
	Logger log = Logger.getLogger(MainController.class);
	
	@RequestMapping("/welcome")
	public String redirect(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
		if(token.getUserAccessLevel()==1) {
			log.info("Logged as HR");
			return "hr/index";
		}
		if(token.getUserAccessLevel()==2) {
			log.info("Logged as Tech");
			return "tech/index";
		}
		if(token.getUserAccessLevel()==3) {
			log.info("Logged as Admin");
			return "admin/index";
		}
		if(token.getUserAccessLevel()==0) {
			log.info("Logged as Student");
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
