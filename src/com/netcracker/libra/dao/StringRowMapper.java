package com.netcracker.libra.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author Alexander Lebed
 */
public class StringRowMapper implements RowMapper <String> {
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString(1);
    }
    
}
