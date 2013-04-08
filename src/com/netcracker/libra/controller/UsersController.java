package com.netcracker.libra.controller;

import com.netcracker.libra.dao.AdminJDBC;
import com.netcracker.libra.model.User;
import com.netcracker.libra.service.LengthService;
import com.netcracker.libra.util.mail.SendMailService;
import com.netcracker.libra.util.security.Security;
import com.netcracker.libra.util.security.SessionToken;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for administrator
 * 
 * - displaying the employees (HR, Tech.interviewer, Administrator)
 * - sorting by a job title, ID, first name, last name, email, password
 *   in ascending or descending order
 * - filtering by a job title
 * - searching by a full name, first name, last name, email
 * - addition user
 * - editing user
 * - removes user
 * - changes user's password
 * 
 * @author Alexander Lebed
 */
@Controller
@RequestMapping("admin")
@SessionAttributes("LOGGEDIN_USER")
public class UsersController {
 
    List <User> employees;  // list of employees (HR, TECH, ADMIN)
    String noResults;       //value in no results case
    String checked;         //selected value in the filter by user's job title
    String jobTitle;        //job title of user
    String selected;        //selected value in the sorting by first name/last name/email etc.
    String text;            //value in the text field
    boolean order;          //value of ascending or descending order; true when ascending
    int currentUserId;      //id of current administrator
    AdminJDBC jdbc = new AdminJDBC();   //works with database
    
    /**
     * Displays on the page all employees (HR, TECH, ADMIN)
     */
    @RequestMapping("employees")
    public ModelAndView showAllEmployees(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            employees = jdbc.getAllEmployees();
            checked = "checkedAll";
            selected = "selectedAll";
            currentUserId = token.getUserId();
            
            mv.setViewName("admin/employees");
            mv.addObject("employees", employees);
            mv.addObject(checked, "checked");
            mv.addObject(selected, "selected");
            mv.addObject("roleOrder", "<img  src=\"../resources/images/admin/arrow_up.png\" width=\"12\" height=\"12\" title=\"по убыванию\"/>");
            order = false;
            mv.addObject("currentUserId", currentUserId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    /**
     * Returns settings and employees who have been showed before
     * Invokes when user clicks "Cancel"
     */
    @RequestMapping("currentEmployees")
    public ModelAndView showCurrentEmployees(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/employees");
            mv.addObject(checked, "checked");
            mv.addObject(selected, "selected");
            mv.addObject("text", text);
            mv.addObject("employees", employees);
            mv.addObject("currentUserId", currentUserId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>"); 
            return mv;
        }
    }
    
    /**
     * Displays on the page employees by results of sorting/filtering/searching
     * or throws an appropriate message when no employees
     * and saves chosen values
     * @param role - job title of user (0 - all employees, 2 - HR, 3 - Tech, 4 - Admin)
     * @param textValue the value entered in the text field
     * @param byWhat - the value of sorting (by first name/email etc.)
     */
    @RequestMapping("requiredEmployees")
    public ModelAndView showRequiredEmployees(
                @ModelAttribute("LOGGEDIN_USER") SessionToken token,
                @RequestParam("role") int role,
                @RequestParam("textValue") String textValue,
                @RequestParam("byWhat") String byWhat) {
        
        if(token.getUserAccessLevel()==3) {
            
            text = textValue;
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/employees");
        
            //defining the job title and radio buttons
            switch(role) {
                case 0: checked = "checkedAll";
                        break;
                case 2: checked = "checkedHR";
                        jobTitle = "HR-менеджер";
                        break;
                case 3: checked = "checkedTech";
                        jobTitle = "Тех.интервьюер";
                        break;
                case 4: checked = "checkedAdmin";
                        jobTitle = "Администратор";
                        break;
            }
            mv.addObject(checked, "checked");
            mv.addObject("text", text);
        
            //defining the search value and saving this value
            switch(byWhat) {
                case "ALL":
                    if(role == 0) {
                            employees = jdbc.getAllEmployees();
                            noResults = "Сотрудники не найдены <br/>";
                            selected = "selectedAll";
                            break;
                        }
                    else {  
                            employees = jdbc.getAllEmployeesByRole(role);
                            noResults = jobTitle+"ы не найдены <br/>";
                            selected = "selectedAll";
                            break;
                        }
                
                case "FULL_NAME":
                    if(role == 0) {
                            employees = jdbc.getAllEmployeesByFullName(textValue);
                            noResults = "Сотрудники(-и) с именем "+ textValue +" не найден(-ы)";
                            selected = "selectedFull";
                            break;
                        }
                    else {
                            employees = jdbc.getAllEmployeesByFullNameAndRole(textValue, role);
                            noResults = jobTitle+"(-ы) с именем "+ textValue +" не найден(-ы)";
                            selected = "selectedFull";
                            break;
                        }
                
                case "FIRST_NAME":
                    if(role == 0) {
                            employees = jdbc.getAllEmployeesByFirstName(textValue);
                            noResults = "Сотрудники(-и) с именем "+ textValue +" не найден(-ы)";
                            selected = "selectedFirst";
                            break;
                        }
                    else {
                            employees = jdbc.getAllEmployeesByFirstNameAndRole(textValue, role);
                            noResults = jobTitle+"(-ы) с именем "+ textValue +" не найден(-ы)";
                            selected = "selectedFirst";
                            break;
                        }
                
                case "LAST_NAME":
                    if(role == 0) {
                            employees = jdbc.getAllEmployeesByLastName(textValue);
                            noResults = "Сотрудники(-и) с фамилией "+ textValue +" не найден(-ы)";
                            selected = "selectedLast";
                            break;
                        }
                    else {
                            employees = jdbc.getAllEmployeesByLastNameAndRole(textValue, role);
                            noResults = jobTitle+"(-ы) с фамилией "+ textValue +" не найден(-ы)";
                            selected = "selectedLast";
                            break;
                        }
                
                case "EMAIL":
                    if(role == 0) {
                            employees = jdbc.getAllEmployeesByEmail(textValue);
                            noResults = "Сотрудники с эл.почтой \""+ textValue +"\" не найден";
                            selected = "selectedEmail";
                            break;
                        }
                    else {
                            employees = jdbc.getAllEmployeesByEmailAndRole(textValue, role);
                            noResults = jobTitle+"с эл.почтой \""+ textValue +"\" не найден";
                            selected = "selectedEmail";
                            break;
                        }
                
            }
            mv.addObject("employees", employees);
            mv.addObject("noResults", noResults);
            mv.addObject(selected, "selected");
            mv.addObject("currentUserId", currentUserId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    /**
     * The sorting in ascending or descending order 
     * by the job title, ID, first name, last name and email
     * 
     * @param orderBy the value of sorting (like by the "ID" or "FIRST_NAME")
     */
    @RequestMapping("sortEmployees")
    public ModelAndView sortEmployeesByLink(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                            @RequestParam("orderBy") String orderBy) {
        
        if(token.getUserAccessLevel()==3) {
            String up = "<img  src=\"../resources/images/admin/arrow_down.png\" width=\"12\" height=\"12\" title=\"по возрастанию\"/>";
            String down = "<img  src=\"../resources/images/admin/arrow_up.png\" width=\"12\" height=\"12\" title=\"по убыванию\"/>";
        
            ModelAndView mv = new ModelAndView();
        
            if(employees.size() > 1) {
                switch(orderBy) {
                case "ROLE":
                    Collections.sort(employees, new UsersController.RoleComparator(order)); //sort by role id (2,3,4)
                    String roleOrder = (order) ? down : up;
                    mv.addObject("roleOrder", roleOrder);
                    break;
                
                case "ID":
                    Collections.sort(employees, new UsersController.IdComparator(order));
                    String idOrder = (order) ? up : down;
                    mv.addObject("idOrder", idOrder);
                    break;
                
                case "FIRST_NAME":
                    Collections.sort(employees, new UsersController.FirstNameComparator(order));
                    String nameOrder = (order) ? up : down;
                    mv.addObject("nameOrder", nameOrder);
                    break;
                
                case "LAST_NAME":
                    Collections.sort(employees, new UsersController.LastNameComparator(order));
                    nameOrder = (order) ? up : down;
                    mv.addObject("nameOrder", nameOrder);
                    break;
                
                case "EMAIL":
                    Collections.sort(employees, new UsersController.EmailComparator(order));
                    String emailOrder = (order) ? up : down;
                    mv.addObject("emailOrder", emailOrder);
                    break;
                }
            }
            //switch to ascending or descending order
            order = (order) ? false : true;

            mv.setViewName("admin/employees");
            mv.addObject("currentUserId", currentUserId);
            mv.addObject(selected, "selected");
            mv.addObject(checked, "checked");
            mv.addObject("text", text);
            mv.addObject("employees", employees);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    
    
    /**
     * Displays the page for edit the data of certain user by his or her ID
     */
    @RequestMapping("editEmployee")
    public ModelAndView showEditEmployeeView(@ModelAttribute("LOGGEDIN_USER") SessionToken token, 
                                             @RequestParam("employeeId") int employeeId) {
        if(token.getUserAccessLevel()==3) {
            User employee = jdbc.getEmployee(employeeId);
        
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/editEmployee");
            mv.addObject("employeeId", employeeId);
            mv.addObject("firstName", employee.getFirstName());
            mv.addObject("lastName", employee.getLastName());
            mv.addObject("email", employee.getEmail());
            mv.addObject("roleId", employee.getRoleId());
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
        
        
    }
    
    /**
     * Changes the user's data by his or her ID 
     * and displays the statement of changes
     */
    @RequestMapping("doneEdit")
    public ModelAndView editEmployee (
                        @ModelAttribute("LOGGEDIN_USER") SessionToken token,
                        @RequestParam("roleId") int roleId,
                        @RequestParam("employeeId") int employeeId,
                        @RequestParam("firstName") String firstName, 
                        @RequestParam("lastName") String lastName,
                        @RequestParam("email") String email) {
        
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
        
            String errorMessage = "";
            String firstNameMark = "";
            String lastNameMark = "";
            String emailMark = "";

            //methods of LengthService class return an empty string if the input value satisfies DB; 
            //otherwise - the message with the restriction
            String firstNameError = LengthService.checkFirstNameLength(firstName);
            String lastNameError = LengthService.checkLastNameLength(lastName);
            String emailError = LengthService.checkEmailLength(email);

            if(!firstNameError.equals("")) {
                firstNameMark = "error";
                errorMessage = firstNameError;
            }
            if(!lastNameError.equals("")) {
                lastNameMark = "error";
                errorMessage += lastNameError;
            }
            if(!emailError.equals("")) {
                emailMark = "error";
                errorMessage += emailError;
            }
            //checks the duplicate emails, true if there are
            if(jdbc.hasDuplicateEmailExceptThis(email, employeeId)){
                emailMark = "error";
                errorMessage += "Пожалуйста, введите другой эл.адрес. Пользователь с эл.адресом \""+email+"\" уже зарегистрирован";
            }
            if(!errorMessage.equals("")) {
                mv.setViewName("admin/editEmployee");
                mv.addObject("errorMessage", errorMessage);
                mv.addObject("employeeId", employeeId);
                mv.addObject("firstNameMark", firstNameMark);
                mv.addObject("lastNameMark", lastNameMark);
                mv.addObject("emailMark", emailMark);
                mv.addObject("firstName", firstName);
                mv.addObject("lastName", lastName);
                mv.addObject("roleId", roleId);
                mv.addObject("email", email);
                return mv;
            }
            else {
                User user = jdbc.getEmployee(employeeId);
                String lastEmail = user.getEmail();
                int lastRoleId = user.getRoleId();
                
                jdbc.updateEmployee(employeeId, firstName, lastName, email, roleId);
                
                if(!lastEmail.equals(email)) {
                    //send notification to user about the changing his password
                    Map model = new HashMap(); 
                    model.put("user", firstName); 
                    model.put("email", email); 
                    SendMailService.sendMail(lastEmail, model, "Изменение логина");
                }
                
                if(lastRoleId != roleId) {
                    String lastRoleName = "";
                    String newRoleName = "";
                    
                    switch(roleId) {
                        case 2 : newRoleName = "HR";
                                 break;
                        case 3 : newRoleName = "Технический интервьюер";
                                 break;
                        case 4 : newRoleName = "Администратор сайта";
                                 break;
                    }
                    
                    switch(lastRoleId) {
                        case 2 : lastRoleName = "HR";
                                 break;
                        case 3 : lastRoleName = "Технический интервьюер";
                                 break;
                        case 4 : lastRoleName = "Администратор сайта";
                                 break;
                    }
                    
                    Map model = new HashMap();
                    model.put("firstName", firstName);
                    model.put("lastRoleName", lastRoleName);
                    model.put("newRoleName", newRoleName);
                    SendMailService.sendMail(lastEmail, model, "Изменение статуса");
                }

                //update the user's data in list
                for (User emp : employees) {
                    if (emp.getUserId() == employeeId) {
                        emp.setFirstName(firstName);
                        emp.setLastName(lastName);
                        emp.setEmail(email);
                        emp.setRoleId(roleId);
                    }
                }

                mv.setViewName("admin/employees");
                mv.addObject("message", "Информация о сотруднике успешно изменена");
                mv.addObject(checked, "checked");
                mv.addObject(selected, "selected");
                mv.addObject("text", text);
                mv.addObject("employees", employees);
                mv.addObject("currentUserId", currentUserId);
                return mv;
            }
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
        
        
    }
    
    /**
     * Displays the page with changing the password of user
     */
    @RequestMapping("resetEmployeePassword")
    public ModelAndView showResetEmployeePassword (@ModelAttribute("LOGGEDIN_USER") SessionToken token, 
                                                   @RequestParam("employeeId") int employeeId) {
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/resetEmployeePassword");
            mv.addObject("id", employeeId);
            mv.addObject(checked, "checked");
            mv.addObject(selected, "selected");
            mv.addObject("text", text);
            mv.addObject("employees", employees);
            mv.addObject("currentUserId", currentUserId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
           mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    /**
     * Reset the password of user, goes to the page with employees
     * and displays the message of changing
     */
    @RequestMapping("doneResetEmployeePassword")
    public ModelAndView resetEmployeePassword (@ModelAttribute("LOGGEDIN_USER") SessionToken token, 
                                               @RequestParam("employeeId") int employeeId) {
        if(token.getUserAccessLevel()==3) {
            Random generator = new Random();
            //6-digit random number
            int randomNumber =  100000 + generator.nextInt(900000);
            String randomString = String.valueOf(randomNumber);
            String randomHashedPassword = Security.getMD5hash(randomString);
            
            jdbc.changePassword(randomHashedPassword, employeeId);
            
            User user = jdbc.getEmployee(employeeId);
            String firstName = user.getFirstName();
            String email = user.getEmail();
            
            //send notification to user about the changing his password
            Map model = new HashMap(); 
            model.put("user", firstName); 
            model.put("password", randomString); 
            SendMailService.sendMail(email, model, "Сброс пароля");

            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/employees");
            mv.addObject(checked, "checked");
            mv.addObject(selected, "selected");
            mv.addObject("text", text);
            mv.addObject("employees", employees);
            mv.addObject("currentUserId", currentUserId);
            mv.addObject("message", "Пароль сброшен");
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    /**
     * Display the page with changing the password of current session administrator
     */
    @RequestMapping("changeOwnPassword")
    public ModelAndView showChangeOwnPasswordView(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                                  @RequestParam("employeeId") int employeeId) {
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/changeOwnPassword");
            mv.addObject("employeeId", employeeId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    /**
     * Change the password of current session administrator
     */
    @RequestMapping("doneChangeOwnPassword")
    public ModelAndView changeOwnPassword(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                          @RequestParam("employeeId") int employeeId,
                                          @RequestParam("currentPassword") String currentPassword,
                                          @RequestParam("newPassword") String newPassword,
                                          @RequestParam("repeatNewPassword") String repeatNewPassword) {
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            User user = jdbc.getEmployee(employeeId);
            String employeePassword = user.getPassword();

            String errorMessage = "";
            String currentPasswordMark = "";
            String newPasswordMark = "";
            String repeatNewPasswordMark = "";

            String currentHashedPass = Security.getMD5hash(currentPassword);

            if(!employeePassword.equals(currentHashedPass)) {
                currentPasswordMark = "error";
                errorMessage = "Неправильный текущий пароль<br>";
            }
            if(!newPassword.equals(repeatNewPassword)) {
                repeatNewPasswordMark = "error";
                errorMessage += "Новый пароль и его дубликат не совпадают<br>";
            }

            int before = errorMessage.length();
            //returns an empty string if the input value satisfies DB; otherwise - the message with the restriction
            errorMessage += LengthService.checkPasswordLength(newPassword);

            int after = errorMessage.length();

            if(after > before) {
                newPasswordMark = "error";
            }

            if(errorMessage.equals("")) {
                String newHashedPassword = Security.getMD5hash(newPassword);
                jdbc.changePassword(newHashedPassword, employeeId);
                
                String firstName = user.getFirstName();
                String email = user.getEmail();
                
                //send notification to user about the changing his password
                Map model = new HashMap(); 
                model.put("user", firstName); 
                model.put("password", newPassword); 
                SendMailService.sendMail(email, model, "Изменение пароля");

                mv.setViewName("admin/employees");
                mv.addObject(checked, "checked");
                mv.addObject(selected, "selected");
                mv.addObject("text", text);
                mv.addObject("employees", employees);
                mv.addObject("currentUserId", currentUserId);
                mv.addObject("message", "Пароль успешно изменен");
                return mv;
            }
            else {
                mv.setViewName("admin/changeOwnPassword");
                mv.addObject("errorMessage", errorMessage);
                mv.addObject("employeeId", employeeId);
                mv.addObject("currentPassword", currentPassword);
                mv.addObject("newPassword", newPassword);
                mv.addObject("repeatNewPassword", repeatNewPassword);
                mv.addObject("currentPasswordMark", currentPasswordMark);
                mv.addObject("newPasswordMark", newPasswordMark);
                mv.addObject("repeatNewPasswordMark", repeatNewPasswordMark);
                return mv;
            }
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
        
    }
    
    /**
     * Displays on the page all employees (HR, Tech.interviewer, Administrator)
     * and the ability to add the new one
     */
    @RequestMapping("addEmployee")
    public ModelAndView showAddEmployeeView(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/addEmployee");
            mv.addObject("techSelected", "selected");
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
    }
    
    /**
     * Add a new user and displays the statement of changes
     */
    @RequestMapping("doneAdd")
    public ModelAndView addEmployee (@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                     @RequestParam("roleId") int roleId,
                                     @RequestParam("firstName") String firstName, 
                                     @RequestParam("lastName") String lastName,
                                     @RequestParam("email") String email, 
                                     @RequestParam("password") String password) {
        if(token.getUserAccessLevel()==3) {
            
            ModelAndView mv = new ModelAndView();
            //values to select item in dropbox
            String hrSelected = "";
            String techSelected = "";
            String adminSelected = "";
            String roleName = "";

            switch(roleId) {
                case 2: hrSelected = "selected";
                        roleName = "HR";
                        break;
                case 3: techSelected = "selected";
                        roleName = "Технический интервьюер";
                        break;
                case 4: adminSelected = "selected";
                        roleName = "Администратор сайта";
                        break;
            }

            String errorMessage = "";
            //values to highlight error window
            String firstNameMark = "";
            String lastNameMark = "";
            String emailMark = "";
            String passwordMark = "";

            //methods of LengthService class return an empty string 
            //if the input value satisfies DB; 
            //otherwise - display the message with the restriction
            String firstNameError = LengthService.checkFirstNameLength(firstName);
            String lastNameError = LengthService.checkLastNameLength(lastName);
            String emailError = LengthService.checkEmailLength(email);
            String passwordError = LengthService.checkPasswordLength(password);

            if(!firstNameError.equals("")) {
                firstNameMark = "error";
                errorMessage = firstNameError;
            }
            if(!lastNameError.equals("")) {
                lastNameMark = "error";
                errorMessage += lastNameError;
            }
            if(!emailError.equals("")) {
                emailMark = "error";
                errorMessage += emailError;
            }
            if(!passwordError.equals("")) {
                passwordMark = "error";
                errorMessage += passwordError;
            }
            //checks the duplicate emails, true if there are
            if(jdbc.hasDuplicateEmail(email)){
                emailMark = "error";
                errorMessage += "Пожалуйста, введите другой эл. адрес. Пользователь с эл. адресом \""
                                                + email + "\" уже зарегистрирован";
            }
            if(!errorMessage.equals("")) {
                mv.setViewName("admin/addEmployee");
                mv.addObject("errorMessage", errorMessage);
                mv.addObject("hrSelected", hrSelected);
                mv.addObject("techSelected", techSelected);
                mv.addObject("adminSelected", adminSelected);
                mv.addObject("firstNameMark", firstNameMark);
                mv.addObject("lastNameMark", lastNameMark);
                mv.addObject("emailMark", emailMark);
                mv.addObject("passwordMark", passwordMark);
                mv.addObject("roleId", roleId);
                mv.addObject("firstName", firstName);
                mv.addObject("lastName", lastName);
                mv.addObject("email", email);
                mv.addObject("password", password);
                return mv;
            }
            else {

                jdbc.addEmployee(firstName, lastName, email, Security.getMD5hash(password), roleId);
                User employee = jdbc.getEmployee(email);

                if(employee != null) {
                    employees.add(employee);
                    //send notification to user about the changing his password
                    Map model = new HashMap(); 
                    model.put("email", email);
                    model.put("password", password);
                    model.put("roleName", roleName);
                    model.put("firstName", firstName); 
                    model.put("lastName", lastName);
                    SendMailService.sendMail(email, model, "Добавление сотрудника");
                }

                mv.setViewName("admin/employees");
                mv.addObject("message", "Сотрудник успешно добавлен");
                mv.addObject(checked, "checked");
                mv.addObject(selected, "selected");
                mv.addObject("text", text);
                mv.addObject("employees", employees);
                mv.addObject("currentUserId", currentUserId);
                return mv;
            }
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
        
        
    }
    
    /**
     * Displays the page with confirmation about deleting the user (Y/N)
     */
    @RequestMapping("deleteEmployee")
    public ModelAndView showDeleteEmployeeView(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                               @RequestParam("employeeId") int employeeId) {
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/delete");
            mv.addObject("id", employeeId);
            mv.addObject(checked, "checked");
            mv.addObject(selected, "selected");
            mv.addObject("text", text);
            mv.addObject("employees", employees);
            mv.addObject("currentUserId", currentUserId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
        
    }
    
    /**
     * Deleting of user from all database tables
     * and displays the statement of changes
     */
    @RequestMapping("doneDelete")
    public ModelAndView deleteEmployee (@ModelAttribute("LOGGEDIN_USER") SessionToken token,
                                        @RequestParam("employeeId") int employeeId) {
        if(token.getUserAccessLevel()==3) {
            ModelAndView mv = new ModelAndView();
            
            User user = jdbc.getEmployee(employeeId);
            String firstName = user.getFirstName();
            String email = user.getEmail();
            
            //send notification to user about the changing his password
            Map model = new HashMap(); 
            model.put("user", firstName);
            SendMailService.sendMail(email, model, "Удаление сотрудника");
            
            //deleting of user from all database tabels
            jdbc.deleteEmployee(employeeId);

            //delete user from list
            for(ListIterator <User> i = employees.listIterator(); i.hasNext(); ) {
                User emp = i.next();
                if(emp.getUserId() == employeeId) {
                    i.remove();
                }
            }

            mv.setViewName("admin/employees");
            mv.addObject("message", "Сотрудник успешно удален");
            mv.addObject(checked, "checked");
            mv.addObject(selected, "selected");
            mv.addObject("text", text);
            mv.addObject("employees", employees);
            mv.addObject("currentUserId", currentUserId);
            return mv;
        }
        else {
            ModelAndView mv = new ModelAndView();
            mv.setViewName("admin/message");
            mv.addObject("title", "Ошибка");
            mv.addObject("message","Чтобы получить доступ к следующей информации, пожалуйста, авторизируйтесь как администратор");
            mv.addObject("link","<a href='/Libra/' class=\"btn\"><img  src=\"../resources/images/admin/glyphicons_224_chevron-left.png\" width=\"7\" height=\"7\"/> Назад </a>");
            return mv;
        }
        
    }

    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order 
     * by the ID (look the sortEmployeesByLink method in UsersController class).
     */
    public static class IdComparator implements Comparator <User> {
        private boolean asc; // true - sorts in ascending order, fasle - descending
        public IdComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(User user1, User user2) {
            if(asc) {
                return (user1.getUserId() > user2.getUserId()) ? 1 
                    : (user1.getUserId() == user2.getUserId()) ? 0 : -1;
            }
            else {
                return (user2.getUserId() > user1.getUserId()) ? 1 
                    : (user2.getUserId() == user1.getUserId()) ? 0 : -1;
            }
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order 
     * by the first name (look the sortEmployeesByLink method in UsersController class).
     */
    public static class FirstNameComparator implements Comparator <User> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public FirstNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(User o1, User o2) {
            return asc ? (o1.getFirstName().compareToIgnoreCase(o2.getFirstName())) 
                       : (o2.getFirstName().compareToIgnoreCase(o1.getFirstName()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order 
     * by the last name (look the sortEmployeesByLink method in UsersController class).
     */
    public static class LastNameComparator implements Comparator <User> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public LastNameComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(User o1, User o2) {
            return asc ? (o1.getLastName().compareToIgnoreCase(o2.getLastName())) 
                       : (o2.getLastName().compareToIgnoreCase(o1.getLastName()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order 
     * by the email (look the sortEmployeesByLink method in UsersController class).
     */
    public static class EmailComparator implements Comparator <User> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public EmailComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(User o1, User o2) {
            return asc ? (o1.getEmail().compareToIgnoreCase(o2.getEmail())) 
                       : (o2.getEmail().compareToIgnoreCase(o1.getEmail()));
        }
    }
    
    /**
     * Comparator has been created to be passed to a method Collections.sort 
     * to sort of objects list in ascending or descending order 
     * by the role (look the sortEmployeesByLink method in UsersController class).
     */
    public static class RoleComparator implements Comparator <User> {
        private boolean asc; //true - sorts in ascending order, fasle - descending
        public RoleComparator(boolean asc) {
            this.asc = asc;
        }
        @Override
        public int compare(User user1, User user2) {
            if(asc) {
                return (user1.getRoleId() > user2.getRoleId()) ? 1 
                    : (user1.getRoleId() == user2.getRoleId()) ? 0 : -1;
            }
            else {
                return (user2.getRoleId() > user1.getRoleId()) ? 1 
                    : (user2.getRoleId() == user1.getRoleId()) ? 0 : -1;
            }
        }
    }
    
}