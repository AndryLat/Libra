/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao.oldNew;

import com.netcracker.libra.model.ApplicationChange;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Alexander Lebed
 */
public class OldNewPhoneNumberRowMapper implements RowMapper {
    
    @Override
    public ApplicationChange mapRow(ResultSet rs, int i) throws SQLException {
        
        ApplicationChange obj = new ApplicationChange();
        obj.setAppId(rs.getInt("appId"));
        obj.setFirstName(rs.getString("firstName"));
        obj.setLastName(rs.getString("lastName"));
        obj.setEmail(rs.getString("email"));
        obj.setFieldName("Телефон");
        obj.setOldValue(rs.getString("oldValue"));
        obj.setNewValue(rs.getString("newValue"));
        obj.setOldId(rs.getInt("oldId"));
        obj.setNewId(rs.getInt("newId"));
        obj.setColumnName("phoneNumber");
        return obj;
    }
    
}
