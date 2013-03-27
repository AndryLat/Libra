/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.InterviewResultsJDBC;
import com.netcracker.libra.dao.UserPreferences;
import com.netcracker.libra.model.InterviewResult;
import com.netcracker.libra.model.InterviewResultsInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author Sashenka
 */
@Controller
public class InterviewResultsController 
{
    @Autowired
    UserPreferences userPreferences;
    InterviewResultsJDBC iresults=new InterviewResultsJDBC();
    //int appId;
    /*Integer count;
    Integer page;
    String order="results";
    boolean desc=true;*/    
    @RequestMapping("resultAjax")
    public ModelAndView ajax(@RequestParam(required=false,value="page") Integer page,
    @RequestParam(required=false,value="count") Integer count,
    @RequestParam(required=false,value="serch") String serch,
    @RequestParam(required=false,value="order") String order,
    @RequestParam(required=false,value="desc") Boolean desc)
    {
        
            
            ModelAndView mav = new ModelAndView();
               
            if(serch!=null)
            {
                String[] s=serch.split("[,\\s]+");
                List<InterviewResultsInfo> inf=iresults.serch(s);
                mav.addObject("showStudents", inf);
                mav.addObject("serch", serch);
                mav.setViewName("resultAjax");  
                return mav;
            }
            
            if(order==null)
            {
                order="results";
            }
            if(desc==null)
            {
                desc=true;
            }
            
            mav.addObject("order",order);
            mav.addObject("desc",desc);
            if(count==null && page==null )
            {            
                mav.addObject("showStudents", iresults.getAllInfo(order,desc));
                mav.setViewName("resultAjax"); 
                mav.addObject("count", null);
                mav.addObject("page", null);
                 return mav;
                
            }
            else
            {
                List<InterviewResultsInfo> inf=iresults.getInfo(order,desc,1+(page-1)*count,page*count);
                mav.addObject("pages",iresults.countPage(count));
                mav.addObject("showStudents", inf);
                mav.addObject("currentpage",page);
                mav.addObject("count",count);
                mav.setViewName("resultAjax");       
                return mav;
            }
           
    }
    @RequestMapping(value="addResult", method= RequestMethod.GET)
    public ModelAndView addResult(@RequestParam("appId") int appId)
    {
        if(userPreferences.accessLevel==1 || userPreferences.accessLevel==2 )
        {
            if(iresults.exists(appId)==0)
            {
                return message("<a href='/Libra/'>Вернуться назад</a>","Такого интервью нету","Ошибка");
            }
           // this.appId=appId;
            ModelAndView mav = new ModelAndView();
            mav.setViewName("addResultView");       
            List<InterviewResult> intres=iresults.getResult(appId);
            mav.addObject("existsComment", iresults.existsComment(userPreferences.UserId, appId));
            mav.addObject("userId", userPreferences.UserId);
            mav.addObject("interviewResult",intres);
            mav.addObject("appId", appId);
            return mav; 
        }
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    @RequestMapping(value="addResultSubmit", method= RequestMethod.POST)
    public ModelAndView addResultSubmit(@RequestParam("mark") int mark,
    @RequestParam("comment") String comment,
    @RequestParam("appId") int appId)
    {
        if(userPreferences.accessLevel==1 || userPreferences.accessLevel==2)
        { 
            try
            {
                iresults.addResult(appId,userPreferences.accessLevel, userPreferences.UserId, mark, comment); 
                return  new ModelAndView("redirect:showResults.html");
            }
            catch(Exception e)
            {
                return message("<a href='showResults.html'>Вернуться назад</a>","Вы не можете добавить комментарий к этой анкете","Ошибка");
            }   
        }
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    @RequestMapping(value="updateResultSubmit", method= RequestMethod.POST)
    public ModelAndView updateResultSubmit(@RequestParam("mark") int mark,
    @RequestParam("comment") String comment,
    @RequestParam("appId") int appId)
    {
        if(userPreferences.accessLevel==1 || userPreferences.accessLevel==2)
        {         
            iresults.updateResult(appId, userPreferences.UserId, mark, comment);
            return  new ModelAndView("redirect:showResults.html");
        }
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    @RequestMapping("showResults")
    public ModelAndView showResults(@RequestParam(required=false,value="page") Integer page,
    @RequestParam(required=false,value="count") Integer count,
    @RequestParam(required=false,value="serch") String serch,
    @RequestParam(required=false,value="order") String order,
    @RequestParam(required=false,value="desc") Boolean desc)
    {
        if(userPreferences.accessLevel==1 || userPreferences.accessLevel==2)
        {
            
            ModelAndView mav = new ModelAndView();
               
            if(serch!=null)
            {
                String[] s=serch.split("[,\\s]+");
                List<InterviewResultsInfo> inf=iresults.serch(s);
                mav.addObject("showStudents", inf);
                mav.addObject("serch", serch);
                mav.setViewName("showResultsView");  
                return mav;
            }
            
            if(order==null)
            {
                order="results";
            }
            if(desc==null)
            {
                desc=true;
            }
            
            mav.addObject("order",order);
            mav.addObject("desc",desc);
            if(count==null && page==null )
            {            
                mav.addObject("showStudents", iresults.getAllInfo(order,desc));
                mav.setViewName("showResultsView"); 
                mav.addObject("count", null);
                mav.addObject("page", null);
                 return mav;
                
            }
            else
            {
                List<InterviewResultsInfo> inf=iresults.getInfo(order,desc,1+(page-1)*count,page*count);
                mav.addObject("pages",iresults.countPage(count));
                mav.addObject("showStudents", inf);
                mav.addObject("currentpage",page);
                mav.addObject("count",count);
                mav.setViewName("showResultsView");       
                return mav;
            }
            /*
            mav.setViewName("showResultsView");        
            return mav; 
            */
        }
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    @RequestMapping(value="deleteResult", method= RequestMethod.GET)
    public ModelAndView delResultSubmit(@RequestParam("appId") int appId)
    {
        if(userPreferences.accessLevel==1 || userPreferences.accessLevel==2)
        {
            iresults.deleteResult(appId,userPreferences.UserId);
            return  new ModelAndView("redirect:showResults.html");
        }
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    public ModelAndView message(String link,String message,String title)
    {
         ModelAndView mav=new ModelAndView();
         mav.setViewName("messageView");
         mav.addObject("link",link);
         mav.addObject("message",message);
         mav.addObject("title",title);
         return mav;
    }
}
