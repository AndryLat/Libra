/*
 * @author Konstantin Kuyun
 */

package com.netcracker.libra.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.netcracker.libra.dao.AppFormJDBC;
import com.netcracker.libra.dao.ColumnJDBC;
import com.netcracker.libra.dao.HrJDBC;
import com.netcracker.libra.model.AppFormColumns;
import com.netcracker.libra.model.Department;
import com.netcracker.libra.model.Faculty;
import com.netcracker.libra.model.RegisterForm;
import com.netcracker.libra.model.University;
import com.netcracker.libra.util.security.Security;
import com.netcracker.libra.util.security.SessionToken;

public class RegformService {
	
	private static AppFormJDBC jdbc = new AppFormJDBC();
	private static ColumnJDBC cjdbc = new ColumnJDBC();
	private static HrJDBC hjdbc = new HrJDBC();
	
	//@return List of all universities defined in the system
	public static List<University> getUniversityList() {
		return hjdbc.getAllUniversity();
	}
	
	//@return List of all faculties defined in the system
	public static List<Faculty> getFacultyListByUniversityID(int univerId) {
		return hjdbc.getAllFaculties(univerId);
	}
	
	//@return List of all departments defined in the system
	public static List<Department> getDepartmentListByFacultyID(int facultyId) {
		return hjdbc.getAllDepartments("f.facultyid", facultyId);
	}	

	public static Integer getAppformId() {
		return jdbc.getAppformNextVal();
	}

	public static Integer getUserId() {
		return jdbc.getUserNextVal();
	}
	
	public static List<AppFormColumns> getActiveTemplate() {
		return cjdbc.getAppFormColumnsForActiveTemplate();
	}
	
	public static int getActiveTemplateId() {
		return jdbc.queryForActiveTemplateId();
	}
	
	public static boolean isAppFormPresent(Integer userId) {
		return jdbc.isAppFormPresent(userId);
	}
	
	public static boolean checkEmailAvailability(String email) {
		return jdbc.isEmailAlreadyExist(email);
	}
	
	@Transactional
	public static void registerUser(RegisterForm form) {
		jdbc.createNewUserAsStudent(form.getUserId(), form.getName(), form.getLastName(), Security.getMD5hash(form.getPassword()), form.getEmail());
		jdbc.insertAppFormDetails(form.getAppId(), form.getUserId(), form.getPatronymic(), form.getPhoneNumber(), form.getDepartment(), 1, form.getCourse(), form.getGraduated(), 0);
	}
	
	@Transactional
	public static void insertAppformAnswers(RegisterForm form, Integer userId) throws SQLException {
		jdbc.updateTemplateIdOnFormSubmit(form.getTemplateId(), userId);
		jdbc.fillAppForm(form, userId);
		return;
	}
	

}
