/*
 * @author Konstantin Kuyun
 */
package com.netcracker.libra.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.netcracker.libra.service.LoginService;
import com.netcracker.libra.service.RegformService;
import com.netcracker.libra.util.security.SessionToken;

import com.netcracker.libra.dao.UserPreferences;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LoginController {

	@Autowired
	UserPreferences userPreferences;
	
	Logger log = Logger.getLogger(LoginController.class);

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showAppform() {
		return "login/login";
	}
	
	
	/*
	 * After successful login populates POJO object with values from persistent 
	 * storage and puts it into current session as @ModelAttribute
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login (@RequestParam("email") String email, 
			@RequestParam("password") String password,
			SessionStatus status, 
			HttpServletRequest request) {

		String viewName = "login/login";
		ModelAndView mav = new ModelAndView(viewName);
		SessionToken userData = null;
		
		try {
			userData = LoginService.login(email, password);
		}
		catch (EmptyResultDataAccessException e) {
			mav.getModel().put("invalidCredentialsMessage", "Проверьте правильность вводимых значений");
			log.warn("Login failed. User not found in DB");
			return mav;
		}
		
		status.setComplete();

		viewName = "redirect:welcome.html";
		
		if (userData.getUserAccessLevel()==0) {
			userData.setAppFormFlag(RegformService.isAppFormPresent(userData.getUserId()));
		}
		
		request.getSession().setAttribute("LOGGEDIN_USER", userData);
		log.info("Login successful. UserID is " + userData.getUserId());
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = "/logout")
	public String logout(ModelMap model) {
		model.addAttribute("logoutMessage", "Вы вышли из системы");
		return "forward:/index.html";
	}

}
