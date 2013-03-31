/*
 * This is a main controller of entire application. It serves as router for requests.
 * Also implements some security checks such as user access level.
 */

package com.netcracker.libra.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.netcracker.libra.service.LoginService;
import com.netcracker.libra.service.RegformService;
import com.netcracker.libra.util.security.Security;
import com.netcracker.libra.util.security.SessionToken;

import com.netcracker.libra.dao.StudentJDBC;
import com.netcracker.libra.dao.UserPreferences;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

@Controller
public class LoginController {

	@Autowired
	UserPreferences userPreferences;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showAppform() {
		return "login/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("email") String email, 
			@RequestParam("password") String password,
			SessionStatus status, 
			HttpServletRequest request) throws SQLException {

		String viewName = "login/login";
		ModelAndView mav = new ModelAndView(viewName);
		SessionToken userData;

		userData = LoginService.login(email, password);

		status.setComplete();

		if (userData == null) {
			mav.getModel().put("loginFailedError",
					"Проверьте правильность вводимых значений");
		} else {
			viewName = "redirect:welcome.html";
			//userData.setAppFormFlag(RegformService.isAppFormPresent(userData.getUserId()));
			request.getSession().setAttribute("LOGGEDIN_USER", userData);
		}
		mav.setViewName(viewName);
		return mav;
	}

	@RequestMapping(value = "/logout")
	public String logout(ModelMap model) {
		model.addAttribute("logoutMessage", "Вы вышли из системы");
		return "forward:/index.html";
	}

	@RequestMapping(value = "editLogin")
	public ModelAndView editLogin() {
		if (userPreferences.accessLevel != -1) {
			return new ModelAndView("login/editLoginView");
		}
		return new ModelAndView("redirect:/");
	}

	// editLoginSubmit

	@RequestMapping(value = "editLoginSubmit", method = RequestMethod.POST)
	public ModelAndView editLoginSubmit(
			@RequestParam("AldPassword") String aldPassword,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2) {
		if (userPreferences.accessLevel != -1) {
			ModelAndView mav = new ModelAndView();
			String er = "";
			if (password1.length() >= 6)
				er += "<p>Длина пароля должна быть больше 6 символов</p>";
			if (password1.length() >= 6)
				er += "<p>Длина пароля должна быть меньше 20 символов</p>";
			if (password1.equals(password2)
					&& (er.equals(""))
					&& (StudentJDBC.exists(userPreferences.UserId,
							Security.getMD5hash(aldPassword)) > 0)) {
				StudentJDBC.updatePassword(userPreferences.UserId,
						Security.getMD5hash(password1));
				mav.addObject("link", "<a href='/Libra/'>Вернуться назад</a>");
				mav.addObject("message", "Пароль успешно изменен!");
				mav.addObject("title", "Успех");
				mav.setViewName("messageView");
				return mav;
			}
			mav.addObject("link",
					"<a href='editLogin.html'>Вернуться назад</a>");
			mav.addObject("message", "Произошла ошибка");
			mav.addObject("title", "Ошибка");
			mav.setViewName("messageView");
			return mav;
		}
		return new ModelAndView("redirect:/");
	}
}
