/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author MorrisDeck
 */
// POJO objects for reports
public class ReportPOJOs {

    /**
     * POJO object for excel report data
     */
    public static class ExcelReportPOJO {

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
    }

    /**
     * POJO object for students record report's data
     */
    public static class StudRecReportPOJO {

        private String date;
        private int value;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    /**
     * POJO object for registration report's data
     */
    public static class RegReportPOJO {

        private int value1;
        private int value2;

        public int getValue1() {
            return value1;
        }

        public void setValue1(int value1) {
            this.value1 = value1;
        }

        public int getValue2() {
            return value2;
        }

        public void setValue2(int value2) {
            this.value2 = value2;
        }
    }
}
