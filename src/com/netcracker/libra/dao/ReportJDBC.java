package com.netcracker.libra.dao;

import com.netcracker.libra.model.Reports.AdvertisePOJO;
import com.netcracker.libra.model.Reports.ExcelReportPOJO;
import com.netcracker.libra.model.Reports.StudRecReportPOJO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * class contains queries to retrieve data for reports
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
     * Query to retrieve data for Excel report
     *
     * @return data for excel report
     */
    public static List<ExcelReportPOJO> getExcelReportData() {
        String SQL = "SELECT u.FirstName, u.LastName, a.Patronymic, u.email, a.phoneNumber,un.universityName, d.departmentName, f.facultyName,  a.course " +
"FROM users u, appform a, department d, faculty f, university un " +
"WHERE u.userid = a.userid AND a.departmentid = d.departmentid AND d.facultyid = f.facultyid AND f.universityid = un.universityid";
        List<ExcelReportPOJO> list = jdbcTemplate.query(SQL, new ExcelReportPOJO.ExcelReportMapper());
        return list;
    }

    /**
     * Query to retrieve data for the report "record students'
     *
     * @return objects with values date/value for students record report
     */
    public static List<StudRecReportPOJO> getStudRecValues() {
        String SQL = "select TO_CHAR(id.datefinish,'dd.mm.yyyy')||' '||TO_CHAR(id.datestart,'hh24:mi')||'-'||TO_CHAR(id.datefinish,'hh24:mi') iDate, count(appId) c "
                + "from interviewDate id  left  join interview i  "
                + "on i.interviewDateId=id.interviewDateId "
                + "where i.status=1 or i.status is null "
                + "group by id.datefinish, id.datestart "
                + "order by id.datestart ";
        List<StudRecReportPOJO> list = jdbcTemplate.query(SQL, new StudRecReportPOJO.StudRecMapper());
        return list;
    }

    /**
     * Query to retrieve data for the report, "The effectiveness of advertising"
     *
     * @return analyzing processes with the values ​​of "the name of
     * advertising" / "number"
     */
    public static List<AdvertisePOJO> getAdvertiseActivity() {
        String SQL = "select a.advertisingTitle, count(appId) "
                + "from advertising a left join appForm af "
                + "on af.advertisingId=a.advertisingId "
                + "group by a.advertisingTitle ";
        List<AdvertisePOJO> list = jdbcTemplate.query(SQL, new AdvertisePOJO.AdvertiseMapper());
        return list;

    }

    /**
     * The method returns the data for the report "Membership / Here"
     *
     * @return
     */
    public static Map getRegReportData() {
        Map map = new HashMap();

        String sql = "select count(appId) "
                + "from appForm ";
        int temp = jdbcTemplate.queryForInt(sql);
        map.put("value1", temp);

        sql = "select count(distinct i.appId)  "
                + "from interviewResults ir join interview i "
                + "on i.interviewId=ir.interviewId ";
        temp = jdbcTemplate.queryForInt(sql);
        map.put("value2", temp);

        return map;

    }

    /**
     * The method returns the data to the report, "was recorded / Here"
     *
     * @return
     */
    public static Map getStudentsActivityData() {
        Map map = new HashMap();

        String sql = "select count(distinct i.appId) "
                + "from  interview i "
                + "where i.status=1 ";
        int temp = jdbcTemplate.queryForInt(sql);
        map.put("value1", temp);

        sql = "select count(distinct i.appId)    "
                + "from interviewResults ir join interview i "
                + "on i.interviewId=ir.interviewId ";
        temp = jdbcTemplate.queryForInt(sql);
        map.put("value2", temp);

        return map;

    }
}
