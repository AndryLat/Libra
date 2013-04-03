/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.VMTemplateJDBC;
import com.netcracker.libra.model.Student;
import com.netcracker.libra.model.VMTemplate;
import com.netcracker.libra.util.mail.SendMailService;
import java.util.HashMap;
import java.util.Map;
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
        boolean isOk;
        //Map map = new HashMap();
        //map.put("user", "Vasya");
        //SendMailService.sendMail("neon-z@mail.ru", map, "test");
        if(template.getContent().equals("")||
                template.getTemplateName().equals("")||
                template.getDescribe().equals("")||
                template.getAuthor().equals("")){
            isOk = false;            
        } else {
            String htmlText = "<html><body>" + template.getContent() + "</body></html>";
            isOk = VMTemplateJDBC.InsertNewVMTemplate(htmlText, template.getTemplateName(), template.getDescribe(), template.getAuthor());
        }
        String text = "";
        ModelAndView mav = new ModelAndView("TemplateCreator", "command", new VMTemplate());
        if (isOk) {
            text = "<div class=\"alert alert-success\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Sucсess!</strong> Шаблон успешно создан!\n"
                    + "</div>";
        } else {
            text = "<div class=\"alert alert-error\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Error!</strong> При создании шаблона произошли ошибки.\n"
                    + "</div>";
        }
        mav.addObject("Text", text );
        return mav;
    }
    

    
    
}
