package com.netcracker.libra.controller;

import com.netcracker.libra.dao.VMTemplateJDBC;
import com.netcracker.libra.model.VMTemplates.VMTemplate;
import com.netcracker.libra.util.mail.SendMailService;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The controller is responsible for the constructor and the Template Manager
 * @author MorrisDeck
 */
@Controller
public class TemplateCreatorController {
    @Autowired
    ServletContext servletContext;

    /**
     * Open Template Constructor
     * @return 
     */
    @RequestMapping(value = "/TemplateCreator", method = RequestMethod.GET)
    public  ModelAndView getCreatorJSP(){
        return new  ModelAndView("TemplateCreator", "command", new VMTemplate());        
    }
    
    /**
     * Save template in DB
     * @param template
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/takeTemplate", method = RequestMethod.POST)
    public  ModelAndView takeContent(@ModelAttribute("VMTemplate") VMTemplate template) throws IOException{
        boolean isOk;
        if(template.getContent().equals("")||
                template.getTemplateName().equals("")||
                template.getDescribe().equals("")||
                template.getAuthor().equals("")){
            isOk = false;            
        } else {
            String htmlText = "<html><body>" + template.getContent() + "</body></html>";
            isOk = VMTemplateJDBC.InsertNewVMTemplate(template.getTemplateName(), htmlText, template.getDescribe(), template.getAuthor());
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
    
    /**
     * temp test method
     */
    @RequestMapping(value = "/testingsend", method = RequestMethod.GET)
    public static void testSend(){
        Map map = new HashMap();
        map.put("key", "Test!");
        SendMailService.sendMail("neon-z@mail.ru", map, "testing");
    }
    
}
