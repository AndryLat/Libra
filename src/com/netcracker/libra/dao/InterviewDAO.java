/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import java.util.List;
import javax.sql.DataSource;

/**
 *
 * @author Sashenka
 */
public interface InterviewDAO 
{
    public void setDataSource(DataSource dataSource);
    public int add(int interviewDateId, int UserId, int status);
    public int getInterviewDateByAppId(int userId);
    public String getRequestInterviewDate(int userId);
}
