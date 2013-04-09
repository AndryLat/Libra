package com.netcracker.libra.controller;

import com.netcracker.libra.dao.HrJDBC;
import com.netcracker.libra.model.ApplicationChange;
import com.netcracker.libra.model.DateAndInterviewer;
import com.netcracker.libra.model.DateAndInterviewerResults;
import com.netcracker.libra.model.Department;
import com.netcracker.libra.model.Faculty;
import com.netcracker.libra.model.Student;
import com.netcracker.libra.model.University;
import com.netcracker.libra.util.security.SessionToken;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for HR
 * 
 * Display universities, faculties and departments
 * Display students and info about their interview
 * Display students changes in application form, in interview time and 
 * be able to apply or undo them.
 * 
 * @author Yuliya
 * @author Alexander Lebed
 */
@Controller
@RequestMapping("hr")
@SessionAttributes("LOGGEDIN_USER")
public class HRController {
           
     HrJDBC hr=new HrJDBC();
     Student s=new Student();
     
     List <ApplicationChange> appChangeList; //objects with application changes
     boolean order; //value of ascending or descending order; true when ascending
     List <ApplicationChange> checkedList; //objects with application changes (checked only)
     
    /*
      * Display faculties of iniversity
      */
    @RequestMapping(value="faculty", method= RequestMethod.POST)
     public ModelAndView myTest(@RequestParam("universityId") int universityId){
            List<Faculty> fact=hr.getAllFaculties(universityId);
            return new ModelAndView("hr/faculty","fact",fact);
        }
    /*
     * Display all universitties
     */
    @RequestMapping(value="university")
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
    @RequestMapping(value="department", method= RequestMethod.POST)
     public ModelAndView myDept(@RequestParam("facultyId") int facultyId){
            List<Department> departments=hr.getAllDepartments("f.facultyId", facultyId);
            return new ModelAndView("hr/department","dept",departments);
        }
    
    /*
     * Display all students
     */
    @RequestMapping("showStudentbyIdView")
    public ModelAndView showStudentbyId(@ModelAttribute("LOGGEDIN_USER") SessionToken token){
        if(token.getUserAccessLevel()==1) {
            List<Student> std=hr.listStudents();
            ModelAndView mav = new ModelAndView();
            mav.addObject("Model",std);
            mav.setViewName("hr/showStudentbyIdView");
            return mav;
        }
        else {
            return errorMessage();
        }
        
      }

     /* Find students by ID, email, firstname, lastname, department, faculty or university
     * @param textBox - key phrase
     * @param filter - search criteria
     * @return list of Students
     */
      @RequestMapping(value="showStudentbyIdView", method= RequestMethod.POST)
      public ModelAndView showStudentByIdView(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
          @RequestParam("filter") int filter,
          org.springframework.web.context.request.WebRequest webRequest){ 
          if(token.getUserAccessLevel()==1) {
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
          else {
            return errorMessage();
          }
          
          
    }
     
      /**
       * Displays information about the student's interview.
       * @author Alexander Lebed
       */
      @RequestMapping("showStudentInterview")
      public ModelAndView showStudentInterview(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                @RequestParam("appId") int appId,
                                                @RequestParam("firstName") String firstName,
                                                @RequestParam("lastName") String lastName,
                                                @RequestParam("view") int view) {
          
          if(token.getUserAccessLevel()==1) {
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
          else {
            return errorMessage();
          }
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
       * Go to page with general info about the student and his changes (old & new value)
       * in application form
       * and changes in interview time
       */
      @RequestMapping("confirmChanges")
      public ModelAndView showConfirmEditing(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
          
          if(token.getUserAccessLevel()==1) {
            ModelAndView mv = new ModelAndView();
            
            appChangeList = getOldNewList();
            Collections.sort(appChangeList, new IdComparator(true));

            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("list", appChangeList);
            mv.addObject("idOrder", "<img  src=\"../resources/images/admin/arrow_down.png\" width=\"12\" height=\"12\" title=\"по убыванию\"/>");
            return mv;
          }
          else {
            return errorMessage();
        }
      }
      
      @RequestMapping("currentConfirmChanges")
      public ModelAndView showcurrentConfirmChanges(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
          if(token.getUserAccessLevel()==1) {
                ModelAndView mv = new ModelAndView();
                mv.setViewName("hr/showApplicationChanges");
                mv.addObject("list", appChangeList);
                return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      /**
       * Sort in ascending or descending order the table of old and new date and time
       * by the app.form ID, first name, last name, email and field name
       */
      @RequestMapping("sortOldNewValues")
      public ModelAndView sortByLink(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                     @RequestParam("orderBy") String orderBy) {
          
          if(token.getUserAccessLevel()==1) {
            //arrows uo and down when sorting
            String up = "<img  src=\"../resources/images/admin/arrow_down.png\" width=\"12\" height=\"12\" title=\"по возрастанию\"/>";
            String down = "<img  src=\"../resources/images/admin/arrow_up.png\" width=\"12\" height=\"12\" title=\"по убыванию\"/>";

             ModelAndView mv = new ModelAndView();
             mv.setViewName("hr/showApplicationChanges");

             switch(orderBy) {

                 case "APP_ID":
                      Collections.sort(appChangeList, new IdComparator(order));
                      String idOrder = (order) ? up : down;
                      mv.addObject("idOrder", idOrder);
                      break;

                 case "FIRST_NAME":
                      Collections.sort(appChangeList, new FirstNameComparator(order));
                      String nameOrder = (order) ? up : down;
                      mv.addObject("nameOrder", nameOrder);
                      break;

                 case "LAST_NAME":
                      Collections.sort(appChangeList, new LastNameComparator(order));
                      nameOrder = (order) ? up : down;
                      mv.addObject("nameOrder", nameOrder);
                      break;

                 case "EMAIL":
                      Collections.sort(appChangeList, new EmailComparator(order));
                      String emailOrder = (order) ? up : down;
                      mv.addObject("emailOrder", emailOrder);
                      break;

                 case "FIELD_NAME":
                      Collections.sort(appChangeList, new FieldNameComparator(order));
                      String fieldNameOrder = (order) ? up : down;
                      mv.addObject("fieldNameOrder", fieldNameOrder);
                      break;
             }
             //switch to ascending or descending order
             order = (order) ? false : true;
             mv.addObject("list", appChangeList);
             return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      /**
       * apply student's changes of interview time
       */
      @RequestMapping("doneConfirmInterviewTime")
      public ModelAndView doneConfirmInterviewTime(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                   @RequestParam("oldId") int oldId,
                                                   @RequestParam("newId") int newId,
                                                   @RequestParam("objectId") int objectId) {
          if(token.getUserAccessLevel()==1) {
              
            hr.deleteInterview(oldId);
            hr.confirmInterviewTime(newId);

            for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }

            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("message", "Изменения сохранены");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      /**
       * undo student's changes of interview time
       */
      @RequestMapping("cancelConfirmInterviewTime")
      public ModelAndView cancelConfirmInterviewTime(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                     @RequestParam("newId") int newId,
                                                     @RequestParam("objectId") int objectId) {
          if(token.getUserAccessLevel()==1) {
              
            hr.deleteInterview(newId);
            
            for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }
            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("message", "Изменение отклонено");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
          
      }
      
      /**
       * apply student's changes of dynamic field in database
       */
      @RequestMapping("doneConfirmDynamicField")
      public ModelAndView doneConfirmDynamicField(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                  @RequestParam("oldId") int oldId,
                                                  @RequestParam("newId") int newId,
                                                  @RequestParam("objectId") int objectId) {
          if(token.getUserAccessLevel()==1) {
              
            hr.deleteDynamicField(oldId);
            hr.confirmDynamicField(newId);

            for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }
            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("message", "Изменения сохранены");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
          
          
      }
      
      /**
       * undo student's changes of dynamic field in database
       */
      @RequestMapping("cancelConfirmDynamicField")
      public ModelAndView cancelConfirmDynamicField(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                    @RequestParam("newId") int newId,
                                                    @RequestParam("objectId") int objectId) {
          if(token.getUserAccessLevel()==1) {
              
            hr.deleteDynamicField(newId);
          
            for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }
            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("message", "Изменение отклонено");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      
      /**
       * apply student's changes of application fields 
       * stored in AppRequest table
       */
      @RequestMapping("doneConfirmMainAppInfo")
      public ModelAndView doneConfirmMainAppInfo(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                 @RequestParam("oldId") int oldId,
                                                 @RequestParam("newId") int newId,
                                                 @RequestParam("objectId") int objectId,
                                                 @RequestParam("columnName") String columnName) {
          if(token.getUserAccessLevel()==1) {
              
            hr.confirmMainAppInfo(columnName, newId, oldId);

            for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }

            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("message", "Изменения сохранены");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      /**
       * undo student's changes of application fields 
       * stored in AppRequest table
       */
      @RequestMapping("cancelConfirmMainAppInfo")
      public ModelAndView cancelConfirmMainAppInfo(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                   @RequestParam("objectId") int objectId) {
          if(token.getUserAccessLevel()==1) {
              
            for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }

            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("message", "Изменение отклонено");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
          
          
      }
      
      /**
       * Display all marked changes of application fields and ask for make changes
       * @param action - name of pressed button
       * @param checker - checked objects
       */
      @RequestMapping("checkAllMessage")
      public ModelAndView checkAllMessage(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                          @RequestParam("action") String action,
                                          @RequestParam(value = "checker[]", required = false) int [] checker) {
          if(token.getUserAccessLevel()==1) {
              
            ModelAndView mv = new ModelAndView();
            checkedList = new ArrayList <ApplicationChange> ();

            if(checker == null) {
                mv.setViewName("hr/showApplicationChanges");
                mv.addObject("list", appChangeList);
            }
            else {
                String message = action.equals("Y") ? "Подтвердить выбранные изменения?" 
                                                    : "Отменить выбранные изменения?";

                for(int i=0; i < checker.length; i++) {
                    try {
                        for(ListIterator <ApplicationChange> j = appChangeList.listIterator(); j.hasNext(); ) {
                            ApplicationChange obj = j.next();
                            if(obj.getObjectId() == checker[i]) {
                                checkedList.add(obj);
                            }
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nexception: " + e.toString());
                    }

                }
                mv.setViewName("hr/checkAllMessage");
                mv.addObject("list", checkedList);
                mv.addObject("message" , message);
                mv.addObject("action", action);
            }
             return mv;
          }
          else {
            return errorMessage();
          }
          
      }
      
      /**
       * undo or apply all marked changes of application fields
       */
      @RequestMapping("deleteOrConfirmFewChanges")
      public ModelAndView deleteOrConfirmAllChanges(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                    @RequestParam("action") String action) {
          if(token.getUserAccessLevel()==1) {
              
            ModelAndView mv = new ModelAndView();

            boolean dynamic = false;
            boolean interview = false;
            boolean mainAppInfo = false;

            List <Integer> dynamicOldIds = null;
            List <Integer> dynamicNewIds = null;
            List <Integer> interviewOldIds = null;
            List <Integer> interviewNewIds = null;
            List <String> mainAppInfoColumnNames = null;
            List <Integer> mainAppInfoOldIds = null;
            List <Integer> mainAppInfoNewIds = null;

            for(ApplicationChange o : checkedList) {
                int oldId = o.getOldId();
                int newId = o.getNewId();
                String columnName = o.getColumnName();

                switch(columnName) {
                    case("dynamic"):
                        dynamicOldIds = new ArrayList <Integer> ();
                        dynamicNewIds = new ArrayList <Integer> ();
                        dynamicOldIds.add(oldId);
                        dynamicNewIds.add(newId);
                        dynamic = true;
                        break;
                    case("interview"):
                        interviewOldIds = new ArrayList <Integer> ();
                        interviewNewIds = new ArrayList <Integer> ();
                        interviewOldIds.add(oldId);
                        interviewNewIds.add(newId);
                        interview = true;
                        break;
                    default:
                        mainAppInfoColumnNames = new ArrayList <String> ();
                        mainAppInfoOldIds = new ArrayList <Integer> ();
                        mainAppInfoNewIds = new ArrayList <Integer> ();
                        mainAppInfoColumnNames.add(columnName);
                        mainAppInfoOldIds.add(oldId);
                        mainAppInfoNewIds.add(newId);
                        mainAppInfo = true;
                        break;
                }

                for(ListIterator <ApplicationChange> i = appChangeList.listIterator(); i.hasNext(); ) {
                    ApplicationChange obj = i.next();
                    if(obj.getObjectId() == o.getObjectId()) {
                        i.remove();
                    }
                }
            }

            if(action.equals("Y")) {

                if(dynamic) {
                    hr.deleteDynamicField(dynamicOldIds);
                    hr.confirmDynamicField(dynamicNewIds);
                }
                if(interview) {
                    hr.deleteInterview(interviewOldIds);
                    hr.confirmInterviewTime(interviewNewIds);
                }
                if(mainAppInfo) {
                    for(int i=0; i < mainAppInfoColumnNames.size(); i++) {
                        String columnName = mainAppInfoColumnNames.get(i);
                        int oldId = mainAppInfoOldIds.get(i);
                        int newId = mainAppInfoNewIds.get(i);
                        hr.confirmMainAppInfo(columnName, newId, oldId);
                    }
                }
                mv.addObject("message", "Изменения сохранены");
            }
            else if(action.equals("N")) {

                if(dynamic) {
                    hr.deleteDynamicField(dynamicNewIds);
                }
                if(interview) {
                    hr.deleteInterview(interviewNewIds);
                }
                mv.addObject("message", "Изменения отклонены");
            }

            mv.setViewName("hr/showApplicationChanges");
            mv.addObject("list", appChangeList);
            return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      /**
       * Asking for action
       */
      @RequestMapping("message")
      public ModelAndView showMessage(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                      @RequestParam("objectId") int objId, 
                                      @RequestParam("action") String action) {
          
          if(token.getUserAccessLevel()==1) {
              
            ModelAndView mv = new ModelAndView();
          
            for(ListIterator <ApplicationChange> j = appChangeList.listIterator(); j.hasNext(); ) {
                ApplicationChange obj = j.next();
                if(obj.getObjectId() == objId) {
                    mv.addObject("o", obj);
                }
            }
            String message = action.equals("confirm") ? "Подтвердить изменение?" : "Отклонить изменение?";
            mv.addObject("message", message);
            mv.setViewName("hr/message");
            mv.addObject("action", action);
            return mv;
          }
          else {
            return errorMessage();
          }
          
          
      }
      
      /**
       * Delete from confirm or undo list
       */
      @RequestMapping("deleteFromAppChangesList")
      public ModelAndView deleteFromAppChangesList(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                    @RequestParam("objectId") int objectId,
                                                    @RequestParam("action") String action) {
          if(token.getUserAccessLevel()==1) {
              
            for(ListIterator <ApplicationChange> i = checkedList.listIterator(); i.hasNext(); ) {
                ApplicationChange obj = i.next();
                if(obj.getObjectId() == objectId) {
                    i.remove();
                }
            }
            
            String message = action.equals("Y") ? "Подтвердить выбранные изменения?" : "Отменить выбранные изменения?";

            ModelAndView mv = new ModelAndView();
            mv.setViewName("hr/checkAllMessage");
            mv.addObject("list", checkedList);
            mv.addObject("message", message);
            mv.addObject("action", action);
            return mv;
          }
          else {
            return errorMessage();
          }
      }
      
      /**
       * returns all fields with the changes
       */
      public List <ApplicationChange> getOldNewList() {
          List <ApplicationChange> appChangeList = new ArrayList <ApplicationChange> ();
          List <ApplicationChange> interviewTimeList = hr.getAllOldNewInterviewTime();
          List <ApplicationChange> dynamicList = hr.getAllOldNewDynamicFields();
          List <ApplicationChange> patronymicList = hr.getOldNewPatronymics();
          List <ApplicationChange> phoneNumbersList = hr.getOldNewPhoneNumbers();
          List <ApplicationChange> educationList = hr.getOldNewEducation();
          List <ApplicationChange> advertisingList = hr.getOldNewAdvertising();
          List <ApplicationChange> coursesList = hr.getOldNewCourses();
          List <ApplicationChange> graduatedYearsList = hr.getOldNewGraduatedYears();
          List <ApplicationChange> firstNamesList = hr.getOldNewFirstNames();
          List <ApplicationChange> lastNamesList = hr.getOldNewLastNames();
          
          appChangeList.addAll(interviewTimeList);
          appChangeList.addAll(dynamicList);
          appChangeList.addAll(patronymicList);
          appChangeList.addAll(phoneNumbersList);
          appChangeList.addAll(educationList);
          appChangeList.addAll(advertisingList);
          appChangeList.addAll(coursesList);
          appChangeList.addAll(graduatedYearsList);
          appChangeList.addAll(firstNamesList);
          appChangeList.addAll(lastNamesList);
          
          return appChangeList;
      }
      
      /**
       * Display error message about no rights to access the information
       */
      public ModelAndView errorMessage() {
          
         ModelAndView mv = new ModelAndView();
         mv.setViewName("hr/errorMessage");
         mv.addObject("title", "Ошибка");
         mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как HR");
         mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
         return mv;
     }
      
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the ID
     */
    public static class IdComparator implements Comparator <ApplicationChange> {
        private boolean asc; // true - sorts in ascending order, fasle - descending
        public IdComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(ApplicationChange obj1, ApplicationChange obj2) {
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
    public static class FirstNameComparator implements Comparator <ApplicationChange> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public FirstNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(ApplicationChange o1, ApplicationChange o2) {
            return asc ? (o1.getFirstName().compareToIgnoreCase(o2.getFirstName())) 
                       : (o2.getFirstName().compareToIgnoreCase(o1.getFirstName()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the last name
     */
    public static class LastNameComparator implements Comparator <ApplicationChange> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public LastNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(ApplicationChange o1, ApplicationChange o2) {
            return asc ? (o1.getLastName().compareToIgnoreCase(o2.getLastName())) 
                       : (o2.getLastName().compareToIgnoreCase(o1.getLastName()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the email
     */
    public static class EmailComparator implements Comparator <ApplicationChange> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public EmailComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(ApplicationChange o1, ApplicationChange o2) {
            return asc ? (o1.getEmail().compareToIgnoreCase(o2.getEmail())) 
                       : (o2.getEmail().compareToIgnoreCase(o1.getEmail()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order by the email
     */
    public static class FieldNameComparator implements Comparator <ApplicationChange> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public FieldNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(ApplicationChange o1, ApplicationChange o2) {
            return asc ? (o1.getFieldName().compareToIgnoreCase(o2.getFieldName())) 
                       : (o2.getFieldName().compareToIgnoreCase(o1.getFieldName()));
        }
    }

}