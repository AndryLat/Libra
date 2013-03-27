package com.netcracker.libra.dao;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
	
	public int queryForActiveTemplateId() {
		return jdbcTemplateObject.queryForInt("select templateid from template where active=1");
	}

	public Long getAppformNextVal() {
		return jdbcTemplateObject
				.queryForLong("select AppForm_seq.NEXTVAL from dual");
	}

	public Integer getUserNextVal() {
		return jdbcTemplateObject
				.queryForInt("select User_seq.NEXTVAL from dual");
	}
	
	public void createNewUserAsStudent(Integer userid, String name, String lastName, String password, String email) {
		jdbcTemplateObject.update("insert into users values (?,?,?,?,?,1)", userid, name, lastName, email, password);
	}
	
	public void insertAppFormDetails(Long appId, Integer userId, String patronymic, 
			String phoneNumber, Long deptId, int adId, int course, Long graduated, int templateId) {
		jdbcTemplateObject.update("insert into appform(appid, userid,patronymic,phonenumber,departmentid,advertisingid,course,graduated,datacreation,templateid) " +
				"values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?)", 
			appId, userId, patronymic, phoneNumber, 
					deptId, adId, course, graduated, templateId);
	}
	
	public void fillAppForm(RegisterForm form, Integer userId) throws SQLException {
		Long appId = jdbcTemplateObject.queryForLong("select appid from appform where userid=?", userId);
			for (String x : form.getMap().keySet()) {
				jdbcTemplateObject.update("insert into COLUMNFIELDS (columnid, appid, value, status) values (?,?,?,1)", x, appId, form.getMap().get(x));
			}
		return;
	}
	
	public void updateTemplateIdOnFormSubmit(Integer userId) {
		jdbcTemplateObject.update("update appform set templateid=? where userid=?", userId);
		return;
	}
}
