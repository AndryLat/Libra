
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.HrJDBC;
import com.netcracker.libra.model.DateAndInterviewer;
import com.netcracker.libra.model.DateAndInterviewerResults;
import com.netcracker.libra.model.Department;
import com.netcracker.libra.model.Faculty;
import com.netcracker.libra.model.Student;
import com.netcracker.libra.model.University;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
          
          HrJDBC hrjdbc = new HrJDBC();
          List <Integer> interviewIds = hrjdbc.getInterviewIds(appId);
          
          if(interviewIds.isEmpty()) {
              //if no any information about the interview, displayed the message
              notAssigned = "Студент "+ firstName +" "+ lastName +" не записался на интервью";
          }
          
          for(Integer id : interviewIds) {
              
              int interviewId = id;
              //string of interview's finish date and time
              String interviewDateFinish = hrjdbc.getInterviewFinishDate(interviewId);
              boolean wasInterviewed = hrjdbc.getInterviewResults(interviewId);
              
              if(actualInterview(interviewDateFinish)) {
                  //else if the student will be interviewed, diplayed application's form ID, 
                  //interview's date and time, assigned interviewers
                  List <DateAndInterviewer> resultList = hrjdbc.getDateAndInterviewer(interviewId);
                  dateAndInterviewerList.addAll(resultList);
              }
              else if(wasInterviewed) {
                  //else if the student was interviewed, displayed the application's form ID, date and time of the interview, 
                  //assigned interviewers, results of the interview (marks, comments)
                  List <DateAndInterviewerResults> resultList = hrjdbc.getDateAndInterviewerResults(interviewId);
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


      
      

     
    

}

