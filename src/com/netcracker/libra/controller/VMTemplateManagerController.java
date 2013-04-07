package com.netcracker.libra.controller;

import com.netcracker.libra.dao.VMTemplateJDBC;
import com.netcracker.libra.model.VMTemplates.VMTemplateDelete;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller is responsible for Template Manager
 * @author MorrisDeck
 */
@Controller
public class VMTemplateManagerController {
    
    /**
     * Create view for Template Manager
     * @return 
     */
    @RequestMapping(value = "/VMTemplateManager", method = RequestMethod.GET)
    public  ModelAndView getCreatorJSP(){
        ModelAndView mav = new ModelAndView("VMTemplateManager", "command", new VMTemplateDelete());
        mav.addObject("templates", VMTemplateJDBC.getVMTemplateData());
        return mav;
    }
    
    /**
     * This method delete choosen template from DB
     * @param template template name
     * @return 
     */
    @RequestMapping(value = "/VMTemplateManagerDelete", method = RequestMethod.POST)
    public  ModelAndView deleteVMTemplate(@ModelAttribute("VMTemplate") VMTemplateDelete template){
        ModelAndView mav = new ModelAndView("VMTemplateManager", "command", new VMTemplateDelete());
        mav.addObject("templates", VMTemplateJDBC.getVMTemplateData());
        String text;
        boolean isOK = false;
        if (!template.getName().equals("")) {
            if (VMTemplateJDBC.deleteVMTemplate(template.getName()) != 0) {
                isOK = true;
            }
        }
        if (isOK) {
            text = "<div class=\"alert alert-success\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Sucсess!</strong> Шаблон успешно удален!\n"
                    + "</div>";
        } else {
            text = "<div class=\"alert alert-error\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Error!</strong> При удалении шаблона произошли ошибки.\n"
                    + "</div>";
        }
        mav.addObject("text", text);
        return mav;
    }
    
}
