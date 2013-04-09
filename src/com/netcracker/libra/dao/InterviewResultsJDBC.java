/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import com.netcracker.libra.model.InterviewResult;
import com.netcracker.libra.model.InterviewResultsInfo;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Sashenka
 */
@Repository
public class InterviewResultsJDBC  implements InterviewResultsDAO
{
    private static JdbcTemplate jdbcTemplateObject;
    
    @Autowired
    public void setDataSource(DataSource dataSource) 
    {
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
     
    public List<InterviewResult> getResult(int appId)
    {
        String SQL="select r.title||': '||u.lastName||' '||u.firstName fio, ir.Mark, ir.comments, ir.userId  "+
                    "from interviewResults ir join "+
                    "users u on u.userId=ir.userId "+
                    "join roles r on r.roleId=u.roleId "
                    +"join interview i on i.interviewId=ir.interviewId "+
                    "where i.appId=?";
        return jdbcTemplateObject.query(SQL, new InterviewResultRowMapper(),appId);
    }
    public int exists(int appId)
    {
        String SQL="select count(*) from appForm where appId=?";
        return jdbcTemplateObject.queryForInt(SQL,appId);
    }
    public int existsComment(int userId, int appId)
    {
        String SQL="select count(*) from interviewResults ir "
                + "join interview i on i.interviewId=ir.interviewId "
                + "where ir.userId=? and i.appId=?";
        return jdbcTemplateObject.queryForInt(SQL,userId,appId);
    }
    public int countPage(int count)
    {
        String SQL="select  CASE WHEN count(distinct i.appId)/? - floor(count(distinct i.appId)/?)>0 THEN  floor(count(distinct i.appId)/?+1) "
                    +"else count(distinct i.appId)/? "
                    +"end "                
                    +"from  interview i where i.status=1";
        return jdbcTemplateObject.queryForInt(SQL,count,count,count,count);
    }
    public List<InterviewResultsInfo> getAllInfo(String order,Boolean desc)
    {
        String SQL = "select u.lastName||' '||u.firstname||' '||af.patronymic fio, "
	+"af.appId, round(nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0),3) avgMark  ,  u.email,  "
                + "ROW_NUMBER()   OVER ( ORDER BY";
        if(order!=null)
        { 
            if(order.equalsIgnoreCase("results"))
            {
                SQL+= "  nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0)   ";
            }
            else
            {
                if(order.equalsIgnoreCase("appId"))
                {
                    SQL+= "  af.appId  ";
                }
                else
                {
                    if(order.equalsIgnoreCase("lastname"))
                    {
                        SQL+= "  u.lastName ";
                    }
                    else
                    {
                        if(order.equalsIgnoreCase("email"))
                        {
                            SQL+= " u.email ";
                        }
                    }
                }
            }   
        }
        if(desc)
            SQL+="desc";
        else 
            SQL+="asc";
        SQL+=") r  "
                + "from users u join appForm af on af.UserId=u.UserId "
        +"right join "
        +"(select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
        +"from interview i "
        +"join interviewerList ilHR on ilHR.interviewdateId=i.interviewdateId "
        +"join users uHR on uHR.userId=ilHR.userId "
        +"join roles rHR on rHR.roleId=uHR.roleId "
        +"left join interviewResults ir on ir.InterviewId=i.interviewId "
        +"where i.status=1 and rHR.accessLevel=1 "
        +"group by i.appId , i.interviewid ) HR "
        +"on HR.appId=af.AppId "
        +"full outer join "
        +"(select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
        +"from interview i "
        +"join interviewerList ili on ili.interviewdateId=i.interviewdateId "
        +"join users ui on ui.userId=ili.userId "
        +"join roles ri on ri.roleId=ui.roleId "
        +"left join interviewResults ir on ir.InterviewId=i.interviewId "
        +"where i.status=1 and ri.accessLevel=2 "
        +"group by i.appId , i.interviewid ) Interv on Interv.appId=HR.appId ";
        List<InterviewResultsInfo> interview = jdbcTemplateObject.query(SQL, new InterviewResultInfoRowMapper());      
        return interview;
    }
    public List<InterviewResultsInfo> getInfo(String order, Boolean desc, int start, int finish)
    {
        String SQL = "select  "
	+"appId, round(avgMark,3) avgMark, fio, r, email "
        +"from("
                + "select u.lastName||' '||u.firstname||' '||af.patronymic fio, "
	+"af.appId, nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0) avgMark  , u.email,  "
                  + "ROW_NUMBER()   OVER ( ORDER BY";
        if(order.equalsIgnoreCase("results"))
        {
            SQL+= "  nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0)   ";
        }
        else
        {
            if(order.equalsIgnoreCase("appId"))
            {
                SQL+= "  af.appId  ";
            }
            else
            {
                if(order.equalsIgnoreCase("lastname"))
                {
                    SQL+= "  u.lastName ";
                }
                else
                {
                    if(order.equalsIgnoreCase("email"))
                    {
                        SQL+= " u.email ";
                    }
                }
            }
        }  
        if(desc)
            SQL+="desc";
        else 
            SQL+="asc";
        SQL+=", hr.appId) r  "
        +"from users u join appForm af on af.UserId=u.UserId "
        +"right join "
        +"(select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
        +"from interview i "
        +"join interviewerList ilHR on ilHR.interviewdateId=i.interviewdateId "
        +"join users uHR on uHR.userId=ilHR.userId "
        +"join roles rHR on rHR.roleId=uHR.roleId "
        +"left join interviewResults ir on ir.InterviewId=i.interviewId "
        +"where i.status=1 and rHR.accessLevel=1 "
        +"group by i.appId , i.interviewid ) HR "
        +"on HR.appId=af.AppId "
        +"full outer join "
        +"(select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
        +"from interview i "
        +"join interviewerList ili on ili.interviewdateId=i.interviewdateId "
        +"join users ui on ui.userId=ili.userId "
        +"join roles ri on ri.roleId=ui.roleId "
        +"left join interviewResults ir on ir.InterviewId=i.interviewId "
        +"where i.status=1 and ri.accessLevel=2 "
        +"group by i.appId , i.interviewid ) Interv on Interv.appId=HR.appId "
        +")"
        + "where r  BETWEEN ? AND ?";
        List<InterviewResultsInfo> interview = jdbcTemplateObject.query(SQL, new InterviewResultInfoRowMapper(),start,finish);      
        return interview;
    }
    
    public List<InterviewResultsInfo> serch(String[] serchString)
    {
        String SQL = 
                "select  appId, round(avgMark,3) avgMark, fio, r, email "
        +"from "
	+"(select u.lastName||' '||u.firstname||' '||af.patronymic fio, "
			+"u.email, af.appId, nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0) avgMark  ,    "
			+"ROW_NUMBER()   OVER ( ORDER BY nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0) desc) r  "
			+"from users u join appForm af on af.UserId=u.UserId "
			+"right join "
				+"( "
					+"select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
					+"from interview i "
					+"join appForm af on af.AppId=i.AppId "
					+"join users u on af.UserId=u.UserId "
					+"join interviewerList ilHR on ilHR.interviewdateId=i.interviewdateId "
					+"join users uHR on uHR.userId=ilHR.userId "
					+"join roles rHR on rHR.roleId=uHR.roleId "
					+"left join interviewResults ir on ir.InterviewId=i.interviewId "
					+"where i.status=1 and rHR.accessLevel=1 and (";
                                        for(int i=0;i<serchString.length-1;i++)
                                        {
                                            SQL+="UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email) LIKE '%'||UPPER('"+serchString[i]+"')||'%' or ";
                                           // SQL+="regexp_like(UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email),Trim(UPPER('"+serchString[i]+"'))) or ";
                                        }
                                        SQL+="UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email) LIKE '%'||UPPER('"+serchString[serchString.length-1]+"')||'%') ";  
                                       // SQL+="regexp_like(UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email),Trim(UPPER('"+serchString[serchString.length-1]+"'))))";						
                                        SQL+="group by i.appId , i.interviewid, u.firstname,af.patronymic,u.lastName,af.appId,u.email  "
				+") HR on HR.appId=af.AppId full "
				+"outer join "
				+"( "
					+"select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
					+"from interview i "
					+"join appForm af on af.AppId=i.AppId "
					+"join users u on af.UserId=u.UserId  "
					+"join interviewerList ili on ili.interviewdateId=i.interviewdateId "
					+"join users ui on ui.userId=ili.userId "
					+"join roles ri on ri.roleId=ui.roleId "
					+"left join interviewResults ir on ir.InterviewId=i.interviewId "
					+"where i.status=1 and ri.accessLevel=2 and (" ;
                                        for(int i=0;i<serchString.length-1;i++)
                                        {
                                            SQL+="UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email) LIKE '%'||UPPER('"+serchString[i]+"')||'%' or ";
                                           // SQL+="regexp_like(UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email),Trim(UPPER('"+serchString[i]+"'))) or ";
                                        }
                                        SQL+="UPPER(u.firstname||af.patronymic||u.lastName||af.appId||u.email) LIKE '%'||UPPER('"+serchString[serchString.length-1]+"')||'%') ";  
                                        SQL+="group by i.appId , i.interviewid, u.firstname,af.patronymic,u.lastName,af.appId,u.email   "
				+") Interv on Interv.appId=HR.appId "
	+")";
                /*"select  "
	+"appId, round(avgMark,3) avgMark, fio, r, email "
        +"from("
                + "select u.firstname||' '||u.lastName||' '||af.patronymic fio, u.email, "
	+"af.appId, nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0) avgMark  ,   "
        +"u.firstname||af.patronymic||u.lastName||af.appId||u.email serchString, "    
        + " ROW_NUMBER()   OVER ( ORDER BY nvl(Interv.avgMark,0)+ nvl(HR.avgMark,0) desc) r  "
        +"from users u join appForm af on af.UserId=u.UserId "
        +"right join "
        +"(select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
        +"from interview i "
        +"join interviewerList ilHR on ilHR.interviewdateId=i.interviewdateId "
        +"join users uHR on uHR.userId=ilHR.userId "
        +"join roles rHR on rHR.roleId=uHR.roleId "
        +"left join interviewResults ir on ir.InterviewId=i.interviewId "
        +"where i.status=1 and rHR.accessLevel=1 "
        +"group by i.appId , i.interviewid ) HR "
        +"on HR.appId=af.AppId "
        +"full outer join "
        +"(select i.appId , i.interviewid , avg(nvl(ir.mark,0)) avgMark  "
        +"from interview i "
        +"join interviewerList ili on ili.interviewdateId=i.interviewdateId "
        +"join users ui on ui.userId=ili.userId "
        +"join roles ri on ri.roleId=ui.roleId "
        +"left join interviewResults ir on ir.InterviewId=i.interviewId "
        +"where i.status=1 and ri.accessLevel=2 "
        +"group by i.appId , i.interviewid ) Interv on Interv.appId=HR.appId "
        +")"
        + "where ";
                for(int i=0;i<serchString.length-1;i++)
                {
                    SQL+="regexp_like(UPPER(serchString),Trim(UPPER('"+serchString[i]+"'))) or ";
                }
                SQL+="regexp_like(UPPER(serchString),Trim(UPPER('"+serchString[serchString.length-1]+"')))";
        */
        List<InterviewResultsInfo> interview = jdbcTemplateObject.query(SQL, new InterviewResultInfoRowMapper());      
        return interview;
    }
    @Override
    public void addResult(int appId,int accessLevel,int UserId,int mark, String comments)
    {
        String SQL ="INSERT INTO InterviewResults "
                + "VALUES("
                + "(select distinct i.interviewId from " 
                +"interview i join interviewDate id on id.InterviewDateId=i.InterviewDateId "
                +"join interviewerList il on il.InterviewDateId=id.InterviewDateId "
                +"join users u on u.userId=il.userId "
                +"join roles r on r.roleId=u.roleId "
                +"where i.appId=? and i.status=1 and r.accessLevel=?),"
                + "?,"
                + "?,"
                + "?)";
        jdbcTemplateObject.update(SQL,appId,accessLevel,UserId,mark,comments);
    }
    
    @Override
    public void updateResult(int appId,int UserId,int mark, String comments)
    {
        String SQL ="Update InterviewResults set mark=?, comments=? where interviewId="
                 + "(select ir.interviewId from InterviewResults ir "
                +"join interview i on i.interviewId=ir.interviewId "
                +"where ir.UserId=? and i.appId=?)"
                +" and userId=?";
        jdbcTemplateObject.update(SQL,mark,comments,UserId,appId,UserId);
    }
    public void deleteResult(int appId,int UserId)
    {
        String SQL ="delete InterviewResults "
                +"where InterviewId in("
                +"select InterviewId from Interview where appId=? "
                +") and userId=?";
        jdbcTemplateObject.update(SQL,appId,UserId);
    }
}
