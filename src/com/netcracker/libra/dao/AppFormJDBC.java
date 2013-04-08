/*
 * Contains methods for retrieving and updating values from persistence.
 * 
 * @author Konstantin Kuyun
 */

package com.netcracker.libra.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.netcracker.libra.model.RegisterForm;

@Repository
public class AppFormJDBC {

	private static JdbcTemplate jdbcTemplateObject;

	public AppFormJDBC() {
	}

	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
	
	//@return ID for current active template
	public int queryForActiveTemplateId() {
		return jdbcTemplateObject.queryForInt("select templateid from template where active=1");
	}
	
	//@return Next value for appform from DB sequence
	public Integer getAppformNextVal() {
		return jdbcTemplateObject
				.queryForInt("select AppForm_seq.NEXTVAL from dual");
	}
	
	//@return Next value for user from DB sequence
	public Integer getUserNextVal() {
		return jdbcTemplateObject
				.queryForInt("select User_seq.NEXTVAL from dual");
	}
	
	//insert records in DB for new user
	public void createNewUserAsStudent(Integer userid, String name, String lastName, String password, String email) {
		jdbcTemplateObject.update("insert into users values (?,?,?,?,?,1)", userid, name, lastName, email, password);
	}
	
	//insert user's details
	public void insertAppFormDetails(Integer appId, Integer userId, String patronymic, 
			String phoneNumber, Long deptId, int adId, int course, Long graduated, int templateId) {
		jdbcTemplateObject.update("insert into appform(appid, userid,patronymic,phonenumber,departmentid,advertisingid,course,graduated,datacreation,templateid) " +
				"values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?)", 
			appId, userId, patronymic, phoneNumber, 
					deptId, adId, course, graduated, templateId);
	}
	
	//insert user's appform answers
	@Transactional
	public void fillAppForm(RegisterForm form, Integer userId) {
		Integer appId = jdbcTemplateObject.queryForInt("select appid from appform where userid=?", userId);
		if (appId != null)
			for (String x : form.getMap().keySet()) {
				jdbcTemplateObject.update("insert into COLUMNFIELDS (columnid, appid, value, status) values (?,?,?,1)", x, appId, form.getMap().get(x));
			}
		return;
	}
	
	public void updateTemplateIdOnFormSubmit(int templateId, Integer userId) {
		jdbcTemplateObject.update("update appform set templateid=? where userid=?", templateId, userId);
		return;
	}
	
	
	//check for filled appform
	public boolean isAppFormPresent(Integer userId) {
		Integer result = 
				jdbcTemplateObject.queryForInt(
						"select count(*) " +
						"from columnfields " +
						"where appid=(select appid from appform where userid=?)",userId);
		if (result > 0) 
			return true;
		else
			return false;
	}
	
	
	//check for duplicate emails
	public boolean isEmailAlreadyExist(String email) {
		Integer result = 
				jdbcTemplateObject.queryForInt("select count(*) from users where email=?", email);
		if (result > 0)
			return true;
		else
			return false;
	}
}
