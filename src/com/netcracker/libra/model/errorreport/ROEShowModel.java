/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model.errorreport;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * Use to display all error data
 * @author MorrisDeck
 */
public class ROEShowModel {
    private int id;
    private String text;
    private String author;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public static class ROEShowModelMapper implements RowMapper<ROEShowModel>{

        @Override
        public ROEShowModel mapRow(ResultSet rs, int i) throws SQLException {
            ROEShowModel obj = new ROEShowModel();
            
            obj.setId(rs.getInt("id"));
            obj.setText(rs.getString("text"));
            obj.setAuthor(rs.getString("username"));
            obj.setStatus(rs.getInt("status"));
            
            return obj;            
        }
        
    }
}
