/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.model.Student;
import com.netcracker.libra.model.VMTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author MorrisDeck
 */
@Controller
public class TemplateCreatorController {
    
    @RequestMapping(value = "/TemplateCreator", method = RequestMethod.GET)
    public static ModelAndView getCreatorJSP(){
        return new  ModelAndView("TemplateCreator", "command", new VMTemplate());        
    }
    
    @RequestMapping(value = "/takeTemplate", method = RequestMethod.POST)
    public static ModelAndView takeContent(@ModelAttribute("VMTemplate") VMTemplate template){
        System.out.println(template.getContent());
        return new ModelAndView("blank");
    }
    
    
}
