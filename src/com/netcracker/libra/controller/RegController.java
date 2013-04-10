/*
 * This class is responsible for handling user registration and application form submitting and processing.
 * All methods are self-explanatory, i guess.
 * 
 * @author Konstantin Kuyun
 */
package com.netcracker.libra.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.netcracker.libra.model.RegisterForm;
import com.netcracker.libra.service.LoginService;
import com.netcracker.libra.service.RegformService;
import com.netcracker.libra.util.mail.MailService;
import com.netcracker.libra.util.security.ConfirmationCodeGenerator;
import com.netcracker.libra.util.security.SessionToken;

@Controller
@RequestMapping("register")
@SessionAttributes({"LOGGEDIN_USER", "regForm"})
public class RegController {
	
	Logger log = Logger.getLogger(RegController.class);
	
	@ModelAttribute("regForm")
	public RegisterForm createForm() {
		return new RegisterForm();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@ModelAttribute("regForm") RegisterForm form, ModelMap model) {
			model.addAttribute("regForm", form);
			return "signup/appform";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String verifyForm(@ModelAttribute("regForm") @Valid RegisterForm form, 
			BindingResult result, 
			ModelMap model, 
			SessionStatus status, 
			HttpServletRequest request) throws SQLException {
		
		if (result.hasErrors()) {
			model.put("regForm", form);
			return "signup/appform";
		}
		
		else {
			form.setUserId(RegformService.getUserId());
			form.setAppId(RegformService.getAppformId());
			RegformService.registerUser(form);
			request.getSession().setAttribute("LOGGEDIN_USER", LoginService.login(form.getEmail(), form.getPassword()));
			log.info("Created new user with Email: " + form.getEmail());
			return "redirect:/register/welcome.html";
		}
	}
	
	@RequestMapping(value = "/welcome", method=RequestMethod.GET)
	public String showWelcomePage() {
		return "signup/welcome";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String showAppForm(@ModelAttribute("LOGGEDIN_USER") SessionToken token, 
			@ModelAttribute("regForm") RegisterForm form, ModelMap model) {
		if (!RegformService.isAppFormPresent(token.getUserId())) {
			model.addAttribute("columns", RegformService.getActiveTemplate());
			model.put("regForm", form);
			return "signup/showAppForm";
			}
		else
			token.setAppFormFlag(true);
			log.info("Appform is already present for userID "+token.getUserId());
			return "signup/welcome";
	}
	
	@RequestMapping(value = "/signup", method=RequestMethod.POST)
	public String validateForm(@ModelAttribute("regForm") 
			RegisterForm form, 
			BindingResult result, 
			MultipartFile photo,
			@ModelAttribute("LOGGEDIN_USER") SessionToken token,
			ModelMap model) {

		if (result.hasErrors()) {
			return "redirect:/register/signup.html";
			
		} else {	
			if (!photo.isEmpty()) {
				String orgName = photo.getOriginalFilename();
				String filePath = form.getAppId() + ".png";
				File dest = new File(filePath);
				log.info("Absolute path for upload is "+dest.getAbsolutePath());

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
			form.setTemplateId(RegformService.getActiveTemplateId());
			MailService.sendConfirmRegistrationMessage(token.getUserEmail(), token.getUserId().toString(), form.getGeneratedCode());
			log.info("Confirmation code for userID "+token.getUserId()+" is " + form.getGeneratedCode());
			log.info("Email send to " + token.getUserEmail());
			model.put("regForm", form);
			return "signup/review";
		}
	}

	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	public String verifyCode(@ModelAttribute("regForm") RegisterForm form, ModelMap model, 
			@ModelAttribute("LOGGEDIN_USER") SessionToken token) throws SQLException {
		
		log.info("Entered code is "+form.getEnteredCode());
		log.info("Entered code is "+form.getGeneratedCode());
		
		if (form.getEnteredCode().equals(form.getGeneratedCode())) {
			log.info("Inserting application form answers...");
			RegformService.insertAppformAnswers(form, token.getUserId());
			token.setAppFormFlag(true);
			model.addAttribute("appformFilledMessage", "Вы успешно заполнили анкету");
			log.info("Done.");
			return "signup/welcome";
		}

		else {
			model.addAttribute("message", "Введенный код не совпадает с кодом из письма");
			log.error("Invalid confirmation code");
			return "signup/review";
		}
	}

}
