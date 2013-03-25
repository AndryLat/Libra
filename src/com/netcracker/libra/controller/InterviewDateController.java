/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.netcracker.libra.dao.InterviewDateJDBC;
import com.netcracker.libra.model.InterviewDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Yuliya
 */
@Controller
public class InterviewDateController 
{    
   
    InterviewDate iDate=new InterviewDate();
    InterviewDateJDBC iDateJdbc = new InterviewDateJDBC();
    
    @RequestMapping("/hr/interviewDate")
    public ModelAndView interDate()
    {
        ModelAndView mav = new ModelAndView();
        iDateJdbc.deleteNotInterviewers();
        List<InterviewDate> id=iDateJdbc.getAllInterviewDatesWithInterviewers();  
        List<Map<String,Object>> inters=iDateJdbc.getInterviewersHr();
        mav.setViewName("hr/interviewDate");
        mav.addObject("Model",id);
        mav.addObject("Inters",inters);
        return mav;
     }
    
    @RequestMapping("hr/showInterviewDateSearch")
    public ModelAndView interSearch(org.springframework.web.context.request.WebRequest webRequest,
    @RequestParam("interSearch") int searchInt){
        String textBoxCalc = webRequest.getParameter("textBoxCalc");
        String textBoxText = webRequest.getParameter("textBox");
        String textBox=null;
        if ((!textBoxText.equals(""))&&(searchInt!=2)){
            textBox=textBoxText;
        }
        if ((!textBoxCalc.equals(""))&&(searchInt==2)){
            textBox=textBoxCalc;
        }
        ModelAndView mav=new ModelAndView();
        mav.addObject("interSearchInt", searchInt);
        List<InterviewDate> dates= null;
        if (searchInt==1){
            int n = Integer.parseInt(textBox);
            dates=iDateJdbc.getAllInterviewDatesWithInterviewersById(n);
        }
        else {
            if(searchInt==2){
                dates=iDateJdbc.getAllInterviewDatesWithInterviewersByDate(textBox.replace('/', '.'));
            }
            else{
                if(searchInt==3){
                    dates=iDateJdbc.getAllInterviewDatesWithInterviewersByInterviewers(textBox);
                }
                else {
                    dates=iDateJdbc.getAllInterviewDatesWithInterviewers();
                }
            }
        }
       
        mav.addObject("textBox" , textBox);
        mav.addObject("dates", dates);
        mav.setViewName("hr/showInterviewDateSearch");
        return mav;
    }
    
    @RequestMapping("/hr/interviewDateAdd")
    public ModelAndView interDateAdd()
    {
        ModelAndView mav = new ModelAndView();
        List<InterviewDate> id=iDateJdbc.getAllInterviewDatesWithInterviewers();  
        List<Map<String,Object>> intersHr=iDateJdbc.getInterviewersHr();
        List<Map<String,Object>> intersTech=iDateJdbc.getInterviewersTech();
        mav.addObject("Inters",intersHr); 
        mav.addObject("intersTech",intersTech);
        mav.setViewName("hr/interviewDateAdd");
        mav.addObject("Model",id);
        return mav;
     }
    /**
     * 
     * @param begin
     * @param end
     * @param duration
     * @param interviewers
     * @return 
     */
      @RequestMapping(value="hr/interviewDateAdded", method= RequestMethod.GET)
    @SuppressWarnings("empty-statement")
      public ModelAndView addInterviewDate(@RequestParam("begin") String begin,
      @RequestParam("type") int typeInt,
      @RequestParam("end") String end,  
      @RequestParam("timeStart") String timeStart,
      @RequestParam("duration") int duration,
      @RequestParam("checkInterviewers[]") int [] interviewers){
            ModelAndView mav=new ModelAndView();
            String type="Tech";
            if (typeInt==1){ 
                type="HR";
            }
            String beginInter=begin+" "+timeStart;
            String endInter = begin+" "+end;
            List<Integer> freeIntersList = new ArrayList();
            List<Map<String,Object>> freeInters=iDateJdbc.getFreeInterviewers(type, beginInter, endInter);
            for (Map<String, Object> freeInter : freeInters) {
                for (Map.Entry<String, Object> entry : freeInter.entrySet()) {
                    String k=entry.getValue().toString();
                freeIntersList.add(Integer.parseInt(k));
            }}
           
            for (int i=0;i<interviewers.length;i++){
                int index =freeIntersList.indexOf(interviewers[i]);
                if (index==-1){
                    List<Map<String,Object>> nameIn=iDateJdbc.getNameOfUser(interviewers[i]);
                       for (Map<String, Object> name:nameIn){
                          for (Map.Entry<String, Object> entryT: name.entrySet())
                          mav.addObject("errorMessage", "Интервьер "+entryT.getValue()+" уже назначен на это же время!");
                }
                mav.addObject("typeInt", typeInt);
                mav.addObject("begin",begin);
                mav.addObject("end",end);
                mav.addObject("timeStart", timeStart);
                mav.addObject("duration", duration);
                List<Map<String,Object>> intersHr=iDateJdbc.getInterviewersHr();
                List<Map<String,Object>> intersTech=iDateJdbc.getInterviewersTech();
                 mav.addObject("Inters",intersHr); 
                mav.addObject("intersTech",intersTech); 
                mav.addObject("checkedIntersHr", interviewers);
                mav.addObject("checkedIntersTech", interviewers);                
                mav.setViewName("/hr/interviewDateAdd");
                return mav;
                   }
                }
            
            int interviewDateId = iDateJdbc.createInterviewDate(beginInter,endInter, duration);
            for (int i=0;i<interviewers.length;i++){
                iDateJdbc.insertInterviewersAndDates(interviewers[i],interviewDateId,type);
            }
            List<InterviewDate> id=iDateJdbc.getAllInterviewDatesWithInterviewers();  
            List<Map<String,Object>> intersHr=iDateJdbc.getInterviewersHr();
            List<Map<String,Object>> intersTech=iDateJdbc.getInterviewersTech();
            mav.addObject("Model",id);
            mav.addObject("Inters",intersHr); 
            mav.addObject("intersTech",intersTech);
            mav.setViewName("hr/interviewDate");
            mav.addObject("message", "Дата успешно добавлена");
            return mav;
      }
      
    @RequestMapping(value="hr/interviewers", method=RequestMethod.POST)
    public ModelAndView getInterviewers(@RequestParam("intersType") int interviewerType){
        ModelAndView mav = new ModelAndView();
        return mav;
    }
      
    @RequestMapping(value="hr/editInterviewDate", method= RequestMethod.GET)
    public ModelAndView editDate(@RequestParam("interviewDateId") int interviewDateId,
    @RequestParam("type") String typeInterview
            ){
        ModelAndView mav = new ModelAndView();
        try{
        List<Map<String,Object>> uncheckedIntersHr=iDateJdbc.getInterviewersHrNotInInterview(interviewDateId);
        List<Map<String,Object>> uncheckedIntersTech=iDateJdbc.getInterviewersTechNotInInterview(interviewDateId);
        List<Map<String,Object>> checkedIntersHr = iDateJdbc.getInterviewersHrById(interviewDateId);
        List<Map<String,Object>> checkedIntersTech = iDateJdbc.getInterviewersTechById(interviewDateId);
        int typeInt=0;
        if (typeInterview.equals("HR")){
             typeInt=1;
        }
        else {
             typeInt=2;
        }
        List<Map<String,Object>> dates = iDateJdbc.getInterviewDateById(interviewDateId);
        mav.addObject("dates",dates );
        mav.addObject("uncheckedIntersHr",uncheckedIntersHr); 
        mav.addObject("uncheckedIntersTech",uncheckedIntersTech); 
        mav.addObject("checkedIntersHr", checkedIntersHr);
        mav.addObject("checkedIntersTech", checkedIntersTech);
        mav.addObject("typeInt",typeInt);
        mav.setViewName("hr/editInterviewDate");
        }
        catch(Exception e){}
        return mav;
    }
   /**
    * 
    * @param interviewDateId
    * @param dateInter
    * @param timeInter
    * @param interviewDuration
    * @param interviewers
    * @return 
    */

    @RequestMapping(value="hr/doneDate",method= RequestMethod.POST)
    public ModelAndView doneDate(@RequestParam("interviewDateId") int interviewDateId,
    @RequestParam("dateInter") String dateInter,
    @RequestParam("type") int typeInt,
    @RequestParam("timeInter") String timeInter,
    @RequestParam("interviewDuration") int interviewDuration,
    @RequestParam("checkInterviewers[]") int[] interviewers){
       
        ModelAndView mav = new ModelAndView();
         try{
        String[] str_time=timeInter.split(" - ");
        iDateJdbc.deleteFromInterviewerList(interviewDateId);
        iDateJdbc.updateInterviewDateByDateId(interviewDateId, dateInter+" "+str_time[0], dateInter+" "+str_time[1], interviewDuration);
        for (int i=0;i<interviewers.length;i++){
            if (typeInt==1){
                iDateJdbc.insertInterviewersAndDates(interviewers[i],interviewDateId,"HR");
            }
            else{
                iDateJdbc.insertInterviewersAndDates(interviewers[i],interviewDateId,"Tech");
            }
         }}
         catch(Exception e){}
        return mav;
    }
    
    @RequestMapping(value="hr/delInterviewDate", method= RequestMethod.GET)
    public ModelAndView delDate(@RequestParam("interviewDateId") int interviewDateId){
        ModelAndView mav = new ModelAndView();
        List <InterviewDate> Model=iDateJdbc.getInterviewDateListById(interviewDateId);
        int delInterview = iDateJdbc.getCountInterview(interviewDateId);
        int delInterviewResults = iDateJdbc.getCountInterviewResults(interviewDateId);
        mav.addObject("delInterview", delInterview);
        mav.addObject("delInterviewResults", delInterviewResults);
        mav.addObject("Model", Model);
        mav.setViewName("/hr/delInterviewDate");
        return mav;
    }

    /**
    * delete interview date
    * @param dateId - number of date
    */
    @RequestMapping(value="hr/deletedInterviewDate", method= RequestMethod.POST)
    public ModelAndView deletedDate(@RequestParam("interviewDateId") int interviewDateId){
        ModelAndView mav = new ModelAndView();
        iDateJdbc.deleteInterviewDateByAppId(interviewDateId);
        mav.addObject("msg", "Запись успешно удалена!");
        List<InterviewDate> id=iDateJdbc.getAllInterviewDatesWithInterviewers();  
        List<Map<String,Object>> inters=iDateJdbc.getInterviewersHr();
        mav.setViewName("hr/interviewDate");
        mav.addObject("Model",id);
        mav.addObject("Inters",inters);
        return mav;
    } 

    
}
