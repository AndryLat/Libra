/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.model.VMTemplates.VMTemplateManager;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Class provides methods for manipulating the templates in the database
 * @author MorrisDeck
 */
@Repository
public class VMTemplateJDBC {
    private static JdbcTemplate jdbcTemplateObject;
    
    @Autowired
    public void setDataSource(DataSource dataSource) 
    {
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    
    /*
     * Insert new template
     */
    public static boolean InsertNewVMTemplate(String name, String content, String describe, String author){
        String SQL = "INSERT INTO VMTemplates "
                + "VALUES (VMTemplate_id.NEXTVAL,?,?,?,?)";
        if (jdbcTemplateObject.update(SQL, content, name, describe, author) != 0){
            return true;
        } else {
            return false;
        }

    }

    /**
     * THis method return content choosen template
     * @param name
     * @return 
     */
    public static String getContentVMTemplate(String name){
        String SQL = "SELECT content FROM VMTemplates WHERE t_name = ? ";
        return jdbcTemplateObject.queryForObject(SQL, String.class, name);
    }
    
    /**
     * This method return data about all Templates
     * @return 
     */
    public static List<VMTemplateManager> getVMTemplateData(){
        String SQL = "SELECT id, t_name, t_describe, author FROM VMTemplates";
        return jdbcTemplateObject.query(SQL, new VMTemplateManager.VMTemplateManagerMapper() );
    }
    
    /**
     * This method delete choosen template
     * @param name
     * @return 
     */
    public static int deleteVMTemplate(String name){
        String SQL = "DELETE VMTemplates WHERE t_name = ?";
        return jdbcTemplateObject.update(SQL, name);
    }
    
}
