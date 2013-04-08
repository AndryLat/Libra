package com.netcracker.libra.controller;

import com.netcracker.libra.dao.ReportOnErrorJDBC;
import com.netcracker.libra.model.errorreport.ROEFixModel;
import com.netcracker.libra.model.errorreport.ReportOnErrorModel;
import java.util.List;
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
public class ReportOnErrorController {
    
    /**
     * Return view for errors reports
     * @return 
     */
    @RequestMapping(value="/ReportOnError", method = RequestMethod.GET)
    public static ModelAndView getReportOnErrorJPS(){
        return new  ModelAndView("ReportOnError", "command", new ReportOnErrorModel());
    }
    
    /**
     * Sending message about error in DB
     * @param ROEmodel
     * @return 
     */
    @RequestMapping(value="/ReportOnErrorSend", method = RequestMethod.POST)
    public static ModelAndView SendReportOnError(@ModelAttribute("ReportOnErrorModel") ReportOnErrorModel ROEmodel){
        ModelAndView mav = new  ModelAndView("ReportOnError", "command", new ReportOnErrorModel());
        String text;
        boolean isOk = false;
        if(ROEmodel.getUserName().equals("") || 
                ROEmodel.getTextError().equals("") ||
                ROEmodel.getTextError().length() > 200 ||
                ROEmodel.getUserName().length() > 100){
           isOk = false; 
        } else {
           if(ReportOnErrorJDBC.insertReportOnError(ROEmodel.getUserName(), ROEmodel.getTextError())!= 0){
              isOk = true; 
           } 
        }
        
        if(isOk){
            text = "<div class=\"alert alert-success\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Sucсess!</strong> Спасибо за помощь! Всего наилучшего!\n"
                    + "</div>";
        } else {
            text = "<div class=\"alert alert-error\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Error!</strong> Вы не правильно оформили форму!\n"
                    + "</div>";
        }
        
        mav.addObject("text", text);
        return mav;
        
    }
    
    /**
     * Return report errors view
     * @return 
     */
    @RequestMapping(value="/ShowReportOnError", method = RequestMethod.GET)
    public static ModelAndView getShowReportOnErrors(){
        List list = ReportOnErrorJDBC.getROEData();
        ModelAndView mav =  new  ModelAndView("ShowReportsOnErrors", "command", new ROEFixModel());
        mav.addObject("errorList", list);
        return mav;
    }
    
    /**
     * Change status choosen error
     * @param fixModel
     * @return 
     */
    @RequestMapping(value="/ShowReportOnErrorFix", method = RequestMethod.POST)
    public static ModelAndView FixShowReportOnErrors(@ModelAttribute("ROEFixModel") ROEFixModel fixModel){
                String text;
        if(ReportOnErrorJDBC.fixROEError(fixModel.getId()) != 0){
            text = "<div class=\"alert alert-success\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Sucсess!</strong> Ошибка исправлена!\n"
                    + "</div>";
        } else {
            text = "<div class=\"alert alert-error\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Error!</strong> Неправильный номер ошибки!\n"
                    + "</div>";
        }
        List list = ReportOnErrorJDBC.getROEData();
        ModelAndView mav =  new  ModelAndView("ShowReportsOnErrors", "command", new ROEFixModel());
        mav.addObject("errorList", list);
        mav.addObject("text", text);
        
        return mav;
    }
    
    
     /**
      * Clear list bug fixes
      * @return 
      */       
    @RequestMapping(value="/ShowReportOnErrorClear", method = RequestMethod.POST)
    public static ModelAndView ClearShowReportOnErrors(){
        String text;
        if(ReportOnErrorJDBC.fixDeleteROEError() != 0){
            text = "<div class=\"alert alert-success\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Sucсess!</strong> Cписок очищен!\n"
                    + "</div>";
        } else {
            text = "<div class=\"alert alert-error\">\n"
                    + "  <button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n"
                    + "  <strong>Error!</strong> Ошибки при очистке!\n"
                    + "</div>";
        }
        List list = ReportOnErrorJDBC.getROEData();
        ModelAndView mav =  new  ModelAndView("ShowReportsOnErrors", "command", new ROEFixModel());
        mav.addObject("errorList", list);
        mav.addObject("text", text);
        
        return mav;
    }        
    
}
