/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.model.Interview;
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

    public int exists(int interviewDateId,Long UserId,int role)
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
    
    public int exists0(Long UserId,int role)
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
    
    public Interview getInterview(int id)
    {
        String SQL = "select * from Interview where InterviewId =?";
        Interview interview = jdbcTemplateObject.queryForObject(SQL, new InterviewRowMapper(),id);      
        return interview;
    }
    
    public List<Interview> getAll()
    {
         String SQL = "select * from Interview order by InterviewId";
        List <Interview> interviews = jdbcTemplateObject.query(SQL, new InterviewRowMapper());
        return interviews;
    }
    
    private int getCurVal()
    {
        String sqlSeq = "select Interview_seq.NEXTVAL as Id from dual";
        return jdbcTemplateObject.queryForInt(sqlSeq);
    }
   
    public int add(int interviewDateId, Long UserId, int status)
    {
        int i=getCurVal();
        String SQL ="INSERT INTO Interview VALUES(?,?,"+
                "(Select appId from appform where UserId=?),"+
                "?)";
        jdbcTemplateObject.update(SQL,i,interviewDateId,UserId,status);
        return i;
    }
    
    public void updateRequest(int interviewDateId, Long UserId,int role)
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
    public void deleteRequest(Long userId,int role)
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
    
    public int getInterviewDateByAppId(Long userId,int role)
    {
        String sql="select  distinct i.InterviewDateId from interview i "
                + " join appform af on af.appid=i.appid "
                + " join interviewerList il on il.InterviewDateId=i.InterviewDateId "
                + " join users u on u.UserId=il.UserId "
                + " join roles r on r.roleId=u.roleId "
                + "where i.status=1 and af.userId=? and r.AccessLevel=?";
        return jdbcTemplateObject.queryForInt(sql,userId,role);
    }
        
    public String getRequestInterviewDate(Long userId,int role)
    {
        String SQL="select distinct 'Вы хотели перезаписатся ";
        if(role==1)// interview
          SQL+="на интервью с HR-ом ";   
        else
            SQL+="на техническое интервью ";
        SQL+="'||TO_CHAR(id.dateFinish,'dd.mm.yyyy')||' "
                + "на '||TO_CHAR(id.dateStart,'hh24:mi')||'-'|| TO_CHAR(id.dateFinish,'hh24:mi') "
                + "from Interview i join appForm af on af.appId=i.appId  "
                + "join InterviewDate id on id.InterviewDateId=i.InterviewDateId "
                + " join InterviewerList ilist on ilist.InterviewDateId=id.InterviewDateId "
                + " join users u on u.UserId=ilist.UserId "
                + " join roles r on r.roleId=u.roleId "
                + "where af.UserId=? and i.status=0 and r.AccessLevel=?";
        return (String)jdbcTemplateObject.queryForObject(SQL,String.class,userId,role);
    }
       
}
