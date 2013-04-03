/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model.Reports;

import com.netcracker.libra.model.ReportPOJOs;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author MorrisDeck
 */
public class ExcelReportPOJO {

        private String firstName;
        private String lastName;
        private String email;
        private String patronymic;
        private String phoneNumber;
        private int course;
        private Date dataCreation;

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPatronymic() {
            return patronymic;
        }

        public void setPatronymic(String patronymic) {
            this.patronymic = patronymic;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getCourse() {
            return course;
        }

        public void setCourse(int course) {
            this.course = course;
        }

        public Date getDataCreation() {
            return dataCreation;
        }

        public void setDataCreation(Date dataCreation) {
            this.dataCreation = dataCreation;
        }
        
        
        static class ExcelReportMapper implements RowMapper<ReportPOJOs.ExcelReportPOJO> {

        @Override
        public ReportPOJOs.ExcelReportPOJO mapRow(ResultSet rs, int i) throws SQLException {
            ReportPOJOs.ExcelReportPOJO obj = new ReportPOJOs.ExcelReportPOJO();
            obj.setFirstName(rs.getString("firstname"));
            obj.setLastName(rs.getString("lastname"));
            obj.setPatronymic(rs.getString("patronymic"));
            obj.setEmail(rs.getString("email"));
            obj.setCourse(rs.getInt("course"));
            obj.setPhoneNumber(rs.getString("phonenumber"));
            obj.setDataCreation(rs.getDate("datacreation"));
            return obj;
        }
    }
    }