package com.netcracker.libra.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.netcracker.libra.model.Student;
import com.netcracker.libra.util.security.Security;
import com.netcracker.libra.util.security.SessionToken;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

@Repository
public class StudentJDBC {

   private static JdbcTemplate jdbcTemplateObject;
   //private static int userId = 300;
   private final int roleId = 1;
   
   public StudentJDBC() {
	   
   }
   
   @Autowired
   public void setDataSource(DataSource dataSource) {
       jdbcTemplateObject = new JdbcTemplate(dataSource);
   }
   
   private int getCurrVal()
   {
       String SQL="select User_seq.NEXTVAL as Id from dual";
       return jdbcTemplateObject.queryForInt(SQL);
   }
   
   public void create(String name, String lastName, String email, String password) {
      String SQL = "insert into Users (USERID, FIRSTNAME, LASTNAME, EMAIL, PASSWORD, ROLEID) values (?,?,?,?,?,?)";
      int userId=getCurrVal();
      jdbcTemplateObject.update(SQL, userId, name, lastName, email, password, roleId);
      System.out.println("Created Record Name = " + name + " Lastname = " + lastName + " Id = " +userId);
      return;
   }

   public Student getStudent(Integer id) {
      String SQL = "select * from Users where userid = ?";
      Student student = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{id}, new StudentRowMapper());
      return student;
   }

   public List<Student> listStudents() {
      String SQL = "select * from Users";
      List <Student> students = jdbcTemplateObject.query(SQL, new StudentRowMapper());
      return students;
   }

   public void delete(Integer id){
      String SQL = "delete from Users where id = ?)";
      jdbcTemplateObject.update(SQL, id);
      System.out.println("Deleted Record with ID = " + id );
      return;
   }

   public void update(Integer id, String name, String lastName, String email, String password) {
      String SQL = "update Users set firstname = ?, lastname = ?, email = ?, password = ? where userid = ?";
      jdbcTemplateObject.update(SQL, name, lastName, email, password, id);
      System.out.println("Updated Record with ID = " + id );
      return;
   }
   
   public static SessionToken verifyLogin(String email, String password) throws EmptyResultDataAccessException  {
	   String SQL = "select userid, accesslevel from users natural join roles where email=? and password=?";
	   Map result = jdbcTemplateObject.queryForMap(SQL, email, Security.getMD5hash(password));
	   return new SessionToken(((BigDecimal)result.get("userid")).intValueExact(), email, ((BigDecimal)result.get("accessLevel")).intValueExact());
   }
   public static int getAccess(Long id)
   {
        String SQL = "select r.AccessLevel from users u join roles r on r.roleId=u.roleId where u.userId=?";
	   return  jdbcTemplateObject.queryForInt(SQL, id);
   }
   
   public static boolean isAppformExist(Long id)
   {
	   if (jdbcTemplateObject.queryForInt("select templateid from appform where userid=?", id)==0)
	   		return false;
	   else
	   		return true;
   }
   public static int exists(Integer userId,String p)
   {
       String SQL="select count(*) from users "
               + "where userId=? and password=? ";
       return jdbcTemplateObject.queryForInt(SQL,userId,p);
   }
   
   public static void updatePassword(Integer userId, String password) 
   {
      String SQL = "update Users set password = ? where userid = ?";
      jdbcTemplateObject.update(SQL, password,userId);
   }
   
   public Integer getAppIdByUserId(int userId)
   {
       String SQL="Select AppId from appForm where userId=?";
       try
       {
        return jdbcTemplateObject.queryForInt(SQL,userId);
       }
       catch(Exception e)
       {
           return -1;
       }
   }
}
