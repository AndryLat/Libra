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

	public Long getUserNextVal() {
		return jdbcTemplateObject
				.queryForLong("select User_seq.NEXTVAL from dual");
	}
	
	public void createNewUserAsStudent(Long userid, String name, String lastName, String password, String email) {
		jdbcTemplateObject.update("insert into users values (?,?,?,?,?,1)", userid, name, lastName, email, password);
	}
	
	public void insertAppFormDetails(Long appId, Long userId, String patronymic, 
			String phoneNumber, Long deptId, int adId, int course, Long graduated, int templateId) {
		jdbcTemplateObject.update("insert into appform(appid, userid,patronymic,phonenumber,departmentid,advertisingid,course,graduated,datacreation,templateid) " +
				"values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?)", 
			appId, userId, patronymic, phoneNumber, 
					deptId, adId, course, graduated, templateId);
	}
	
	public void fillAppForm(RegisterForm form) throws SQLException {
			Long appId = form.getAppId();
			for (String x : form.getMap().keySet()) {
				jdbcTemplateObject.update("insert into COLUMNFIELDS (columnid, appid, value, status) values (?,?,?,1)", x, appId, form.getMap().get(x));
			}
		return;
	}
}
