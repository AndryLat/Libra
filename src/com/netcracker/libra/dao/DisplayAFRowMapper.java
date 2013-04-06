/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.model.DisplayAF;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Sashenka
 */
public class DisplayAFRowMapper implements RowMapper<DisplayAF>
{
    public DisplayAF mapRow(ResultSet rs, int rowNum) throws SQLException 
   {
      DisplayAF af = new DisplayAF();
      af.setAdvertisingComment(rs.getString("advertisingComment"));
      af.setAdvertisingName(rs.getString("advertisingTitle"));
      af.setAppId(rs.getInt("appId"));
      af.setCourse(rs.getInt("course"));
      af.setDepartmentName(rs.getString("departmentName"));
      af.setEmail(rs.getString("email"));
      af.setFacultyName(rs.getString("facultyName"));
      af.setFio(rs.getString("fio"));
     // af.setFirstName(rs.getString("firstName"));
      af.setGraduated(rs.getInt("graduated"));
     // af.setLastName(rs.getString("lastName"));
     // af.setPatronymic(rs.getString("patronymic"));
      af.setPhoneNumber(rs.getString("phoneNumber"));
      af.setUniversityName(rs.getString("universityName"));
      return af;
   }
}
