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

public class RegformService {
	
	private static AppFormJDBC jdbc = new AppFormJDBC();
	private static ColumnJDBC cjdbc = new ColumnJDBC();
	private static HrJDBC hjdbc = new HrJDBC();
	
	public static List<University> getUniversityList() {
		return hjdbc.getAllUniversity();
	}
	
	public static List<Faculty> getFacultyListByUniversityID(int univerId) {
		return hjdbc.getAllFaculties(univerId);
	}
	
	public static List<Department> getDepartmentListByFacultyID(int facultyId) {
		return hjdbc.getAllDepartments("f.facultyid", facultyId);
	}	

	public static Long getAppformId() {
		return jdbc.getAppformNextVal();
	}

	public static Long getUserId() {
		return jdbc.getUserNextVal();
	}
	
	public static List<AppFormColumns> getActiveTemplate() {
		return cjdbc.getAppFormColumnsForActiveTemplate();
	}
	
	public static int getActiveTemplateId() {
		return jdbc.queryForActiveTemplateId();
	}
	
	@Transactional
	public static void registerUser(RegisterForm form) {
		jdbc.createNewUserAsStudent(form.getUserId(), form.getName(), form.getLastName(), Security.getMD5hash(form.getPassword()), form.getEmail());
		jdbc.insertAppFormDetails(form.getAppId(), form.getUserId(), form.getPatronymic(), form.getPhoneNumber(), form.getDepartment(), 1, form.getCourse(), form.getGraduated(), 0);
		return;
	}
	
	@Transactional
	public static void insertAppformAnswers(RegisterForm form, Long userId) throws SQLException {
		jdbc.updateTemplateIdOnFormSubmit(userId);
		jdbc.fillAppForm(form, userId);
		return;
	}
	

}
