package com.netcracker.libra.model.Reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 * POJO object for excel report
 *
 * @author MorrisDeck
 */
public class ExcelReportPOJO {

    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;    
    private String phoneNumber;
    private String university;
    private String department;
    private String faculty;
    private int course;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }


    

    /**
     * RowMapper for this POJO
     */
    public static class ExcelReportMapper implements RowMapper<ExcelReportPOJO> {

        @Override
        public ExcelReportPOJO mapRow(ResultSet rs, int i) throws SQLException {
            ExcelReportPOJO obj = new ExcelReportPOJO();
            obj.setFirstName(rs.getString("firstname"));
            obj.setLastName(rs.getString("lastname"));
            obj.setPatronymic(rs.getString("patronymic"));
            obj.setEmail(rs.getString("email"));
            obj.setPhoneNumber(rs.getString("phonenumber"));
            obj.setUniversity(rs.getString("universityName"));
            obj.setDepartment(rs.getString("departmentName"));
            obj.setFaculty(rs.getString("facultyName"));
            obj.setCourse(rs.getInt("course"));            
            return obj;
        }
    }
}