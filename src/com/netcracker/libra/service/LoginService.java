package com.netcracker.libra.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.netcracker.libra.dao.StudentJDBC;

public class LoginService {
	
	private JdbcTemplate jdbcTemplateObject;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		 this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	public static int verifyCredentials(String email, String password) {
		return StudentJDBC.verifyLogin(email, password);
	}
	
	public static int getUserAccess(int id) {
		return StudentJDBC.getAccess(id);
	}
}
