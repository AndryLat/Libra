package com.netcracker.libra.model.Reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * POJO object for advertising effectiveness report
 *
 * @author MorrisDeck
 */
public class AdvertisePOJO {

    private String name;
    private int value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * RowMapper for this POJO
     */
    public static class AdvertiseMapper implements RowMapper<AdvertisePOJO> {

        @Override
        public AdvertisePOJO mapRow(ResultSet rs, int i) throws SQLException {
            AdvertisePOJO obj = new AdvertisePOJO();
            obj.setName(rs.getString(1));
            obj.setValue(rs.getInt(2));
            return obj;
        }
    }
}
