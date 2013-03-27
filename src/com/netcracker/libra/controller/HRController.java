
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
     
     /*
      * Display faculties of iniversity
      */
    @RequestMapping(value="hr/faculty", method= RequestMethod.POST)
     public ModelAndView myTest(@RequestParam("universityId") int universityId){
        List<Faculty> fact=hr.getAllFaculties(universityId);
            return new ModelAndView("hr/faculty","fact",fact);
        }
    /*
     * Display all universitties
     */
    @RequestMapping(value="hr/university")
     public ModelAndView myUn(){
        List<University> univers=hr.getAllUniversity();
        return new ModelAndView("hr/university","univers",univers);
        }
    
    @Deprecated
    @RequestMapping(value="/hr/testNav")
    public ModelAndView myTestNav(){
        List<Student> std= hr.listStudents();
        ModelAndView mav= new ModelAndView();
        mav.addObject("Model", std);
        return mav;
    }
    /*
     * Display departments of faculty
     */
    @RequestMapping(value="hr/department", method= RequestMethod.POST)
     public ModelAndView myDept(@RequestParam("facultyId") int facultyId){
            List<Department> departments=hr.getAllDepartments("f.facultyId", facultyId);
            return new ModelAndView("hr/department","dept",departments);
        }
    /*
     * Display all students
     */
    @RequestMapping("/hr/showStudentbyIdView")
    public ModelAndView showStudentbyId(){
        List<Student> std=hr.listStudents();
        ModelAndView mav = new ModelAndView();
        mav.addObject("Model",std);
        mav.setViewName("hr/showStudentbyIdView");
        return mav;
      }

     /* Find students by ID, email, firstname, lastname, department, faculty or university
     * @param textBox - key phrase
     * @param filter - search criteria
     * @return list of Students
     */
      @RequestMapping(value="/hr/showStudentbyIdView", method= RequestMethod.POST)
      public ModelAndView showStudentByIdView(
              @RequestParam("filter") int filter,
          org.springframework.web.context.request.WebRequest webRequest){ 
          ModelAndView mav = new ModelAndView();
          String textBox=webRequest.getParameter("textBox");
          String univerId=webRequest.getParameter("univ");
          String facultyId=webRequest.getParameter("fact");
          String departmentId=webRequest.getParameter("dept");
          mav.setViewName("hr/showStudentbyIdView");
          mav.addObject("textBox", textBox);
          mav.addObject("filterInt", filter);
          List<Student> std=null;
          if ((filter==1) && (univerId.equals("0"))){
              std=hr.listStudents();
              mav.addObject("Model",std);
              return mav;
          }
          if (textBox.equals("") && (filter!=1) && (univerId.equals("0"))){
              mav.addObject("errorMessage", "Введите значение для поиска");
              std=hr.listStudents();
              mav.addObject("Model",std);
              return mav;
          }
          if (!textBox.equals("") && (filter!=1) && (!univerId.equals("0"))){
            String education="department";
            int eduValue=0;
            if (facultyId.equals("0")){
                education="university";
                eduValue=Integer.parseInt(univerId);
                    mav.addObject("selectedUniv",univerId);
            }
            else {
                if(departmentId.equals("0")){
                    education="faculty";
                    eduValue=Integer.parseInt(facultyId);
                    mav.addObject("selectedFact",facultyId);
                    mav.addObject("selectedUniv",univerId);
                }
                else {
                    eduValue=Integer.parseInt(departmentId);
                    mav.addObject("selectedDept",departmentId);
                    mav.addObject("selectedFact",facultyId);
                    mav.addObject("selectedUniv",univerId);
                }
            }
            if (filter==2){
                try{
                    int i=Integer.parseInt(textBox);
                    std=hr.getStudent(education, eduValue, i);
                    }
                catch(Exception ex){
                    mav.addObject("errorMessage","Введенные данные некорректны!");
                    }
            }
            if (filter==3){
                   std =hr.getStudentsByFirstName(education, eduValue,textBox);
                    }
            if (filter==4){
                   std =hr.getStudentsByLastName(education, eduValue,textBox);
                    }
            if (filter==5){
                   std =hr.getStudentsByEmail(education, eduValue,textBox);
                    }
            if (filter == 6){
                std = hr.getStudentsByAllFields(education, eduValue,textBox);
            }
          }
          if ((textBox.equals("") && (!univerId.equals("0")))|| ((filter==1) && (!univerId.equals("")))){
              if (facultyId.equals("0")){
                  int universityId=Integer.parseInt(univerId);
                  std=hr.getStudentByUniversity(universityId);
                  mav.addObject("selectedUniv",universityId);
              }
                  else{    
                    if (departmentId.equals("0")){
                        int facultId=Integer.parseInt(facultyId);
                        std=hr.getStudentByFaculty(facultId);
                        mav.addObject("selectedFact",facultId);
                        mav.addObject("selectedUniv",univerId);
                    }
                    else {
                    int departId=Integer.parseInt(departmentId);
                    std=hr.getStudentByDepartment(departId);
                    mav.addObject("selectedDept",departId);
                    mav.addObject("selectedFact",facultyId);
                    mav.addObject("selectedUniv",univerId);
                    }
                }
             }
            if (!textBox.equals("") && (filter!=1) && (univerId.equals("0"))){
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
           
          } 
          
          catch(Exception ex){
            mav.addObject("errorMessage","Введенные данные некорректны!");   
            }
          }
          mav.addObject("Model",std);
          if (std.isEmpty())
              mav.addObject("errorMessage", "Студенты не найдены!");
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
      @RequestMapping("/hr/сonfirmEditing")
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

