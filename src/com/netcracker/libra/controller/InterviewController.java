/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.InterviewDateJDBC;
import com.netcracker.libra.dao.InterviewJDBC;
import com.netcracker.libra.dao.UserPreferences;
import com.netcracker.libra.model.InterviewDateInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author Сашенька
 */
@Controller
public class InterviewController
{
    InterviewDateJDBC interviewDateJDBC=new InterviewDateJDBC();
    InterviewJDBC interviewJDBC=new InterviewJDBC();
    
    @Autowired
    UserPreferences userPreferences;
        
    //return new ModelAndView("redirect:showTypes.html");
    @RequestMapping("showInterviewDate")
    public ModelAndView showInterviewDate()
    {
        ModelAndView mav = new ModelAndView();

        if(userPreferences.accessLevel==0)
        {
            int request=0;
            List<InterviewDateInfo> ilistHr=interviewDateJDBC.getFreePlaces(1);
            List<InterviewDateInfo> ilistInterview=interviewDateJDBC.getFreePlaces(2);
            mav.addObject("interviewDatesHr", ilistHr);
            mav.addObject("interviewDatesInterview", ilistInterview);
            //Gets Request
            try
            {         
                mav.addObject("requestDateHr", interviewJDBC.getRequestInterviewDate(userPreferences.UserId,1));
                request=1;
            }
            catch(EmptyResultDataAccessException e)
            {  
            }
            try
            {
                mav.addObject("requestDateInterview", interviewJDBC.getRequestInterviewDate(userPreferences.UserId,2));           
                request=1;
            }
            catch(EmptyResultDataAccessException e)
            {         
            }
                mav.addObject("request", request);           
            
            //Gets date on which student is assigned
            //If student doesn't assigned return -1
            try
            {
                int i=interviewJDBC.getInterviewDateByAppId(userPreferences.UserId,1);
                mav.addObject("interviewDateHr", i);
            }             
            catch(EmptyResultDataAccessException e)
            {
                mav.addObject("interviewDateHr",-1);
            }
            try
            {
                mav.addObject("interviewDateInterview", interviewJDBC.getInterviewDateByAppId(userPreferences.UserId,2));
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
    
    @RequestMapping(value="chooseDate", method= RequestMethod.POST)
    public ModelAndView chooseDatePost(@RequestParam("selDate") int selDate)
    {
        ModelAndView mav = new ModelAndView();
        if(userPreferences.accessLevel==0)
        {
            if(interviewDateJDBC.exists(selDate)>0)
            {
                interviewJDBC.add(selDate, userPreferences.UserId, 1);
                
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
    
    @RequestMapping(value="changeDate", method= RequestMethod.POST)
    public ModelAndView changeDatePost(@RequestParam("selDate") int selDate)
    {
        ModelAndView mav = new ModelAndView();
        if(userPreferences.accessLevel==0)
        {
            if(interviewDateJDBC.exists(selDate)>0)
            {
                int role=interviewJDBC.getRole(selDate);
                //If exists request
                int c=interviewJDBC.exists0(userPreferences.UserId,role);
                if(c>0)
                {
                    //if selDate=InterviewDateId whith statys=1
                    if(interviewJDBC.exists(selDate, userPreferences.UserId,role)>0)
                    {
                        //delete request
                        interviewJDBC.deleteRequest(userPreferences.UserId,role);
                    }
                    else
                    {
                        //update request
                        interviewJDBC.updateRequest(selDate,userPreferences.UserId,role);
                    }
                }
                else// if doesn't exist request
                {
                    //if selDate != current date
                    if(interviewJDBC.exists(selDate, userPreferences.UserId,role)==0)
                    {
                        //addRequest
                        interviewJDBC.add(selDate, userPreferences.UserId, 0);
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
