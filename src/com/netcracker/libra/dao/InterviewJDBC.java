/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Сашенька
 */
@Repository
public class InterviewJDBC 
{
    private static JdbcTemplate jdbcTemplateObject;
    
    @Autowired
    public void setDataSource(DataSource dataSource) 
    {
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }

    public int exists(int interviewDateId,int UserId,int role)
    {
         String sql = "select Count(*) from Interview i "
                 + " join appForm af on af.appId=i.appId "
                 + " join interviewDate id on id.InterviewDateId=i.InterviewDateId "
                 + " join interviewerList il on il.InterviewDateId=id.InterviewDateId "
                 + " join Users u on u.UserId=il.UserId"
                 + " join Roles r on u.RoleId=r.roleId "
                 + " where af.UserId=? and i.interviewDateId=? and i.status=1 and r.AccessLevel=?";
        return jdbcTemplateObject.queryForInt(sql,UserId,interviewDateId,role);
    }
    
    public int getRole(int request)
    {
         String sql = "select Distinct r.AccessLevel from  "
                 + " interviewerList il "
                 + " join Users u on u.UserId=il.UserId "
                 + " join Roles r on u.RoleId=r.roleId "
                 + " where r.AccessLevel!=3 and il.InterviewDateId=?";
        return jdbcTemplateObject.queryForInt(sql,request);
    }
    
    public int exists0(int UserId,int role)
    {
         String sql = "select Count(*) from Interview i "
                 + " join appForm af on af.appId=i.appId "
                 + " join interviewDate id on id.InterviewDateId=i.InterviewDateId "
                 + " join interviewerList il on il.InterviewDateId=id.InterviewDateId "
                 + " join Users u on u.UserId=il.UserId"
                 + " join Roles r on u.RoleId=r.roleId "
                 + " where af.UserId=? and i.status=0 and r.AccessLevel=? ";
        return jdbcTemplateObject.queryForInt(sql,UserId,role);
    }

    private int getCurVal()
    {
        String sqlSeq = "select Interview_seq.NEXTVAL as Id from dual";
        return jdbcTemplateObject.queryForInt(sqlSeq);
    }
   
    public int add(int interviewDateId, int UserId, int status)
    {
        int i=getCurVal();
        String SQL ="INSERT INTO Interview VALUES(?,?,"+
                "(Select appId from appform where UserId=?),"+
                "?)";
        jdbcTemplateObject.update(SQL,i,interviewDateId,UserId,status);
        return i;
    }
    
    public void updateRequest(int interviewDateId, int UserId,int role)
    {
            String   SQL="Update Interview set InterviewDateId=? "
                    + " where appId=(select appId from appForm where UserId=?) and status=0"
                    + " and interviewdateId in"
                    + " (select id.InterviewdateId "
                    + " from interviewDate id "
                    + " join InterviewerList il "
                    + " on il.interviewdateId=id.interviewdateId "
                    + " join users u on u.userId=il.UserId "
                    + " join roles r on r.roleId=u.roleId "
                    + " where r.AccessLevel=?"
                    + ")";
            jdbcTemplateObject.update(SQL,interviewDateId,UserId,role);
    }
    public void deleteRequest(int userId,int role)
    {
        String SQL="delete from interview "
                + " where appId=(select appId from appForm where UserId=?) "
                + " and status=0 "
                + " and interviewdateId in"
                + " (select id.InterviewdateId "
                + " from interviewDate id "
                + " join InterviewerList il "
                + " on il.interviewdateId=id.interviewdateId "
                + " join users u on u.userId=il.UserId "
                + " join roles r on r.roleId=u.roleId "
                + " where r.AccessLevel=?"
                + ")";
        jdbcTemplateObject.update(SQL, userId,role);
    }
    
    public int getInterviewDateByAppId(int userId,int role)
    {
        String sql="select  distinct i.InterviewDateId from interview i "
                + " join appform af on af.appid=i.appid "
                + " join interviewerList il on il.InterviewDateId=i.InterviewDateId "
                + " join users u on u.UserId=il.UserId "
                + " join roles r on r.roleId=u.roleId "
                + "where i.status=1 and af.userId=? and r.AccessLevel=?";
        return jdbcTemplateObject.queryForInt(sql,userId,role);
    }
        
    public Integer getRequestInterviewDate(int userId,int role)
    {
        String SQL="select distinct i.InterviewDateId "
                + "from Interview i join appForm af on af.appId=i.appId  "
                + "join InterviewDate id on id.InterviewDateId=i.InterviewDateId "
                + " join InterviewerList ilist on ilist.InterviewDateId=id.InterviewDateId "
                + " join users u on u.UserId=ilist.UserId "
                + " join roles r on r.roleId=u.roleId "
                + "where af.UserId=? and i.status=0 and r.AccessLevel=?";
        return jdbcTemplateObject.queryForInt(SQL,userId,role);
    }
    public String getTime()
    {
        String SQL="select to_char(SYSDATE+NVL((select distinct TIMEZONEDIFFERENCE from libraconfigs),0)/24, 'yyyy/mm/dd HH24:mi') from dual";
        return jdbcTemplateObject.queryForObject(SQL, String.class);
    }

    public String getServerTime()
    {
        String SQL="select to_char(SYSDATE, 'yyyy/mm/dd HH24:mi:ss') from dual";
        return jdbcTemplateObject.queryForObject(SQL, String.class);
    }
    public void updateTimeZone(int diff)
    {
        String SQL="MERGE INTO libraconfigs l1 "
                    +"USING (select count(*) c from libraconfigs) l2 "
                    +"ON (l2.c>0) "
                    +"WHEN MATCHED THEN "
                    +"UPDATE SET l1.TIMEZONEDIFFERENCE = ? " 
                    +" WHEN NOT MATCHED THEN "
                    +"INSERT (l1.TIMEZONEDIFFERENCE) "
                    +"VALUES (?)";
        jdbcTemplateObject.update(SQL,diff,diff);
    }
    
    public String getInterviewForApp(int accessLevel,int appId)
    {
        String SQL="select DISTINCT DECODE(?,1,'Hr собеседование:','Техническое собеседование:')||TO_CHAR(id.datefinish,' dd.mm.yyyy ') ||  TO_CHAR(id.datestart,'hh24:mi')||'-'||TO_CHAR(id.datefinish,'hh24:mi') hTime "
	+"from interview i "
	+"join interviewdate id on id.interviewDateId=i.interviewDateId "
	+"join interviewerList il on il.interviewDateId=id.interviewDateId "
	+"join users u on u.UserId=il.userId "
	+"join roles r on r.roleId=u.roleId "
	+"where i.appId=? and i.status=1 and r.accessLevel=?";
        return jdbcTemplateObject.queryForObject(SQL, String.class,accessLevel,appId,accessLevel);
    }
}
