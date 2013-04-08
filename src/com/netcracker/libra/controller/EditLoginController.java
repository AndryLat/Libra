/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.StudentJDBC;
import com.netcracker.libra.util.security.Security;
import com.netcracker.libra.util.security.SessionToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Sashenka
 */
@Controller
@SessionAttributes({"regForm", "LOGGEDIN_USER"})
public class EditLoginController 
{
    
    @RequestMapping(value = "editLogin")
    public ModelAndView editLogin(@ModelAttribute("LOGGEDIN_USER") SessionToken token) 
    {
	if (token.getUserAccessLevel() != -1) 
        {
			return new ModelAndView("login/editLoginView");
    	}
	return new ModelAndView("redirect:/");
    }

	// editLoginSubmit

	@RequestMapping(value = "editLoginSubmit", method = RequestMethod.POST)
	public ModelAndView editLoginSubmit(
                        @ModelAttribute("LOGGEDIN_USER") SessionToken token,
			@RequestParam("AldPassword") String aldPassword,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2) 
        {
		if (token.getUserAccessLevel() != -1) 
                {
			ModelAndView mav = new ModelAndView();
			String er = "";                      
			if (password1.length() <= 5)
				er += "<p>Длина пароля должна быть больше 5 символов</p>";
			if (password1.length() > 20)
				er += "<p>Длина пароля должна быть меньше 20 символов</p>";
			if (password1.equals(password2)
					&& (er.equals(""))
					&& (StudentJDBC.exists(token.getUserId(),
							Security.getMD5hash(aldPassword)) > 0)) 
                        {
				StudentJDBC.updatePassword(token.getUserId(),
						Security.getMD5hash(password1));
				mav.addObject("link", "<a href='/Libra/'>Вернуться назад</a>");
				mav.addObject("message", "Пароль успешно изменен!");
				mav.addObject("title", "Успех");
				mav.setViewName("messageView");
				return mav;
                        }
			mav.addObject("link",
					"<a href='editLogin.html'>Вернуться назад</a>");
			mav.addObject("message", er);
			mav.addObject("title", "Ошибка");
			mav.setViewName("messageView");
			return mav;
		}
		return new ModelAndView("redirect:/");
	}
}
