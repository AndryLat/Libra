/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.service;

import org.springframework.web.servlet.ModelAndView;

/**
 * Class contain methods for returning error page
 * @author MorrisDeck
 */
public class ErrorService {

    /**
     * This method return error page with text @Sorry, access to this page has only HR!@
     * @return error page
     */
    public static ModelAndView getHRErrorPage() {
        ModelAndView mav = new ModelAndView("ErrorPage");
        mav.setViewName("ErrorPage");
        String text = "<div class=\"alert alert-error\">\n"
                + "  Извините, доступ к данной странице имеет только HR! \n"
                + "</div>";
        mav.addObject("errorText", text);
        return mav;
    }
    
    /**
     * This method return error page with text 
     * @return error page
     */
    public static ModelAndView getErrorPage(String mes) {
        ModelAndView mav = new ModelAndView("ErrorPage");
        mav.setViewName("ErrorPage");
        String text = "<div class=\"alert alert-error\">\n"
                + "  "+ mes + " \n"
                + "</div>";
        mav.addObject("errorText", text);
        return mav;
    }
}
