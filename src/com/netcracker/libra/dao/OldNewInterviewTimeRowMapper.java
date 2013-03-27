package com.netcracker.libra.dao;

import com.netcracker.libra.model.OldNewInterviewTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Alexander Lebed
 */
public class OldNewInterviewTimeRowMapper implements RowMapper {

    @Override
    public OldNewInterviewTime mapRow(ResultSet rs, int i) throws SQLException {
        
        OldNewInterviewTime obj = new OldNewInterviewTime();
        obj.setAppId(rs.getInt("appId"));
        obj.setFirstName(rs.getString("firstName"));
        obj.setLastName(rs.getString("lastName"));
        obj.setEmail(rs.getString("email"));
        obj.setOldTimeInterview(rs.getString("oldTimeInterview"));
        obj.setOldDateInterview(rs.getString("oldDateInterview"));
        obj.setNewTimeInterview(rs.getString("newTimeInterview"));
        obj.setNewDateInterview(rs.getString("newDateInterview"));
        obj.setOldInterviewId(rs.getInt("oldInterviewId"));
        obj.setNewInterviewId(rs.getInt("newInterviewId"));
        return obj;
    }
    
}
