package com.netcracker.libra.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netcracker.libra.dao.StudentJDBC;
import com.netcracker.libra.util.security.SessionToken;

public class LoginService {
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		 this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public static SessionToken login(String email, String password) {
		return StudentJDBC.verifyLogin(email, password);
	}
	
	public static int getUserAccess(Long userId) {
		return StudentJDBC.getAccess(userId);
	}
	
	public static boolean isAppformPresent(Long userId) {
		return StudentJDBC.isAppformExist(userId);
	}
}
