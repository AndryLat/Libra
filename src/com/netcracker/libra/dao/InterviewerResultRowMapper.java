package com.netcracker.libra.dao;

import com.netcracker.libra.model.InterviewerResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Alexander Lebed
 */
public class InterviewerResultRowMapper  implements RowMapper <InterviewerResult> {
    @Override
    public InterviewerResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        InterviewerResult userResult = new InterviewerResult();
        userResult.setUserId(rs.getInt("USERID"));
        userResult.setFirstName(rs.getString("FIRSTNAME"));
        userResult.setLastName(rs.getString("LASTNAME"));
        userResult.setRoleId(rs.getInt("ROLEID"));
        userResult.setMark(rs.getInt("MARK"));
        userResult.setComments(rs.getString("COMMENTS"));
        return userResult;
    }
    
}
