package com.netcracker.libra.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.netcracker.libra.dao.UserPreferences;
import com.netcracker.libra.model.RegisterForm;
import com.netcracker.libra.service.RegformService;
import com.netcracker.libra.util.mail.MailService;
import com.netcracker.libra.util.security.ConfirmationCodeGenerator;

@Controller
@SessionAttributes("regForm")
@RequestMapping("register")
public class RegController {
	
	@Autowired
	UserPreferences userPreferences;

	@ModelAttribute("regForm")
	public RegisterForm getRegisterForm() {
		return new RegisterForm();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@ModelAttribute("regForm") RegisterForm form, ModelMap model) {
		if (userPreferences.UserId==-1) {
			model.put("regForm", form);
			return "/signup/appform";
		}
		else
			return "redirect:index.html";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String renderForm(@ModelAttribute("regForm") @Valid RegisterForm form, BindingResult result) {
		if(result.hasErrors())
			return "/signup/appform";
		else {
			form.setUserId(RegformService.getUserId());
			form.setAppId(RegformService.getAppformId());
			userPreferences.userEmail=form.getEmail();
			RegformService.registerUser(form);
			return "signup/welcome";
		}
	}
	
	@RequestMapping(value = "/welcome", method=RequestMethod.GET)
	public String showWelcomePage(@ModelAttribute("regForm") RegisterForm form, ModelMap model){
		if (userPreferences.accessLevel==0) {
			if (userPreferences.isAppformFilled) {
				form.setUserId(userPreferences.UserId);
				return "welcome";
			}
			else
				return "signup/welcome";
		}
		else
			return "redirect:index.html";
	}
/*
	@RequestMapping(value = "/welcome", method=RequestMethod.POST)
	public String showWelcomePageOnSubmit(@ModelAttribute("regForm")RegisterForm form, BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			model.put("regForm", form);
			return "redirect:/register.html";
		}
		else

			return "signup/welcome";
	}
*/
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showAppForm(@ModelAttribute("regForm") RegisterForm form, ModelMap model) {
		model.addAttribute("columns", RegformService.getActiveTemplate());
		form.setTemplateId(RegformService.getActiveTemplateId());
		model.put("regForm", form);
		return "signup/showAppForm";
	}
	
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public String validateForm(@ModelAttribute("regForm")RegisterForm form, BindingResult result, MultipartFile photo, ModelMap model) {

		if (result.hasErrors()) {
			return "redirect:/signup.html";
			
		} else {	
			if (!photo.isEmpty()) {
				String orgName = photo.getOriginalFilename();
				String filePath = form.getAppId() + ".png";
				File dest = new File(filePath);

				try { 
					photo.transferTo(dest);
				} catch (IllegalStateException e) {
					e.printStackTrace();
					return "File uploaded failed:" + orgName;
				} catch (IOException e) {
					e.printStackTrace();
					return "File uploaded failed:" + orgName;
				}
			}
			form.setGeneratedCode(ConfirmationCodeGenerator.generateCode());
			//MailService.sendConfirmRegistrationMessage(userPreferences.userEmail, userPreferences.UserId.toString(), form.getGeneratedCode());
			model.put("regForm", form);
			return "signup/review";
		}
	}

	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	public String verifyCode(@ModelAttribute("regForm") RegisterForm form, ModelMap model) throws SQLException {
		if (form.getEnteredCode().equals(form.getGeneratedCode())) {
			model.put("regForm", form);
			RegformService.insertAppformAnswers(form, form.getUserId());
			return "signup/success";
		}

		else {
			model.addAttribute("regForm", form);
			model.addAttribute("message", "Введенный код не совпадает с кодом из письма");
			return "signup/review";
		}
	}

}
