package com.netcracker.libra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class IndexController {
 
	@RequestMapping("/index")
    public ModelAndView helloWorld() {
 
        String message = "�������� ��������: ";
        return new ModelAndView("index", "message", message);
 
	}
 
}