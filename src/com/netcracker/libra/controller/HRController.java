
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.HrJDBC;
import com.netcracker.libra.model.DateAndInterviewer;
import com.netcracker.libra.model.DateAndInterviewerResults;
import com.netcracker.libra.model.Department;
import com.netcracker.libra.model.Faculty;
import com.netcracker.libra.model.OldNewInterviewTime;
import com.netcracker.libra.model.Student;
import com.netcracker.libra.model.University;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Yuliya
 */
@Controller
public class HRController {
           
     HrJDBC hr=new HrJDBC();
     Student s=new Student();
     
     //list contain general info about the student, date and time of his/her interview
     //(that were before student's wishes to change and after it)
     List <OldNewInterviewTime> oldNewInterviewTimeList; 
     boolean order; //value of ascending or descending order; true when ascending
     
    @RequestMapping(value="hr/faculty", method= RequestMethod.POST)
     public ModelAndView myTest(@RequestParam("universityId") int universityId){
        List<Faculty> fact=hr.getAllFaculties(universityId);
            return new ModelAndView("hr/faculty","fact",fact);
        }
     
    @RequestMapping(value="hr/department", method= RequestMethod.POST)
     public ModelAndView myDept(@RequestParam("facultyId") int facultyId){
            List<Department> departments=hr.getAllDepartments("f.facultyId", facultyId);
            return new ModelAndView("hr/department","dept",departments);
        }
    
    @RequestMapping("/hr/showStudentbyIdView")
    public ModelAndView showStudentbyId(){
        List<Student> std=hr.listStudents();
        ModelAndView mav = new ModelAndView();
        List<University> universities;
        universities= hr.getAllUniversity();
        mav.addObject("Model",std);
        mav.addObject("univers",universities);
        mav.addObject("Model", std);
        mav.addObject("direction","asc");
        mav.addObject("orderBy","appId");
        mav.setViewName("hr/showStudentbyIdView");
        return mav;
      }
  
    /*
     * Show all students of All Universities
     */
   @RequestMapping(value = "/hr/showStudentByEducation")
    public ModelAndView showAllStudent(){
        ModelAndView mav = new ModelAndView();
        List<Student> std=hr.listStudents();
        List<University> universities;
        universities= hr.getAllUniversity();
        mav.addObject("Model",std);
        mav.addObject("univers",universities);
        mav.setViewName("hr/showStudentByEducation");
        return mav;
    }
   
   /**
    * Find students by University, Faculty or Department
    * @param universityId
    * @param facultyId
    * @param departmentId
    * @return list os Students and list of University
    */
   
    @RequestMapping(value="/hr/showStudentByEducation", method=RequestMethod.POST)
    public ModelAndView showStudentsByEd(@RequestParam("univ") int universityId,
    @RequestParam("fact") int facultyId,
    @RequestParam("dept") int departmentId){
        ModelAndView mav = new ModelAndView();
        List<University> universities= hr.getAllUniversity();
        mav.addObject("univers",universities);
        List<Student> std=null;
        if (facultyId==0){
           std = hr.getStudentByUniversity(universityId);
        }
        else 
            if (departmentId==0){
                std = hr.getStudentByFaculty(facultyId);
            }
            else{ 
                std = hr.getStudentByDepartment(departmentId);
            }
        mav.addObject("Model",std);
        mav.setViewName("hr/showStudentByEducation");
        return mav;
       
        }   
    
    /**
     * Find students by ID, email, firstname or lastname
     * @param textBox - key phrase
     * @param filter - search criteria
     * @return list of Students
     */
      @RequestMapping(value="/hr/showStudentbyIdView", method= RequestMethod.POST)
      public ModelAndView showStudentByIdView(@RequestParam("textBox") String textBox, 
      @RequestParam("filter") int filter){
          ModelAndView mav = new ModelAndView();
          mav.setViewName("hr/showStudentbyIdView");
          mav.addObject("textBox", textBox);
          mav.addObject("direction","asc");
          mav.addObject("orderBy","appId");
          mav.addObject("filterInt", filter);
          if (textBox.equals("") && (filter!=1)){
              mav.addObject("msg", "Input data for search!");
              return mav;
          }
          List<Student> std=null;
          try{
            if (filter==1){
                    std=hr.listStudents();              
                    }
            if (filter==2){
                    int i=Integer.parseInt(textBox);
                    std=hr.getStudent(i);
                    } 
            if (filter==3){
                   std =hr.getStudentsByFirstName(textBox);
                    }
            if (filter==4){
                   std =hr.getStudentsByLastName(textBox);
                    }
            if (filter==5){
                   std =hr.getStudentsByEmail(textBox);
                    }
            if (filter==6){
                std = hr.getStudentsByAllFields(textBox);
            }
            mav.addObject("Model",std);
          }
          catch(Exception ex){
            mav.addObject("msg","Input data is not in correct format! Try again!");   
         }
       /*   if (std.isEmpty()){
              mav.addObject("msg1", "Студенты не найдены.");
          }*/
          return mav;
    }
      

      
      @RequestMapping(value = "/hr/sortedByEducation", method = RequestMethod.GET)
      public ModelAndView sortedbyEdu( 
      org.springframework.web.context.request.WebRequest webRequest
       ){
          String orderBy = webRequest.getParameter("orderBy");
          String direction = webRequest.getParameter("direction");
          String universityId = webRequest.getParameter("universityId");
          String facultyId = webRequest.getParameter("facultyId");
          String departmentId = webRequest.getParameter("departmentId");
          ModelAndView mav = new ModelAndView();
          List<Student> std=null;
          if (universityId.equals("")){
              std = hr.getOrderStudent("getAll", "bla", orderBy);
          }
          else{ 
          if ((!universityId.equals("0")) && (facultyId.equals("0"))){
               std = hr.getOrderedStudentByEdu("universityId", universityId, orderBy);
            } else {
              if ((!facultyId.equals("0")) && (departmentId.equals("0"))){
                  std = hr.getOrderStudent("facultyId", facultyId, orderBy);
              }
              else {
                  if (!departmentId.equals("0")){
                      std = hr.getOrderStudent("departmentId", departmentId, orderBy);
                  }
                  else {
                      std = hr.getOrderStudent("getAll", "bla", orderBy);
                  }
              }        
          }
          }
          mav.addObject("Model", std);
          mav.setViewName("hr/showStudentByEducation");
          return mav;
      }
      @RequestMapping(value="hr/showLanguages")
      public ModelAndView showAllLangs(){
          List<Map<String,Object>> langs=hr.getAllLanguages();
          ModelAndView mav = new ModelAndView();
          mav.addObject("languages", langs);
          mav.setViewName("hr/showLanguages");
          return mav;
      }
      @RequestMapping(value="/hr/delLanguage", method=RequestMethod.GET)
      public ModelAndView delLang(@RequestParam("languageId") int languageId){
          List<Map<String,Object>> langs = hr.showLangById(languageId);
          ModelAndView mav = new ModelAndView();
          mav.addObject("languages", langs);
          return mav;
    }
      @RequestMapping(value="/hr/deletedLang", method=RequestMethod.POST)
      public ModelAndView deletedLang(@RequestParam("languageId") int languageId){
          hr.deleteLanguage(languageId);
          ModelAndView mav = new ModelAndView();
          List<Map<String,Object>> langs = hr.getAllLanguages();
          mav.addObject("languages", langs);
          mav.addObject("msg", "Language successfully deleted!");
          mav.setViewName("/hr/showLanguages");
          return mav;
    }
      
      @RequestMapping(value="/hr/addLanguage", method= RequestMethod.GET)
      public ModelAndView addLang(
      org.springframework.web.context.request.WebRequest webRequest){
        String langSearch = webRequest.getParameter("langSearch");
        String textbox = webRequest.getParameter("textBox");
        ModelAndView mav = new ModelAndView();
        List<Map<String,Object>> langs=null;
        try{
            int langSearchInt=Integer.parseInt(langSearch);
            mav.addObject("textBox", textbox);
            mav.addObject("langSearchInt", langSearch);
            if (langSearchInt==0){
                langs = hr.getAllLanguages();
            }
            if (langSearchInt==1){
            try{
            int n=Integer.parseInt(textbox);
             langs = hr.showLangById(n);
            }
            catch(Exception e){
                mav.addObject("msg", "№ language has not correct format!");
                langs = hr.getAllLanguages();
                    }
                }
            if (langSearchInt==2){
            langs = hr.showLangByName(textbox);
            }
        }
        catch(Exception ex){
            langs = hr.getAllLanguages();
        }
        finally{
            mav.addObject("languages", langs);
            mav.setViewName("/hr/addLanguage");
            return mav;
    }
    }
      @RequestMapping(value="hr/addLanguageAdded", method= RequestMethod.GET)
      public ModelAndView addedLang(
      @RequestParam("langName") String name){
        ModelAndView mav = new ModelAndView();
        if (name.equals("")) {
            mav.addObject("msg", "Language name is not correct! Try again");
            mav.setViewName("/hr/addLanguage");
            List<Map<String,Object>> langs = hr.getAllLanguages();
            mav.addObject("languages", langs);
            return mav;
        }
        hr.createLanguage(name);
        List<Map<String,Object>> langs = hr.getAllLanguages();
        mav.addObject("languages", langs);
        mav.addObject("msg", "Language successfully added!");
        mav.setViewName("/hr/showLanguages");
        return mav;
    }
      
      @RequestMapping(value="/hr/deletedLanguage", method=RequestMethod.POST)
      public ModelAndView deletedUnivers(@RequestParam("languageId") int languageId){
          hr.deleteLanguage(languageId);
          ModelAndView mav = new ModelAndView();
          List<Map<String,Object>> langs = hr.getAllLanguages();
          mav.addObject("languages", langs);
          mav.addObject("msg", "Language successfully deleted!");
          mav.setViewName("/hr/showLanguages");
          return mav;
    }
    
      @RequestMapping(value="hr/editLanguage", method = RequestMethod.GET)
      public ModelAndView editLang(@RequestParam("languageId") int languageId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("languages", hr.showLangById(languageId));
        return mav;
    }
      @RequestMapping(value="hr/editedLang", method = RequestMethod.POST)
      public ModelAndView editedLang(@RequestParam("languageId") int languageId,
      @RequestParam("languageName") String languageName){
          ModelAndView mav = new ModelAndView();
            try{
                if (languageName.equals("")){
                    mav.addObject("languages", hr.showLangById(languageId));
                    mav.addObject("msg", "Editing failed, language name has uncorrect format");
                    mav.setViewName("/hr/editLanguage"); 
                    return mav;
                    }
                hr.updateLanguage(languageId, languageName);
                List<Map<String,Object>> langs = hr.getAllLanguages();
                mav.addObject("languages", langs);
                mav.addObject("msg", "Language successfully edited!");
                mav.setViewName("hr/showLanguages");
                return mav;
                    }
                catch(Exception ex){
                    mav.addObject("msg", "Editing failed");
                    mav.setViewName("/hr/editLanguage"); 
                    return mav;
                }
        }
      
      @RequestMapping(value="hr/showLanguagesSearch", method= RequestMethod.GET)
      public ModelAndView searchUnivs(
      @RequestParam("textBox") String name, 
      @RequestParam("langSearch") int langSearchInt){
        ModelAndView mav = new ModelAndView();
        mav.addObject("textBoxString", name);
        mav.addObject("langSearchInt", langSearchInt);
        List<Map<String,Object>> langs=null;
        if (langSearchInt==1){
            try{
            int n=Integer.parseInt(name);
            langs = hr.showLangById(n);
            }
            catch(Exception e){
                mav.addObject("msg", "№ language has not correct format!");
                langs = hr.getAllLanguages();
            }
        }
        if (langSearchInt==2){
            langs = hr.showLangByName(name);
        }
        if(langSearchInt==0){
            langs = hr.getAllLanguages();
        }
        mav.addObject("languages", langs);
        return mav;
    }
      
      @RequestMapping(value = "/hr/sortedBy", method = RequestMethod.GET)
      public ModelAndView sortedby( 
      org.springframework.web.context.request.WebRequest webRequest
       ){
          String orderBy = webRequest.getParameter("orderBy");
          String textBox = webRequest.getParameter("textBox");
          String filter = webRequest.getParameter("filter");
          ModelAndView mav = new ModelAndView();
          List<Student> std=null;
          switch(filter){
              case("2"): 
                  std = hr.getOrderStudent("appId", textBox, orderBy);
                            break;
              case("3"): std = hr.getOrderStudent("firstName", textBox, orderBy);
                            break;
              case("4"): std = hr.getOrderStudent("lastName", textBox, orderBy);
                            break;
              case("5"): std = hr.getOrderStudent("email", textBox, orderBy);
                            break;
              case("6"): std = hr.getOrderStudent("allFields", textBox, orderBy);
                            break;
              default: std = hr.getOrderStudent("getAll", textBox, orderBy);   
                            break;
            } 
          mav.addObject("textBox", textBox);
          mav.addObject("filterInt", filter);
          mav.addObject("Model", std);
          mav.setViewName("hr/showStudentbyIdView");
          return mav;
      }
     
      /**
       * Displays information about the student's interview.
       * @author Alexander Lebed
       */
      @RequestMapping("hr/showStudentInterview")
      public ModelAndView showStudentInterview(
                            @RequestParam("appId") int appId,
                            @RequestParam("firstName") String firstName,
                            @RequestParam("lastName") String lastName,
                            @RequestParam("view") int view) {
          
          //these values will be passed to the results page
          String notAssigned = "";
          String wasAbsent = "";
          List dateAndInterviewerList = new ArrayList();
          List dateAndInterviewerResultsList = new ArrayList();
          
          List <Integer> interviewIds = hr.getInterviewIds(appId);
          
          if(interviewIds.isEmpty()) {
              //if no any information about the interview, displayed the message
              notAssigned = "Студент "+ firstName +" "+ lastName +" не записался на интервью";
          }
          
          for(Integer id : interviewIds) {
              
              int interviewId = id;
              //string of interview's finish date and time
              String interviewDateFinish = hr.getInterviewFinishDate(interviewId);
              boolean wasInterviewed = hr.getInterviewResults(interviewId);
              
              if(actualInterview(interviewDateFinish)) {
                  //else if the student will be interviewed, diplayed application's form ID, 
                  //interview's date and time, assigned interviewers
                  List <DateAndInterviewer> resultList = hr.getDateAndInterviewer(interviewId);
                  dateAndInterviewerList.addAll(resultList);
              }
              else if(wasInterviewed) {
                  //else if the student was interviewed, displayed the application's form ID, date and time of the interview, 
                  //assigned interviewers, results of the interview (marks, comments)
                  List <DateAndInterviewerResults> resultList = hr.getDateAndInterviewerResults(interviewId);
                  dateAndInterviewerResultsList.addAll(resultList);
              }
              else {
                  //else if the interview have been assigned but the student didn't come, displayed the corresponding message
                  wasAbsent += "Студент "+ firstName +" "+ lastName +" не явился на интервью <br>";
              }
          }

          ModelAndView mv = new ModelAndView();
          mv.setViewName("hr/showStudentInterviews");
          mv.addObject("view", view); // 0 if redirected from showStudentbyIdView.jsp, 1 - from showStudentByEducation.jsp
          mv.addObject("notAssigned", notAssigned);
          mv.addObject("dateAndInterviewerList", dateAndInterviewerList);
          mv.addObject("dateAndInterviewerResultsList", dateAndInterviewerResultsList);
          mv.addObject("wasAbsent", wasAbsent);
          return mv;
      }

      /**
       * @return true if the interview will be; false - if it was
       */
      public boolean actualInterview(String interviewDateFinish) {
          SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
          Date sqlDate = null;
          try {
             //forms the date based on the date-string and date-format
             sqlDate = df.parse(interviewDateFinish);
          } catch (ParseException ex) {
              Logger.getLogger(HRController.class.getName()).log(Level.SEVERE, null, ex);
          }
          Date current = new Date();
          return current.before(sqlDate);
      }
      
      
      /**
       * Go to page with general info about the student, date and time of his/her interview
       * (that were before student's wishes to change and after it) 
       */
      @RequestMapping("hr/сonfirmEditing")
      public ModelAndView showConfirmEditing() {
          ModelAndView mv = new ModelAndView();
          
          oldNewInterviewTimeList = hr.getAllOldNewInterviewTime();
          
          mv.setViewName("hr/showConfirmEditing");
          mv.addObject("timeList", oldNewInterviewTimeList);
          return mv;
      }
      
      /**
       * Sort in ascending or descending order 
       * by the app.form ID, first name, last name, email and field name
       */
      @RequestMapping("hr/sortOldNewInterview")
      public ModelAndView sortByLink(@RequestParam("orderBy") String orderBy) {
          
          String up = "<img  src=\"../resources/images/admin/arrow_down.png\" width=\"12\" height=\"12\" title=\"по возрастанию\"/>";
          String down = "<img  src=\"../resources/images/admin/arrow_up.png\" width=\"12\" height=\"12\" title=\"по убыванию\"/>";
          
           ModelAndView mv = new ModelAndView();
           mv.setViewName("hr/showConfirmEditing");
           
           switch(orderBy) {
               
               case "APP_ID":
                    Collections.sort(oldNewInterviewTimeList, new IdComparator(order));
                    String idOrder = (order) ? up : down;
                    mv.addObject("idOrder", idOrder);
                    break;
                   
               case "FIRST_NAME":
                    Collections.sort(oldNewInterviewTimeList, new FirstNameComparator(order));
                    String nameOrder = (order) ? up : down;
                    mv.addObject("nameOrder", nameOrder);
                    break;
                   
               case "LAST_NAME":
                    Collections.sort(oldNewInterviewTimeList, new LastNameComparator(order));
                    nameOrder = (order) ? up : down;
                    mv.addObject("nameOrder", nameOrder);
                    break;
                
               case "EMAIL":
                    Collections.sort(oldNewInterviewTimeList, new EmailComparator(order));
                    String emailOrder = (order) ? up : down;
                    mv.addObject("emailOrder", emailOrder);
                    break;
                   
               case "FIELD_NAME":
                    Collections.sort(oldNewInterviewTimeList, new FieldNameComparator(order));
                    String fieldNameOrder = (order) ? up : down;
                    mv.addObject("fieldNameOrder", fieldNameOrder);
                    break;
           }
           //switch to ascending or descending order
           order = (order) ? false : true;
           mv.addObject("timeList", oldNewInterviewTimeList);
           return mv;
      }
      
      /**
       * Cancel the changes with new date and time of interview
       */
      @RequestMapping("hr/cancelConfirmEditingInterviewTime")
      public ModelAndView cancelConfirmEditingInterviewTime(@RequestParam("newInterviewId") int newInterviewId) {
          
          hr.deleteInterview(newInterviewId);
          
          for(ListIterator <OldNewInterviewTime> i = oldNewInterviewTimeList.listIterator(); i.hasNext(); ) {
              OldNewInterviewTime obj = i.next();
              if(obj.getNewInterviewId() == newInterviewId) {
                  i.remove();
              }
          }
          
          ModelAndView mv = new ModelAndView();
          mv.setViewName("hr/showConfirmEditing");
          mv.addObject("message", "Изменение успешно отменено");
          mv.addObject("timeList", oldNewInterviewTimeList);
          return mv;
      }
      
      /**
       * Change interview to new date and time
       */
      @RequestMapping("hr/doneConfirmEditingInterviewTime")
      public ModelAndView doneConfirmEditingInterviewTime(@RequestParam("oldInterviewId") int oldInterviewId,
                                                          @RequestParam("newInterviewId") int newInterviewId) {
          hr.deleteInterview(oldInterviewId);
          hr.congirmInterview(newInterviewId);
          
          for(ListIterator <OldNewInterviewTime> i = oldNewInterviewTimeList.listIterator(); i.hasNext(); ) {
              OldNewInterviewTime obj = i.next();
              if(obj.getOldInterviewId() == oldInterviewId) {
                  i.remove();
              }
          }
          
          ModelAndView mv = new ModelAndView();
          mv.setViewName("hr/showConfirmEditing");
          mv.addObject("message", "Изменения сохранены");
          mv.addObject("timeList", oldNewInterviewTimeList);
          return mv;
      }
      
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the ID
     */
    public static class IdComparator implements Comparator <OldNewInterviewTime> {
        private boolean asc; // true - sorts in ascending order, fasle - descending
        public IdComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(OldNewInterviewTime obj1, OldNewInterviewTime obj2) {
            if(asc) {
                return (obj1.getAppId() > obj2.getAppId()) ? 1 
                    : (obj1.getAppId() == obj2.getAppId()) ? 0 : -1;
            }
            else {
                return (obj2.getAppId() > obj1.getAppId()) ? 1 
                    : (obj2.getAppId() == obj1.getAppId()) ? 0 : -1;
            }
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the first name
     */
    public static class FirstNameComparator implements Comparator <OldNewInterviewTime> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public FirstNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(OldNewInterviewTime o1, OldNewInterviewTime o2) {
            return asc ? (o1.getFirstName().compareToIgnoreCase(o2.getFirstName())) 
                       : (o2.getFirstName().compareToIgnoreCase(o1.getFirstName()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the last name
     */
    public static class LastNameComparator implements Comparator <OldNewInterviewTime> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public LastNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(OldNewInterviewTime o1, OldNewInterviewTime o2) {
            return asc ? (o1.getLastName().compareToIgnoreCase(o2.getLastName())) 
                       : (o2.getLastName().compareToIgnoreCase(o1.getLastName()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the email
     */
    public static class EmailComparator implements Comparator <OldNewInterviewTime> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public EmailComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(OldNewInterviewTime o1, OldNewInterviewTime o2) {
            return asc ? (o1.getEmail().compareToIgnoreCase(o2.getEmail())) 
                       : (o2.getEmail().compareToIgnoreCase(o1.getEmail()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the email
     */
    public static class FieldNameComparator implements Comparator <OldNewInterviewTime> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public FieldNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(OldNewInterviewTime o1, OldNewInterviewTime o2) {
            return asc ? (o1.getFieldName().compareToIgnoreCase(o2.getFieldName())) 
                       : (o2.getFieldName().compareToIgnoreCase(o1.getFieldName()));
        }
    }

}

