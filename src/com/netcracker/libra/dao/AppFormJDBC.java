package com.netcracker.libra.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.netcracker.libra.model.RegisterForm;
import com.netcracker.libra.util.security.Security;

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

	public Map<Integer, String> getAllUniversities() {

		Map<Integer, String> map = new HashMap<>();
		String sql = "select * from university";

		for (Map<String, Object> x : jdbcTemplateObject.queryForList(sql)) {
			map.put(((BigDecimal) x.get("universityid")).intValueExact(),
					(String) x.get("universityname"));
		}
		return map;

	}

	public Map<Integer, String> getAllFaculties() {

		Map<Integer, String> map = new HashMap<>();
		String sql = "select * from faculty";

		for (Map<String, Object> x : jdbcTemplateObject.queryForList(sql)) {
			map.put(((BigDecimal) x.get("facultyid")).intValueExact(),
					(String) x.get("facultyname"));
		}
		return map;

	}

	public Map<Integer, String> getAllDepartments() {

		Map<Integer, String> map = new HashMap<>();
		String sql = "select * from department";

		for (Map<String, Object> x : jdbcTemplateObject.queryForList(sql)) {
			map.put(((BigDecimal) x.get("departmentid")).intValueExact(),
					(String) x.get("departmentname"));
		}
		return map;

	}

	public Long getAppformNextVal() {
		return jdbcTemplateObject
				.queryForLong("select AppForm_seq.NEXTVAL from dual");
	}

	public Long getUserNextVal() {
		return jdbcTemplateObject
				.queryForLong("select User_seq.NEXTVAL from dual");
	}

	public Map queryForProgrammingLanguages() {

		List<Map<String, Object>> list = jdbcTemplateObject
				.queryForList("select * from languages where languagename!=ALL('Java', 'C++')");
		Map map = new HashMap();

		for (Map x : list) {
			map.put(x.get("languageId"), x.get("languagename"));
		}

		return map;
	}
	
	public boolean createNewUserAsStudent(Long userid, String name, String lastName, String password, String email) throws SQLException {
		boolean success = false;
		jdbcTemplateObject.update("insert into users values (?,?,?,?,?,1)", userid, name, lastName, email, password);
		success = true;
		return success;
	}
	
	public boolean insertAppFormDetails(Long appId, Long userId, String patronymic, 
			String phoneNumber, Long deptId, int adId, int course, Long graduated, int templateId) throws SQLException {
		boolean success = false;
		jdbcTemplateObject.update("insert into appform(appid, userid,patronymic,phonenumber,departmentid,advertisingid,course,graduated,datacreation,templateid) " +
				"values (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?)", 
			appId, userId, patronymic, phoneNumber, 
					deptId, adId, course, graduated, templateId);
		success = true;
		return success;
	}
	
	public void fillAppForm(RegisterForm form) throws SQLException {
		if (createNewUserAsStudent(form.getUserId(), form.getName(), form.getLastName(), Security.getMD5hash(form.getPassword()), form.getEmail()) && 
				insertAppFormDetails(form.getAppId(), form.getUserId(), form.getPatronymic(), 
						form.getPhoneNumber(), form.getDepartment(), 1, 
							form.getCourse(), form.getGraduated(), form.getTemplateId())) {
			Long appId = form.getAppId();
			for (String x : form.getMap().keySet()) {
				jdbcTemplateObject.update("insert into COLUMNFIELDS (columnid, appid, value, status) values (?,?,?,1)", x, appId, form.getMap().get(x));
			}
		}
		return;
	}
}
