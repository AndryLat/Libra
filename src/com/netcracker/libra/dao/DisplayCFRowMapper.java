/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.model.DisplayCF;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Sashenka
 */
public class DisplayCFRowMapper implements RowMapper<DisplayCF>
{
    public DisplayCF mapRow(ResultSet rs, int rowNum) throws SQLException 
   {
      DisplayCF columns = new DisplayCF();
      columns.setName(rs.getString("name"));
      columns.setValue(rs.getString("value"));
      columns.setLevel(rs.getInt("l"));
      return columns;
   }
}
