package com.netcracker.libra.model.Reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * POJO object for student's records report
 *
 * @author MorrisDeck
 */
public class StudRecReportPOJO {

    /**
     * record date for the interview
     */
    private String date;
    /**
     * number of recorded
     */
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

    /**
     * RowMapper for this POJO
     */
    public static class StudRecMapper implements RowMapper<StudRecReportPOJO> {

        @Override
        public StudRecReportPOJO mapRow(ResultSet rs, int i) throws SQLException {
            StudRecReportPOJO obj = new StudRecReportPOJO();
            obj.setDate(rs.getString(1));
            obj.setValue(rs.getInt(2));
            return obj;
        }
    }
}
