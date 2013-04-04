/*
 * Handles requests from AJAX select forms on pages. Contains methods for retrieving Lists of Universities, 
 * faculties and departments from persistent storage
 * 
 * @author Konstantin Kuyun
 */
package com.netcracker.libra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.netcracker.libra.service.RegformService;

@Controller
@RequestMapping("ajax")
public class AjaxController {
	
	@RequestMapping(value = "/university", method=RequestMethod.GET)
	public ModelAndView ajaxGetUniversities() {
		ModelAndView model = new ModelAndView();
		model.addObject("univers", RegformService.getUniversityList());
		model.setViewName("ajax/university");
		return model;
	}

	@RequestMapping(value = "/faculty", method = RequestMethod.POST)
	public ModelAndView ajaxGetFaculties(@RequestParam("universityId") int universityId) {
		ModelAndView model = new ModelAndView();
		model.addObject("fact", RegformService.getFacultyListByUniversityID(universityId));
		model.setViewName("ajax/faculty");
		return model;
	}

	@RequestMapping(value = "/department", method = RequestMethod.POST)
	public ModelAndView ajaxGetDepartments(@RequestParam("facultyId") int facultyId) {
		ModelAndView model = new ModelAndView();
		model.addObject("dept", RegformService.getDepartmentListByFacultyID(facultyId));
		model.setViewName("ajax/department");
		return model;
	}
}
