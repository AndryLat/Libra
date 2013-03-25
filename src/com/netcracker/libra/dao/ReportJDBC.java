/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.dao.ReportMappers.ExcelReportMapper;
import com.netcracker.libra.dao.ReportMappers.StudRecMapper;
import com.netcracker.libra.model.ReportPOJOs;
import com.netcracker.libra.model.ReportPOJOs.StudRecReportPOJO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MorrisDeck
 */
@Repository
public class ReportJDBC {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     *
     * @return data for excel report
     */
    public static List<ReportPOJOs.ExcelReportPOJO> getExcelReportData() {
        String SQL = "SELECT u.firstname, u.lastname, a.patronymic, u.email, a.phonenumber, a.course, a.datacreation "
                + "FROM Users u, AppForm a "
                + "WHERE u.userid = a.userid";
        List<ReportPOJOs.ExcelReportPOJO> list = jdbcTemplate.query(SQL, new ExcelReportMapper());
        return list;
    }

    /**
     * return objects with values date/value for students record report
     *
     * @return
     */
    public static List<ReportPOJOs.StudRecReportPOJO> getStudRecValues() {
        String SQL = "Select * from users";
        List<ReportPOJOs.StudRecReportPOJO> list = jdbcTemplate.query(SQL, new StudRecMapper());


        return list;
    }

    public static Map getRegReportData() {
        Map map = new HashMap();

        String sql = "";
        int temp = jdbcTemplate.queryForInt(sql);
        map.put("value1", temp);

        sql = "";
        temp = jdbcTemplate.queryForInt(sql);
        map.put("value2", temp);

        return map;

    }

    public static Map getStudentsActivityData() {
        Map map = new HashMap();

        String sql = "";
        int temp = jdbcTemplate.queryForInt(sql);
        map.put("value1", temp);

        sql = "";
        temp = jdbcTemplate.queryForInt(sql);
        map.put("value2", temp);

        return map;

    }
}
