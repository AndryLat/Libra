/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

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
    
//    public static String getPathVMTemplate(String name){
//        String SQL = "SELECT path FROM VMTemplates WHERE t_name = ?";
//         return jdbcTemplateObject.queryForObject(SQL,String.class, name);
//    }
    
    public static String getContentVMTemplate(String name){
        String SQL = "SELECT content FROM VMTemplates WHERE t_name = ? ";
        return jdbcTemplateObject.queryForObject(SQL, String.class, name);
    }
    
}
