package com.netcracker.libra.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.netcracker.libra.model.RegisterForm;
import com.netcracker.libra.service.RegformService;
import com.netcracker.libra.util.security.ConfirmationCodeGenerator;

@Controller
@RequestMapping("register")
@SessionAttributes("regForm")
public class RegController {

	@ModelAttribute("regForm")
	public RegisterForm getRegisterForm() {
		return new RegisterForm();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(@ModelAttribute("regForm") RegisterForm form,
			ModelMap model) {
		model.addAttribute("regForm", form);
		model.put("uniList", RegformService.getUniversityList());
		model.put("facList", RegformService.getFacultyList());
		model.put("deptList", RegformService.getDepartmentList());
		model.put("languages", RegformService.getProgrammingLanguagesList());
		return "/signup/appform";
	}

	@RequestMapping("/next")
	public String validateForm(@Valid RegisterForm form, BindingResult result,
			MultipartFile photo, ModelMap model) {

		if (result.hasErrors()) {
			result.getFieldError().toString();
			return "redirect:register.html";
		} else {

			form.setAppId(RegformService.getAppformId());
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
			model.addAttribute("regForm", form);
			return "redirect:/register/showAppForm.html";
		}
	}

	@RequestMapping(value = "/showAppForm", method = RequestMethod.GET)
	public String showAppForm(ModelMap model,
			@ModelAttribute("regForm") RegisterForm form) {
		model.addAttribute("columns", RegformService.getActiveTemplate());
		form.setTemplateId(RegformService.getActiveTemplateId());
		model.addAttribute("regForm", form);
		return "signup/showAppForm";
	}

	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(ModelMap model,
			@ModelAttribute("regForm") RegisterForm form) {
		form.setGeneratedCode(ConfirmationCodeGenerator.generateCode());
		form.setUserId(RegformService.getUserId());
		model.addAttribute("regForm", form);
		return "signup/review";
	}

	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String reviewForm(ModelMap model,
			@ModelAttribute("regForm") RegisterForm form) {
		return "signup/review";
	}

	@RequestMapping(value = "/verifyCode", method = RequestMethod.POST)
	public String verifyCode(@ModelAttribute("regForm") RegisterForm form,
			ModelMap model) throws SQLException {

		if (form.getEnteredCode().equals(form.getGeneratedCode())) {
			System.out.println("Comparing " + form.getEnteredCode() + " and "
					+ form.getGeneratedCode());
			form.setAppId(RegformService.getAppformId());
			RegformService.registerUser(form);
			return "signup/success";
		}

		else
			System.out.println("Comparing " + form.getEnteredCode() + " and "
					+ form.getGeneratedCode());
		model.addAttribute("regForm", form);
		return "redirect:/register/submit.html";
	}

}
