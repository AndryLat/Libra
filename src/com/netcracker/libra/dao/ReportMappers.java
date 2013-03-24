/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.model.ReportPOJOs;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author MorrisDeck
 */
public class ReportMappers {
    /*
     * Mapper for students record report.
     */

    static class StudRecMapper implements RowMapper<ReportPOJOs.StudRecReportPOJO> {

        @Override
        public ReportPOJOs.StudRecReportPOJO mapRow(ResultSet rs, int i) throws SQLException {
            ReportPOJOs.StudRecReportPOJO obj = new ReportPOJOs.StudRecReportPOJO();
            return obj;
        }
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
