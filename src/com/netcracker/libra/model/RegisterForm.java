/*
 * This class stores values submitted by users during registration, and handles their validation
 */

package com.netcracker.libra.model;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.netcracker.libra.service.RegformService;

public class RegisterForm {
	
	private Map<String, String> map = new HashMap<>();
	private String generatedCode;
	private String enteredCode;
	private String phoneNumber;
	private Integer userId;
	private Integer appId;
	private MultipartFile photo;
	private Long department;
	private Integer course;
	private Long graduated;

	private int id;
	private int id2;
	private int id3;
	private int grade;
	private int grade2;
	private int grade3;
	private int templateId;

	
	@NotEmpty
	@Length(max=20)
	private String name;
	
	@NotEmpty
	@Length(max=30)
	private String patronymic;

	@NotEmpty
	@Length(min=2, max=30)
	private String lastName;
	
	@NotEmpty
	@Length(min=6, max=20)
	private String password;
	
	private String confirmedPassword;
	
	@NotEmpty
	@Email
	@Length(max=50)
	private String email;
	
	private boolean isPasswordsEqual;
	private boolean isCodesEqual;
	private boolean isEmailAlreadyInUse;
	
	public RegisterForm() {
		
	}
	
	public RegisterForm getRegisterForm() {
		return new RegisterForm();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId2() {
		return id2;
	}

	public void setId2(int id2) {
		this.id2 = id2;
	}

	public int getId3() {
		return id3;
	}

	public void setId3(int id3) {
		this.id3 = id3;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getGrade2() {
		return grade2;
	}

	public void setGrade2(int grade2) {
		this.grade2 = grade2;
	}

	public int getGrade3() {
		return grade3;
	}

	public void setGrade3(int grade3) {
		this.grade3 = grade3;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPatronymic() {
		return patronymic;
	}

	public void setPatronymic(String patronymic) {
		this.patronymic = patronymic;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmedPassword() {
		return confirmedPassword;
	}
	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@AssertFalse(message="Электронный адрес уже используется")
	public boolean isEmailAlreadyInUse() {
		return RegformService.checkEmailAvailability(email);
	}
	
	@AssertTrue(message="Пароли не совпадают")
	public boolean isPasswordsEqual() {
		if (password == null) {
            return false;
        } 
		else {
            return password.equals(confirmedPassword);
       }
	}

	public String getEnteredCode() {
		return enteredCode;
	}

	public void setEnteredCode(String enteredCode) {
		this.enteredCode = enteredCode;
	}

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setSubmittedMap(Map<String, String> submittedValues) {
		this.map = submittedValues;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getGeneratedCode() {
		return generatedCode;
	}

	public void setGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Long getDepartment() {
		return department;
	}

	public void setDepartment(Long department) {
		this.department = department;
	}

	public Integer getCourse() {
		return course;
	}

	public void setCourse(Integer course) {
		this.course = course;
	}

	public Long getGraduated() {
		return graduated;
	}

	public void setGraduated(Long graduated) {
		this.graduated = graduated;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
	
}
