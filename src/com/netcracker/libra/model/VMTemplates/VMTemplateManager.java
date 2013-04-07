package com.netcracker.libra.model.VMTemplates;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * POJO object is used to display data in a table of all the templates
 * @author MorrisDeck
 */
public class VMTemplateManager {
    private int ID;
    private String name;
    private String describe;
    private String author;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    /**
     * RowMapper for VMTemplateManager POJO class
     */
    public static class VMTemplateManagerMapper implements RowMapper<VMTemplateManager>{

        @Override
        public VMTemplateManager mapRow(ResultSet rs, int i) throws SQLException {
            VMTemplateManager obj = new VMTemplateManager();
            obj.setID(rs.getInt("id"));
            obj.setName(rs.getString("t_name"));
            obj.setDescribe(rs.getString("t_describe"));
            obj.setAuthor(rs.getString("author"));
            return obj;
        }
        
    }
    
}
