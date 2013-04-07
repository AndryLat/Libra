package com.netcracker.libra.dao;

import com.netcracker.libra.model.errorreport.ROEShowModel;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author MorrisDeck
 */
@Repository
public class ReportOnErrorJDBC {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    /**
     * Insert new message about error
     * @param userName message authoe
     * @param text nature of the error
     * @return 
     */
    public static int insertReportOnError(String userName, String text){
        String SQL = "INSERT INTO ReportsOnErrors VALUES (ROEsequence.NEXTVAL,?,?,0)";
        return jdbcTemplate.update(SQL, text,userName);
    }
    
    /**
     * Return data about errors
     * @return 
     */
    public static List<ROEShowModel> getROEData(){
        String SQL = "SELECT id, text, userName, status FROM ReportsOnErrors";
        return jdbcTemplate.query(SQL, new ROEShowModel.ROEShowModelMapper());                
    }
    
    /**
     * Clear bug fixes
     * @return 
     */
    public static int fixDeleteROEError(){
        String SQL = "DELETE ReportsOnErrors WHERE status > 0";
        return jdbcTemplate.update(SQL);
    }
    
    /**
     * fixes a bug as fixed
     * @param id
     * @return 
     */
    public static int fixROEError( int id ){
        String SQL = "UPDATE ReportsOnErrors SET status = 1 WHERE id = ?";
        return jdbcTemplate.update(SQL, id);
    }
}
