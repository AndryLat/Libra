/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.InterviewDateJDBC;
import com.netcracker.libra.dao.InterviewJDBC;
import com.netcracker.libra.dao.UserPreferences;
import com.netcracker.libra.model.InterviewDateInfo;
import com.netcracker.libra.util.security.SessionToken;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author Сашенька
 */
@Controller
@SessionAttributes({"regForm", "LOGGEDIN_USER"})
public class InterviewController
{
    /**
     * Return page, where administrator can configurate server  time 
     */
    InterviewDateJDBC interviewDateJDBC=new InterviewDateJDBC();
    InterviewJDBC interviewJDBC=new InterviewJDBC();  
    @RequestMapping(value="admin/timeZone")
    public String index(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token)  
    {
        if(token.getUserAccessLevel()==3)
        {
            model.addAttribute("time", interviewJDBC.getTime());
            model.addAttribute("serverTime", interviewJDBC.getServerTime());
            return "admin/timeZoneView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    /**
     * Configurate server  time 
     */
    @RequestMapping(value="admin/editTimeZoneDiff", method= RequestMethod.POST)
    public String editTimeZoneDiff(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    int diff)  
    {
        if(token.getUserAccessLevel()==3)
        {
            interviewJDBC.updateTimeZone(diff);
            return "redirect:timeZone.html";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    /**
     * Display page, where student can choose time of interviews
     */
    @RequestMapping("showInterviewDate")
    public ModelAndView showInterviewDate(@ModelAttribute("LOGGEDIN_USER") SessionToken token)
    {
        ModelAndView mav = new ModelAndView();

        if(token.getUserAccessLevel()==0)
        {
            List<InterviewDateInfo> ilistHr=interviewDateJDBC.getFreePlaces(1);
            List<InterviewDateInfo> ilistInterview=interviewDateJDBC.getFreePlaces(2);
            mav.addObject("interviewDatesHr", ilistHr);
            mav.addObject("interviewDatesInterview", ilistInterview);
            //Gets Request
            try
            {         
                mav.addObject("requestDateHr", interviewJDBC.getRequestInterviewDate(token.getUserId(),1));
            }
            catch(EmptyResultDataAccessException e)
            {  
            }
            try
            {
                mav.addObject("requestDateInterview", interviewJDBC.getRequestInterviewDate(token.getUserId(),2));           
            }
            catch(EmptyResultDataAccessException e)
            {         
            }
               
            //Gets date on which student is assigned
            //If student doesn't assigned return -1
            try
            {
                int i=interviewJDBC.getInterviewDateByAppId(token.getUserId(),1);
                mav.addObject("interviewDateHr", i);
            }             
            catch(EmptyResultDataAccessException e)
            {
                mav.addObject("interviewDateHr",-1);
            }
            try
            {
                mav.addObject("interviewDateInterview", interviewJDBC.getInterviewDateByAppId(token.getUserId(),2));
            }
            catch(EmptyResultDataAccessException e)
            {
                mav.addObject("interviewDateInterview",-1);
            }
            mav.setViewName("showInterviewDateView");       
            return mav;
        }
        else
        {
                        mav.setViewName("messageView");
            mav.addObject("link","<a href='/Libra/'>Вернуться назад</a>");
            mav.addObject("message","У Вас нету прав на эту страницу");
            mav.addObject("title","Ошибка");
            return mav;
        }
    }
    /**
     * Processes a request to add date od interview for current student 
     */
    @RequestMapping(value="chooseDate", method= RequestMethod.POST)
    public ModelAndView chooseDatePost(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("selDate") int selDate)
    {
        ModelAndView mav = new ModelAndView();
        if(token.getUserAccessLevel()==0)
        {
            if(interviewDateJDBC.exists(selDate)>0)
            {
                interviewJDBC.add(selDate,token.getUserId(), 1);
                
                mav.setViewName("redirect:showInterviewDate.html");       
                return mav;
            }
            else
            {
                 mav.setViewName("messageView");
                mav.addObject("link","<a href='/Libra/'>Вернуться назад</a>");
                mav.addObject("message","У Вас нету прав на эту страницу");
                mav.addObject("title","Ошибка");
                return mav;
            }
        } 
        else
        {
             mav.setViewName("messageView");
            mav.addObject("link","<a href='/Libra/'>Вернуться назад</a>");
            mav.addObject("message","У Вас нету прав на эту страницу");
            mav.addObject("title","Ошибка");
            return mav;
        }
    }
    
    /**
     * Processes a request to change date od interview for current student 
     */
    @RequestMapping(value="changeDate", method= RequestMethod.POST)
    public ModelAndView changeDatePost(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("selDate") int selDate)
    {
        ModelAndView mav = new ModelAndView();
        if(token.getUserAccessLevel()==0)
        {
            if(interviewDateJDBC.exists(selDate)>0)
            {
                int role=interviewJDBC.getRole(selDate);
                //If exists request
                int c=interviewJDBC.exists0(token.getUserId(),role);
                if(c>0)
                {
                    //if selDate=InterviewDateId whith statys=1
                    if(interviewJDBC.exists(selDate, token.getUserId(),role)>0)
                    {
                        //delete request
                        interviewJDBC.deleteRequest(token.getUserId(),role);
                    }
                    else
                    {
                        //update request
                        interviewJDBC.updateRequest(selDate,token.getUserId(),role);
                    }
                }
                else// if doesn't exist request
                {
                    //if selDate != current date
                    if(interviewJDBC.exists(selDate,token.getUserId(),role)==0)
                    {
                        //addRequest
                        interviewJDBC.add(selDate,token.getUserId(), 0);
                    }
                }
                mav.setViewName("redirect:showInterviewDate.html");       
                return mav;
            }
            else
            {
                mav.setViewName("messageView");
                mav.addObject("link","<a href='/Libra/'>Вернуться назад</a>");
                mav.addObject("message","У Вас нету прав на эту страницу");
                mav.addObject("title","Ошибка");
                return mav;
            }
        } 
        else
        {
             mav.setViewName("messageView");
            mav.addObject("link","<a href='/Libra/'>Вернуться назад</a>");
            mav.addObject("message","У Вас нету прав на эту страницу");
            mav.addObject("title","Ошибка");
            return mav;
        }
    }
}
