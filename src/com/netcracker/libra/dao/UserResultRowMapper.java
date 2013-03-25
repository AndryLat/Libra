package com.netcracker.libra.dao;

import com.netcracker.libra.model.UserResult;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Alexander Lebed
 */
public class UserResultRowMapper  implements RowMapper <UserResult> {
    @Override
    public UserResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserResult userResult = new UserResult();
        userResult.setUserId(rs.getInt("USERID"));
        userResult.setFirstName(rs.getString("FIRSTNAME"));
        userResult.setLastName(rs.getString("LASTNAME"));
        userResult.setRoleId(rs.getInt("ROLEID"));
        userResult.setMark(rs.getInt("MARK"));
        userResult.setComments(rs.getString("COMMENTS"));
        return userResult;
    }
    
}
