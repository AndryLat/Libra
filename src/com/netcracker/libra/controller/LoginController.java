package com.netcracker.libra.controller;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.netcracker.libra.dao.StudentJDBC;
import com.netcracker.libra.model.Student;
import com.netcracker.libra.util.security.Security;
import org.springframework.dao.EmptyResultDataAccessException;
import com.netcracker.libra.dao.UserPreferences;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	private JdbcTemplate jdbcTemplateObject;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		 this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
   @RequestMapping(value = "login", method = RequestMethod.GET)
   public ModelAndView login() {
      return new ModelAndView("login/login", "command", new Student());
   }
   
    @Autowired
    UserPreferences userPreferences;
   @RequestMapping(value = "submit", method = RequestMethod.POST)
   public String verify(@ModelAttribute("Student") Student student) {
        try{
              int id=StudentJDBC.verifyLogin(student.getEmail(), Security.getMD5hash(student.getPassword()));	                 
              int acess=StudentJDBC.getAccess(id);
              userPreferences.UserId=id;
              userPreferences.accessLevel=acess;
              return "login/loginSuccesful";
        }
        catch(EmptyResultDataAccessException e)
        {
             return "login/loginFailed";
        }   
     }
   @RequestMapping(value = "logout")
   public ModelAndView logout() 
   {
      ModelAndView mav=new ModelAndView();
      mav.setViewName("index");
      userPreferences.UserId=-1;
      userPreferences.accessLevel=-1;
      return mav;
   }
   
   @RequestMapping(value = "editLogin")
   public ModelAndView editLogin() 
   {
       if(userPreferences.accessLevel!=-1)
       {
        return new ModelAndView("login/editLoginView");
       }
       return new ModelAndView("redirect:/");
   }
   
   //editLoginSubmit
   
   @RequestMapping(value = "editLoginSubmit", method= RequestMethod.POST)
   public ModelAndView editLoginSubmit(@RequestParam("AldPassword") String aldPassword,
            @RequestParam("password1") String password1,
            @RequestParam("password2") String password2) 
   {
       if(userPreferences.accessLevel!=-1)
       {
            ModelAndView mav=new ModelAndView();
            if(password1.equals(password2)&&(password1.length()>=6&&password1.length()<=20)&&(StudentJDBC.exists(userPreferences.UserId, Security.getMD5hash(aldPassword))>0))
            {
                StudentJDBC.updatePassword(userPreferences.UserId, Security.getMD5hash(password1));
                mav.addObject("link", "<a href='/Libra/'>Вернуться назад</a>");
             mav.addObject("message", "Пароль успешно изменен!");
             mav.addObject("title", "Успех");
             mav.setViewName("messageView");
             return mav;
            }
             mav.addObject("link", "<a href='editLogin.html'>Вернуться назад</a>");
             mav.addObject("message", "Произошла ошибка");
             mav.addObject("title", "Ошибка");
             mav.setViewName("messageView");
             return mav;
        }
       return new ModelAndView("redirect:/");
   }
}

