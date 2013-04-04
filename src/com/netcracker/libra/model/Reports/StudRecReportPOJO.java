/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model.Reports;

import com.netcracker.libra.model.ReportPOJOs;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author MorrisDeck
 */
public class StudRecReportPOJO {

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
        
        static class StudRecMapper implements RowMapper<ReportPOJOs.StudRecReportPOJO> {

        @Override
        public ReportPOJOs.StudRecReportPOJO mapRow(ResultSet rs, int i) throws SQLException {
            ReportPOJOs.StudRecReportPOJO obj = new ReportPOJOs.StudRecReportPOJO();
            return obj;
        }
    }
        
}
