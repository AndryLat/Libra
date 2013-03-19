package com.netcracker.libra.dao;

import com.netcracker.libra.model.InterviewDate;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

public interface InterviewDateDAO {
    
    void setDataSource(DataSource dataSource);
    int createInterviewDate(String startDateAndTime, String finishDateAndTime, Integer duration);
    void updateInterviewDateByDateId(Integer appId, String startDateAndTime, 
                                                   String finishDateAndTime, Integer duration);
    void deleteInterviewDateByAppId(Integer appId);
    void addExtraTimeByAppId(Integer appId, Integer minutes);
    List<Map<String, Object>> getInterviewDateById(Integer Id);
    List <InterviewDate> getAllInterviewDates();
    void insertInterviewers(Integer userId);
    
    
}
