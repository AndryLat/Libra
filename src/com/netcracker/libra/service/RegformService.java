package com.netcracker.libra.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.netcracker.libra.dao.AppFormJDBC;
import com.netcracker.libra.dao.ColumnJDBC;
import com.netcracker.libra.model.AppFormColumns;
import com.netcracker.libra.model.RegisterForm;

public class RegformService {
	
	private static AppFormJDBC jdbc = new AppFormJDBC();
	private static ColumnJDBC cjdbc = new ColumnJDBC();
	
	private static Map<Integer, String> universities = jdbc.getAllUniversities();
	private static Map<Integer, String> faculties = jdbc.getAllFaculties(); 
	private static Map<Integer, String> departments = jdbc.getAllDepartments();
	
	public static Map<Integer, String> getUniversityList() {
		return universities;
	}
	
	public static Map<Integer, String> getFacultyList() {
		return faculties;
	}
	
	public static Map<Integer, String> getDepartmentList() {
		return departments;
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
	
	public static Map getProgrammingLanguagesList() {
		return jdbc.queryForProgrammingLanguages();
	}
	
	public static void registerUser(RegisterForm form) throws SQLException {
		jdbc.fillAppForm(form);
	}

}
