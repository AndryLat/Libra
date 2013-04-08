package com.netcracker.libra.dao;

import com.netcracker.libra.model.User;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * The class is used for output from a database of employees (HR, Tech.interviewer, Administrator),
 * edit, delete their data and addition a new employees
 * 
 * @author Alexander Lebed
 */
@Repository
public class AdminJDBC implements AdminDAO {
    
        //it simplifies the use of JDBC and helps to avoid common errors
        private static JdbcTemplate jdbcTemplateObject;

	@Override
	public void create(String name, String lastName, String email,
			String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Integer id, String name, String lastName, String email,
			String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}
        
	@Override
	@Autowired
	public void setDataSource(DataSource dataSource) {
            jdbcTemplateObject = new JdbcTemplate(dataSource);
	}
        
        /**
         * Returns an employee by ID
         */
        public User getEmployee(Integer employeeId) {
            String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE UserId = ?";
            User employee = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{employeeId}, new UserRowMapper());
            return employee;
        }
        
        /**
         * Returns an employee by email
         */
        public User getEmployee(String email) {
            String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE email = ?";
            User employee = jdbcTemplateObject.queryForObject(SQL, 
                        new Object[]{email}, new UserRowMapper());
            return employee;
        }
        
        /**
         * Returns a List of all employees
         */
        public List <User> getAllEmployees() {
            String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 ORDER BY RoleId";
            List <User> employees = jdbcTemplateObject.query(SQL, new UserRowMapper());
            return employees;
        }
        
        /**
         * Returns a List of employees by job title
         * @param role - job title (2 - HR, 3 - Tech.interviewer, 4 - Admin)
         */
        public List <User> getAllEmployeesByRole(Integer role) {
            String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId = ? ORDER BY firstName";
            List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {role}, new UserRowMapper());
            return employees;
        }
        
        /**
         * Returns a List of employees with a particular full name;
         * if the full name is missing - return a List of all employees in ascending order by a first name
         * @param fullName the full name like "John Lennon"
         */
        public List <User> getAllEmployeesByFullName(String fullName) {
            
            if(!fullName.isEmpty()) {
                String [] names = fullName.split(" ");
                String firstName = "";
                String lastName = "";
                try {
                    firstName = names[0];
                    lastName = names[1];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nexception: " + e.toString());
                }
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 AND upper(firstName) LIKE upper(?) AND upper(lastName) LIKE upper(?)";
                Object [] params = new Object[] {"%"+firstName+"%", "%"+lastName+"%"};
                List <User> employees = jdbcTemplateObject.query(SQL, params, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 ORDER BY firstName";
                List <User> employees = jdbcTemplateObject.query(SQL, new UserRowMapper());
                return employees;
            }
        }
        
        /**
         * Returns a List of employees with a particular full name and a job title;
         * if the full name is missing - return a List of all employees with the entered job title 
         * in ascending order by a first name
         * @param fullName the full name like "John Lennon"
         * @param role the job title (2 - HR, 3 - Tech.interviewer, 4 - Admin)
         */
        public List <User> getAllEmployeesByFullNameAndRole(String fullName, Integer role) {
            
            if(!fullName.isEmpty()) {
                String [] names = fullName.split(" ");
                String firstName = "";
                String lastName = "";
                try {
                    firstName = names[0];
                    lastName = names[1];
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nexception: " + e.toString());
                }
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE upper(firstName) LIKE upper(?) AND upper(lastName) LIKE upper(?) AND RoleId = ?";
                Object [] params = new Object[] {"%"+firstName+"%", "%"+lastName+"%", role};
                List <User> employees = jdbcTemplateObject.query(SQL, params, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId = ? ORDER BY firstName";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {role}, new UserRowMapper());
                return employees;
            }
        }

        /**
         * Returns a List of employees with a particular first name;
         * if the first name is missing - return a List of all employees in ascending order by a first name
         */
        public List <User> getAllEmployeesByFirstName(String firstName) {
            
            if(!firstName.isEmpty()) {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 AND upper(firstName) LIKE upper(?)";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {"%"+firstName+"%"}, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 ORDER BY firstName";
                List <User> employees = jdbcTemplateObject.query(SQL, new UserRowMapper());
                return employees;
            } 
        }
        
        /**
         * Returns a List of employees with a particular first name and a job title;
         * if the full name is missing - return a List of all employees with the entered job title 
         * in ascending order by a first name
         * @param role the job title (2 - HR, 3 - Tech.interviewer, 4 - Admin)
         */
        public List <User> getAllEmployeesByFirstNameAndRole(String firstName, Integer role) {
            
            if(!firstName.isEmpty()) {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE upper(firstName) LIKE upper(?) AND RoleId = ?";
                Object [] params = new Object[] {"%"+firstName+"%", role};
                List <User> employees = jdbcTemplateObject.query(SQL, params, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId = ? ORDER BY firstName";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {role}, new UserRowMapper());
                return employees;
            }
        }
        
        /**
         * Returns a List of employees with a particular last name;
         * if the last name is missing - return a List of all employees in ascending order by a last name
         */
        public List <User> getAllEmployeesByLastName(String lastName) {
            
            if(!lastName.isEmpty()) {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 AND upper(lastName) LIKE upper(?)";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {"%"+lastName+"%"}, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 ORDER BY lastName";
                List <User> employees = jdbcTemplateObject.query(SQL, new UserRowMapper());
                return employees;
            }
        }
        
        /**
         * Returns a List of employees with a particular last name and a job title;
         * if the last name is missing - return a List of all employees in ascending order by a last name
         * and with a particular job title
         * @param role the job title (2 - HR, 3 - Tech.interviewer, 4 - Admin)
         */
        public List <User> getAllEmployeesByLastNameAndRole(String lastName, Integer role) {
            
            if(!lastName.isEmpty()) {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE upper(lastName) LIKE upper(?) AND RoleId = ?";
                Object [] params = new Object[] {"%"+lastName+"%", role};
                List <User> employees = jdbcTemplateObject.query(SQL, params, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId = ? ORDER BY lastName";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {role}, new UserRowMapper());
                return employees;
            }
        }
        
        /**
         * Returns a List of employees with a particular email;
         * if the email is missing - return a List of all employees in ascending order by a email
         */
        public List <User> getAllEmployeesByEmail(String email) {
            
            if(!email.isEmpty()) {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 AND upper(email) LIKE upper(?)";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {"%"+email+"%"}, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId > 1 ORDER BY email";
                List <User> employees = jdbcTemplateObject.query(SQL, new UserRowMapper());
                return employees;
            }
        }
        
        /**
         * Returns a List of employees with a particular email and a job title;
         * if the email is missing - return a List of all employees in ascending order by a email
         * and with a particular job title
         * @param role the job title (2 - HR, 3 - Tech.interviewer, 4 - Admin)
         */
        public List <User> getAllEmployeesByEmailAndRole(String email, Integer role) {
            
            if(!email.isEmpty()) {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE upper(email) LIKE upper(?) AND RoleId = ?";
                Object [] params = new Object[] {"%"+email+"%", role};
                List <User> employees = jdbcTemplateObject.query(SQL, params, new UserRowMapper());
                return employees;
            }
            else {
                String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE RoleId = ? ORDER BY email";
                List <User> employees = jdbcTemplateObject.query(SQL, new Object[] {role}, new UserRowMapper());
                return employees;
            }
        }
        
        /**
         * Replace an employee's data by his or her ID
         */
        public void updateEmployee(Integer employeeId, String firstName, String lastName, 
                                        String email, Integer roleId) {
            String SQL = "UPDATE Users SET firstName = ?, lastName = ?,"+
                         "email = ?, roleId = ? WHERE userId = ?";
            jdbcTemplateObject.update(SQL, firstName, lastName, email, roleId, employeeId);
        }
        
        /**
         * Changes the password of employee by ID
         */
        public void changePassword(String password, Integer employeeId) {
            String SQL = "UPDATE Users SET password = ? WHERE userId = ?";
            jdbcTemplateObject.update(SQL, password, employeeId);
        }
        
        /**
         * Adds the employee
         */
        public void addEmployee(String firstName, String lastName,
                            String email, String password, Integer roleId) {
            String SQL = "INSERT INTO Users VALUES(User_seq.NEXTVAL,?,?,?,?,?)";
            jdbcTemplateObject.update(SQL, firstName, lastName, email, password, roleId);
        }
        
        /**
         * Deletes an employee from the entire database by his or her ID
         */
        public void deleteEmployee(Integer employeeId) {
            String SQL = "DELETE FROM Users WHERE UserId = ?";
            jdbcTemplateObject.update(SQL, employeeId);
        }
        
        /**
         * Check a duplicate for the email except the email of one employee
         * Return true if there is duplicate for email
         */
        public boolean hasDuplicateEmailExceptThis(String email, Integer employeeId) {
            String resultEmail = ""; //
            String SQL = "SELECT DISTINCT(email) FROM Users WHERE email = ? AND userId <> ?";
            try {
                resultEmail = jdbcTemplateObject.queryForObject(SQL, new Object[] {email, employeeId}, String.class);
            }
            catch (EmptyResultDataAccessException e) {
                System.err.println("method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nexception: " + e.toString());
            }
            finally {
                return resultEmail.equals("") ? false : true;
            }
        }
        
        /**
         * Check a duplicate for the email
         * Return true if there is a duplicate for email
         */
        public boolean hasDuplicateEmail(String email) {
            String resultEmail = "";
            String SQL = "SELECT DISTINCT(email) FROM Users WHERE email = ?";
            try {
                resultEmail = jdbcTemplateObject.queryForObject(SQL, new Object[] {email}, String.class);
            }
            catch (EmptyResultDataAccessException e) {
                System.err.println("method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + "\nexception: " + e.toString());
            }
            finally {
                return resultEmail.equals("") ? false : true;
            }
        }

}