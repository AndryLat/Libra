package com.netcracker.libra.dao;

import com.netcracker.libra.dao.oldNew.OldNewAdvertisingRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewCourseRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewDynamicFieldRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewEducationRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewFirstNameRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewGraduatedRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewInterviewTimeRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewLastNameRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewPatronynicRowMapper;
import com.netcracker.libra.dao.oldNew.OldNewPhoneNumberRowMapper;
import com.netcracker.libra.model.ApplicationChange;
import com.netcracker.libra.model.DateAndInterviewer;
import com.netcracker.libra.model.DateAndInterviewerResults;
import com.netcracker.libra.model.Department;
import com.netcracker.libra.model.Faculty;
import com.netcracker.libra.model.InterviewerResult;
import com.netcracker.libra.model.Student;
import com.netcracker.libra.model.University;
import com.netcracker.libra.model.User;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 * 
 * @author Yuliya
 */
@Repository
public class HrJDBC implements HrDAO {

        private static JdbcTemplate jdbcTemplateObject;
       /*
        * Returns all students
        */
        public List<Student> listStudents() {
        String SQL = "select a.appid, "
                        + "u.firstname, "
                        + "u.lastname, "
                        + "u.email from Users u "
                            + "join AppForm a on u.userId=a.userId "
                                + "where u.roleid=1";
        List <Student> students = jdbcTemplateObject.query(SQL, new ShortStudentRowMapper());
        return students;
            }

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
		
	}
        
        @Override
        public void deleteFormById(Integer id){
           String SQL = "delete from users where userid=("
                        + " select u.userid from users u join appForm a on a.userId=u.userId "
                            + "where a.appid = ?)";
           jdbcTemplateObject.update(SQL, id);
        }

        public void updateStudent(Student student){
         
        }
        
	@Override
	@Autowired
        public void setDataSource(DataSource dataSource) {
            jdbcTemplateObject = new JdbcTemplate(dataSource);
        }

        /*
         * Returns student by AppForm Id
         */
    public List<Student> getStudent(Integer id) {
        String SQL = "select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u "
                                + "join appform a on u.userid=a.userid and a.appid=?";
        List <Student> students = jdbcTemplateObject.query(SQL, new Object[]{id}, new ShortStudentRowMapper());
        return students;
     }
    /*
     * Check if student has appForm
     */
    public int checkAppForm(int appId){
        String SQL="select count(c.appId) from columnFields c "
                    + "join appform a on a.APPID=c.appId where a.appId=?";
        int k=jdbcTemplateObject.queryForInt(SQL,appId);
        return k;
    }
    /*
     * Check if student has/had interview
     */
    public int checkStudentHasInterview(int appId){
        String SQL="select count(i.appId) from interview i "
                    + "join appform a on a.APPID=i.appId where i.appId=?";
        int k=jdbcTemplateObject.queryForInt(SQL,appId);
        return k;
    }
    /*
     * Check if student passed interview
     */
    public int checkStudentPassedInterview(int appId){
        String SQL="select count(i.interviewId) from interviewResults i "
                    + "join interview a on a.interviewId=i.interviewId where a.appId=?";
        int k=jdbcTemplateObject.queryForInt(SQL,appId);
        return k;
    }
    /*
      * Returns right param for query if it needs to find students by education
      */
    public String whatEducationLookingFor(String myParam){
        String eduParam="d.departmentId";
        if (myParam.equals("university")) 
            eduParam="un.universityId";
        if (myParam.equals("faculty")) 
            eduParam="f.facultyId";
        return eduParam;
    }
    /*
      * Returns students by appform id and education
      */
    public List<Student> getStudent(String eduParam, int eduValue, Integer idStudent) {
        String education=whatEducationLookingFor(eduParam);
        String query = "select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u join appform a on u.userid=a.userid "
                                + "join department d on d.departmentId=a.DepartmentId "
                                + "join faculty f on f.facultyid=d.facultyId "
                                + "join university un on un.universityId=f.universityId "
                                    + "where "+education+"=? and a.appid=?";
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(), eduValue,idStudent);
        return students;
     }
      /*
      * Returns students by last name 
      */
     @Override
     public List<Student> getStudentsByLastName(String lname) {
          String SQL = "select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u "
                                + "join appform a on u.userid=a.userid "
                                    + "where lower(u.lastname) like '%"+lname.toLowerCase()+"%'";
        List <Student> students;
        students = jdbcTemplateObject.query(SQL, new ShortStudentRowMapper());
        return students;
     }
     /*
      * Returns students by last name and education
      */
     public List<Student> getStudentsByLastName(String eduParam, int eduValue, String lname) {
        String education=whatEducationLookingFor(eduParam);
        String query = "select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u join appform a on u.userid=a.userid "
                                + "join department d on d.departmentId=a.DepartmentId "
                                + "join faculty f on f.facultyid=d.facultyId "
                                + "join university un on un.universityId=f.universityId "
                                    + "where "+education+"=? and lower(u.lastname) like '%"+lname.toLowerCase()+"%'";         
        List <Student> students=jdbcTemplateObject.query(query, new ShortStudentRowMapper(),eduValue);
        return students;
     }
        /*
         * Returns students by any field
         */
        public List<Student> getStudentsByAllFields(String value){
        String SQL = "select a.appid, "
                        + "u.firstname, "
                        + "u.lastname, "
                        + "u.email from users u "
                            + "join appform a on u.userid=a.userid "
                                + "where ((a.appid like '"+value+"') or (lower(u.firstname) like '%"+value.toLowerCase()+"%') "
                                + "or (lower(u.lastname) like '%"+value.toLowerCase()+"%') or (lower(u.email) like '%"+value.toLowerCase()+"%'))";
        List <Student> students;
        students = jdbcTemplateObject.query(SQL, new ShortStudentRowMapper());
        return students;
        }
        /*
         * Returns students by any field and education
         */
        public List<Student> getStudentsByAllFields(String eduParam, int eduValue,String value){
        String education=whatEducationLookingFor(eduParam);
        String query = "select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u join appform a on u.userid=a.userid "
                                + "join department d on d.departmentId=a.DepartmentId "
                                + "join faculty f on f.facultyid=d.facultyId "
                                + "join university un on un.universityId=f.universityId "
                                    + "where "+education+"="+eduValue+" and ((a.appid like '"+value+"') "
                                    + "or (lower(u.firstname) like '%"+value.toLowerCase()+"%') "
                                    + "or (lower(u.lastname) like '%"+value.toLowerCase()+"%') "
                                    + "or (lower(u.email) like '%"+value.toLowerCase()+"%'))";
        
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
        return students;
        }

        /*
         * Returns students by First name
         */
     @Override
     public List<Student> getStudentsByFirstName(String fname) {
         String SQL = "select a.appid, "
                             + "u.firstname, "
                             + "u.lastname, "
                             + "u.email from users u "
                                 + "join appform a on u.userid=a.userid "
                                     + "where lower(u.firstname) like '%"+fname.toLowerCase()+"%' order by 1";
         List <Student> students = jdbcTemplateObject.query(SQL, new ShortStudentRowMapper());
         return students;    
     }
     /*
      * Returns Students bu first name and education
      */
     public List<Student> getStudentsByFirstName(String eduParam, int eduValue, String fname) {
         String education=whatEducationLookingFor(eduParam);
         String query = "select a.appid, "
                             + "u.firstname, "
                             + "u.lastname, "
                             + "u.email from users u "
                                + "join appform a on u.userid=a.userid "
                                + "join department d on d.departmentId=a.DepartmentId "
                                + "join faculty f on f.facultyid=d.facultyId "
                                + "join university un on un.universityId=f.universityId "
                                    + "where "+education+"=? and lower(u.lastname) like '%"+fname.toLowerCase()+"%'";
         
         List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(),eduValue);
         return students;    
     }
     /*
      * Returns students by Email
      */

        @Override
     public List<Student> getStudentsByEmail(String mail) {
        String SQL = "select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u "
                                + "join appform a on u.userid=a.userid "
                                    + "where lower(u.email) like '%"+mail.toLowerCase()+"%'";
        List <Student> students = jdbcTemplateObject.query(SQL, new ShortStudentRowMapper());
        return students;
     }
        /*
         * Returns students by Email and Education
         */
     public List<Student> getStudentsByEmail(String eduParam, int eduValue, String mail) {
        String education=whatEducationLookingFor(eduParam);
        String query = "select a.appid, "
                             + "u.firstname, "
                             + "u.lastname, "
                             + "u.email from users u "
                                + "join appform a on u.userid=a.userid "
                                + "join department d on d.departmentId=a.DepartmentId "
                                + "join faculty f on f.facultyid=d.facultyId "
                                + "join university un on un.universityId=f.universityId "
                                    + "where "+education+"=? and lower(u.lastname) like '%"+mail.toLowerCase()+"%'"; 
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(),eduValue);
        return students;
     }
        /*
         * Returns all universities from database
         */
     public List<University> getAllUniversity() {
        String query="select universityId, "
                          + "universityName from university";
        List<University> universities = jdbcTemplateObject.query(query, new UniversityRowMapper());
        return universities;
     }
     /*
      * Returns unviersities by name
      */
     public List<University> getUniversityByName(String name){
         String query="select universityId, "
                           + "universityName from university "
                             + "where lower(universityName) like '%"+name.toLowerCase()+"%'";
         List<University> universities = jdbcTemplateObject.query(query, new UniversityRowMapper());
         return universities;
     }
     
     /*
      * Returns universities by id
      */
     public List<University> getUniversityById(int universityId){
         String query="select universityId, universityName from university where universityId=?";
         List<University> universities = jdbcTemplateObject.query(query, new UniversityRowMapper(), universityId);
         return universities;
     }
     /*
      * Retutns all faculties
      */
     public List<Faculty> getAllFaculties() {
        String query = "select f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from faculty f "
                                + "join university u on f.universityId=u.universityId "
                                    + "order by f.facultyId";
        List<Faculty> faculties = jdbcTemplateObject.query(query, new FacultyRowMapper() );
        return faculties;
     }
     /*
      * Returns all faulties of university
      */
     public List<Faculty> getAllFaculties(int univerId) {
          String query="select f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from faculty f "
                                + "join university u on f.universityId=u.universityId  "
                                    + "where f.universityId=?";
        List<Faculty> faculties;
        faculties = jdbcTemplateObject.query(query, new FacultyRowMapper(), univerId);
        return faculties;
     }
     /*
      *Returns faculty by id
      */
     public List<Faculty> getAllFacultiesById(int facultyId) {
          String query="select f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from faculty f "
                                + "join university u on f.universityId=u.universityId "
                                    + " where f.facultyId=?";
        List<Faculty> faculties;
        faculties = jdbcTemplateObject.query(query, new FacultyRowMapper(), facultyId);
        return faculties;
     
     }
     public List<Faculty> getUnselectedFaculties(int facultyId, int universityId) {
          String query="select f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from faculty f "
                                + "join university u on f.universityId=u.universityId  "
                                    + "where u.universityId=? and f.facultyId not like '"+facultyId+"'";
        List<Faculty> faculties;
        faculties = jdbcTemplateObject.query(query, new FacultyRowMapper(), universityId);
        return faculties;
     }
     /*
      * Returns all faculties by param
      */
     public List<Faculty> getAllFaculties(String param, String name) {
          String query="select f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from faculty f "
                                + "join university u on f.universityId=u.universityId "
                                    + " where lower("+param+") like '%"+name.toLowerCase()+"%'";
        List<Faculty> faculties;
        faculties = jdbcTemplateObject.query(query, new FacultyRowMapper());
        return faculties;
     }
    /*
     * REturns all departments
     */
     public List<Department> gelAllDepartemtns() {
          String query="select d.departmentId, "
                            + "d.departmentName, "
                            + "f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from department d "
                                + "join faculty f on d.facultyId=f.facultyId "
                                + "join university u on f.universityId=u.universityId";
        List<Department> departments = jdbcTemplateObject.query(query, new departmentRowMapper());
        return departments;
     }
     /*
      * Returns all departmetns by faculty or university
      */
     public List<Department> getAllDepartments(String param, int facultyId) {
          String query="select d.departmentId, "
                            + "d.departmentName, "
                            + "f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from department d "
                                + "join faculty f on d.facultyId=f.facultyId "
                                + "join university u on f.universityId=u.universityId "
                                    + "where "+param+"=?";
        List<Department> departments = jdbcTemplateObject.query(query,new departmentRowMapper(),facultyId);
        return departments;
    }
     public List<Department> getAllDepartments(String param, String name) {
        String query="select d.departmentId, "
                          + "d.departmentName, "
                          + "f.facultyId, "
                          + "f.facultyName, "
                          + "u.universityId, "
                          + "u.universityName from department d "
                            + "join faculty f on d.facultyId=f.facultyId "
                            + "join university u on f.universityId=u.universityId "
                                + "where lower("+param+") like '%"+name.toLowerCase()+"%'";
        List<Department> departments = jdbcTemplateObject.query(query,new departmentRowMapper());
        return departments;
    }
    /*
     * REturns all students by university
     */      
    public List<Student> getStudentByUniversity(int universityId){
          String query="select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email, "
                            + "d.departmentName from users u "+
                                "join appform a on u.userid=a.userid "+
                                "join department d on d.departmentId=a.DepartmentId "+ 
                                "join faculty f on f.facultyid=d.facultyId "+
                                "join university un on un.universityId=f.universityId "
                                    + "where un.universityId=?";
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(), universityId);
        return students;
    }
    
    /*
     * REturns all students by faculty
     */
    public List<Student> getStudentByFaculty(int facultyId){
          String query="select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u "+
                                "join appform a on u.userid=a.userid "+
                                "join department d on d.departmentId=a.DepartmentId  "+
                                "join faculty f on f.facultyid=d.facultyId "
                                    + "where f.facultyId=?";
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(), facultyId);
        return students;
    }
    /*
     * REturns all students by department
     */
    public List<Student> getStudentByDepartment(int departmentId){
          String query="select a.appid, "
                            + "u.firstname, "
                            + "u.lastname, "
                            + "u.email from users u "+ 
                                "join appform a on u.userid=a.userid "
                                    + "where a.departmentId=?";
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(), departmentId);
        return students;
    }
    /*
     * Create new university
     */
    public void addUniversity(String universityName){
        String query="insert into university values(University_seq.NEXTVAL,?)";
        jdbcTemplateObject.update(query,universityName);
    }
    /*
     * Create new Faculty
     */
    public void addFaculty(String facultyName, int univerId) {
        String query="insert into faculty values(Faculty_seq.NEXTVAL,?,?)";
        jdbcTemplateObject.update(query,facultyName,univerId);
    }
    /*
     * Create new department
     */
    public void addDepartment(String departmentName, int facultyId){
        String query="insert into department values(Department_seq.NEXTVAL,?,?)";
        jdbcTemplateObject.update(query,departmentName,facultyId);
    }
    /*
     * Delete univetsity
     */
    public void deleteUniversity(int universityID) {
        String query="delete from university where universityId=?";
        jdbcTemplateObject.update(query,universityID);   
    }
    /*
     * delete faculty
     */
    public void deleteFaculty(int facultyId) {
        String query="delete faculty where facultyId=?";
        jdbcTemplateObject.update(query,facultyId);
    }
    /*
     * delete department
     */
    public void deleteDepartment(int departemtnId) {
        String query="delete from department where departmentID=?";
        jdbcTemplateObject.update(query,departemtnId);
    }
    /*
     * update university
     */
    public void updateUniversity(int universityID, String universityName) {
        String query="update university set universityName=? where universityId=?";
        jdbcTemplateObject.update(query,universityName,universityID);
    }
    
    public List<Faculty> selectedUniversity(int facultyId) {
          String query="select f.facultyId, "
                            + "f.facultyName, "
                            + "u.universityId, "
                            + "u.universityName from faculty f "
                                + "join university u on f.universityId=u.universityId "
                                    + " where f.facultyId = ?";
        List<Faculty> faculties;
        faculties = jdbcTemplateObject.query(query, new FacultyRowMapper(),facultyId);
        return faculties;
     }
    public List<University> unselectedUniversity(int university){
        String query="select universityId, universityName from university where universityId not like ?";
        List<University> univers = jdbcTemplateObject.query(query, new UniversityRowMapper(), university);
        return univers;
    }
    public List<University> selectedUniversit(int university){
        String query="select universityId, universityName from university where universityId = ?";
        List<University> univers = jdbcTemplateObject.query(query, new UniversityRowMapper(), university);
        return univers;
    }
    /*
     * update faculty
     */
    public void updateFaculty(int facultyId, String facultyName, int univerId) {
        String query="update faculty set facultyName=?, universityId=? where facultyId=?";
        jdbcTemplateObject.update(query, facultyName,univerId, facultyId);
    }
    /*
     * Returns university number by university name 
     */
    public int getUniversityIdByName(String name){
        String query="select universityId  from university where lower(universityName) = ?";
        return jdbcTemplateObject.queryForInt(query, name.toLowerCase());
    }
    /*
     * update department
     */
    public void updateDepartment(int departmentId, String departmentName, int facId) {
        String query="update department set departmentName=?, facultyId=? where departmentId=?";
        jdbcTemplateObject.update(query, departmentName, facId,departmentId);
    }
    /*
     * REturns count departments of unviersity or faculty
     */
    public int getCountDepts(String param, int id){
        String query="select count(*) from department d "
                        + "join faculty f on f.facultyId=d.facultyId "
                        + "join university u on u.universityId=f.universityId "
                            + "where "+param+" = ?";
        return jdbcTemplateObject.queryForInt(query, id);
    }
    
    public int getCountFaculty(int universityId){
        String query="select count(*) from faculty where universityId=?";
        return jdbcTemplateObject.queryForInt(query,universityId);
    }
    public int getCountStudents(String param, int id){
        String query="select count(*) from appForm a "
                        + "join department d on a.departmentId=d.departmentId "
                        + "join faculty f on f.facultyId=d.facultyId "
                        + "join university u on u.universityId=f.universityId "
                                + "where "+param+" = ?";
        return jdbcTemplateObject.queryForInt(query, id);
    }
     
    /*
     * Returns all languages 
     */
    @Deprecated
    public List<Map<String, Object>> getAllLanguages(){
        String query="select languageId, languageName from languages";
        List<Map<String, Object>> languages=jdbcTemplateObject.queryForList(query);
        return languages;
    }
    /*
     * Creates new language
     */
    @Deprecated
    public void createLanguage(String name){
        String query="insert into languages values(Lang_seq.nextval, ?)";
        jdbcTemplateObject.update(query, name);
    }
    /*
     * deletes language 
     */
    @Deprecated
    public void deleteLanguage(int id){
        String query="delete from languages where languageid=?";
        jdbcTemplateObject.update(query, id);
    }
    /*
     * Changes name of Language
     */
    @Deprecated
    public void updateLanguage(int id, String name){
        String query="update languages set languageName=? where languageId=?";
        jdbcTemplateObject.update(query, name, id);
    }
    /*
     * Finds language by Id
     */
    @Deprecated
    public List<Map<String, Object>> showLangById(int id){
        String query="select languageId, languageName from languages where languageId=?";
        List<Map<String, Object>> langs=jdbcTemplateObject.queryForList(query, id);
        return langs;
    }
    /**
     * finds languages by name or part of name
     * @return list of langs
     */
    @Deprecated
    public List<Map<String, Object>> showLangByName(String name){
        String query="select languageId, languageName from languages where languageName like '%"+name+"%'";
        List<Map<String, Object>> langs=jdbcTemplateObject.queryForList(query);
        return langs;
    }
    /*
     * Returns list of Sorted Students with filter
     */
    @Deprecated
    public List<Student> getOrderStudent(String param, String value, String orderBy){
        if (param.equals("getAll")){
              String query="select a.appid, "
                                + "u.firstname, "
                                + "u.lastname, "
                                + "u.email from users u "
                                    + "join appform a on u.userid=a.userid "
                                      + "order by "+orderBy;
            List<Student> std = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
            return std;
        }
        if (param.equals("appId")){
              String query="select a.appid, "
                                + "u.firstname, "
                                + "u.lastname, "
                                + "u.email from users u "
                                    + "join appform a on u.userid=a.userid "
                                        + "where a.appid=?";
            List<Student> std = jdbcTemplateObject.query(query, new ShortStudentRowMapper(), value);
            return std;
        }
        if (param.equals("firstName")){
              String query="select a.appid, "
                                + "u.firstname, "
                                + "u.lastname, "
                                + "u.email from users u "
                                    + "join appform a on u.userid=a.userid "
                                        + "where lower(u.firstname) like '%"+value.toLowerCase()+"%' "
                                        + "order by "+orderBy;
            List<Student> std = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
            return std;
        }
        if (param.equals("lastName")){
              String query="select a.appid, "
                                + "u.firstname, "
                                + "u.lastname, "
                                + "u.email from users u "
                                    + "join appform a on u.userid=a.userid "
                                          + "where lower(u.lastname) like '%"+value.toLowerCase()+"%' "
                                          + "order by "+orderBy;
            List<Student> std = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
            return std;
        }
        if (param.equals("email")){
              String query="select a.appid, "
                                + "u.firstname, "
                                + "u.lastname, "
                                + "u.email from users u "
                                    + "join appform a on u.userid=a.userid "
                                        + "where lower(u.email) like '%"+value.toLowerCase()+"%' "
                                        + "order by "+orderBy;
            List<Student> std = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
            return std;
        }
        if (param.equals("allFields")){
            String query = "select a.appid, "
                                + "u.firstname, "
                                + "u.lastname, "
                                + "u.email from users u "
                                    + "join appform a on u.userid=a.userid "
                                        + "where ((a.appid like '"+value+"') or (lower(u.firstname) like '%"+value.toLowerCase()+"%') "
                                        + "or (lower(u.lastname) like '%"+value.toLowerCase()+"%') "
                                        + "or (lower(u.email) like '%"+value.toLowerCase()+"%')) "
                                        + "order by "+orderBy;
            List<Student> std = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
            return std;
        }
        return null;
    }
    /*
     * Returns sorted List of Stundents 
     */
    @Deprecated
    public List<Student> getOrderedStudentByEdu(String param, String value, String orderBy){
        String query=null;
        if (param.equals("getAll")){
             query="select a.appid, "
                        + "u.firstname, "
                        + "u.lastname, "
                        + "u.email from users u "+ 
                            "join appform a on u.userid=a.userid order by "+orderBy;
            List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper());
            return students;
        }
        if (param.equals("universityId")){
             query="select a.appid, "
                        + "u.firstname, "
                        + "u.lastname, "
                        + "u.email, "
                        + "d.departmentName from users u "+
                            "join appform a on u.userid=a.userid "+
                            "join department d on d.departmentId=a.DepartmentId "+ 
                            "join faculty f on f.facultyid=d.facultyId "+
                            "join university un on un.universityId=f.universityId and un.universityId=?"
                                + " order by "+orderBy;
        }
        if (param.equals("facultyId")){
             query="select a.appid, "
                        + "u.firstname, "
                        + "u.lastname, "
                        + "u.email, "
                        + "d.departmentName from users u "+
                            "join appform a on u.userid=a.userid "+
                            "join department d on d.departmentId=a.DepartmentId "+ 
                            "join faculty f on f.facultyid=d.facultyId "+
                            "join university un on un.universityId=f.universityId "+
                                "where f.facultyid=? "+
                                "order by "+orderBy;
        }
        if (param.equals("departmentId")){
            query="select a.appid, "
                        + "u.firstname, "
                        + "u.lastname, "
                        + "u.email from users u " 
                            + "join appform a on u.userid=a.userid "
                                + "where a.departmentId=? order by "+orderBy;
        }
        List <Student> students = jdbcTemplateObject.query(query, new ShortStudentRowMapper(), value);
        return students;
    }
    
    
    /**
     * @return interviews ID by application's form ID
     * @author Alexander Lebed
     */
    public Integer getInterviewId (Integer appId) {
        String SQL = "SELECT interviewId FROM Interview WHERE appId = ?";
        int interviewId;
        try{
            interviewId = jdbcTemplateObject.queryForInt(SQL, new Object[] {appId});
        }
        catch (EmptyResultDataAccessException e) {
            interviewId = 0;
            e.printStackTrace();
        }
        return interviewId;
    }
    
    /**
     * @return interviews IDs by app.form ID
     * @author Alexander Lebed
     */
    public List <Integer> getInterviewIds (Integer appId) {
        String SQL = "SELECT interviewId FROM Interview WHERE appId = ?";
        List <Integer> interviewId = jdbcTemplateObject.queryForList(SQL, new Object[] {appId}, Integer.class);
        return interviewId;
    }
    
    /**
     * @return a string of interviews finish date and time
     */
    public String getInterviewFinishDate(Integer interviewId) {
        String SQL = "SELECT to_char(d.dateFinish,'DD.MM.YYYY HH24:MI') FROM InterviewDate d, Interview i "+
                     "WHERE d.interviewDateId = i.interviewDateId AND i.interviewId = ?";
        String interviewDateFinish;
        try{
            interviewDateFinish = jdbcTemplateObject.queryForObject(SQL, new Object[] {interviewId}, String.class);
        }
        catch (EmptyResultDataAccessException e) {
            interviewDateFinish = null;
            e.getMessage();
        }
        return interviewDateFinish;
    }
    
    
    
    /**
     * @return a List of interviewers who were assigned to a certain time
     */
    public List <User> getInterviewersFromInterviewerList(Integer interviewDateId) {
        String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE userId IN (SELECT userId FROM InterviewerList WHERE interviewDateId = ?)";
        List <User> users = jdbcTemplateObject.query(SQL, new Object[] {interviewDateId}, new UserRowMapper());
        return users;
    }
    
    /**
     * Returns a List of interviewers who interviewed at a particular interview
     */
    public List <User> getInterviewersFromInterviewResults(Integer interviewId) {
        String SQL = "SELECT userId, firstName, lastName, email, password, roleId FROM Users WHERE userId IN (SELECT userId FROM InterviewResults WHERE interviewId = ?)";
        List <User> users = jdbcTemplateObject.query(SQL, new Object[] {interviewId}, new UserRowMapper());
        return users;
    }
    
    /**
     * @return true if a student was interviewed
     */
    public boolean getInterviewResults(Integer interviewId) {
        String SQL = "SELECT DISTINCT(1) FROM InterviewResults WHERE interviewId = ?";
        int result;
        try {
            result = jdbcTemplateObject.queryForInt(SQL, new Object[] {interviewId});
        }
        catch (EmptyResultDataAccessException e) {
            result = 0;
            e.getMessage();
        }
        return result==1 ? true : false;
    }
    
    /**
     * @return a List of InterviewerResult (interviewer's data and his or her assessment of interview) of certain interview 
     */
    public List <InterviewerResult> getUserResults(Integer interviewId) {
        String SQL = "SELECT u.userId, u.firstName, u.lastName, u.roleId, r.mark, r.comments FROM Users u, InterviewResults r WHERE u.userId = r.userId AND r.interviewId = ?";
        List <InterviewerResult> userResults = jdbcTemplateObject.query(SQL, new Object[] {interviewId}, new InterviewerResultRowMapper());
        return userResults;
    }
    
    /**
     * @return a List with information of application's form ID, date and time of certain interview, 
     * assigned interviewers and their assessment of the interview by interviews ID
     */
    public List <DateAndInterviewerResults> getDateAndInterviewerResults (Integer interviewId) {
        String SQL = "SELECT i.AppId, to_char(d.dateStart,'dd.mm.yyyy') interviewDate, to_char(d.dateStart,'hh24:mi')||' - '||  to_char(d.dateFinish,'hh24:mi') interviewTime, "+
                     "u.firstName||' '|| u.lastName interviewerName, u.roleId interviewerRole, r.mark interviewerMark, r.comments interviewerComments "+
                     "FROM interviewResults r, interview i, interviewDate d, users u "+
                     "WHERE i.interviewId = r.interviewId AND i.interviewDateId = d.interviewDateId AND u.userId = r.userId AND i.interviewId = ?";
        List <DateAndInterviewerResults> resultList = jdbcTemplateObject.query(SQL, new Object[] {interviewId}, new DateAndInterviewerResultsRowMapper());
        return resultList;
    }
    
    /**
     * @return a List with information of application's form ID, date and time of certain interview, 
     * assigned interviewers by interviews ID
     */
    public List <DateAndInterviewer> getDateAndInterviewer (Integer interviewId) {
        String SQL = "SELECT i.AppId, to_char(d.dateStart,'dd.mm.yyyy') interviewDate, to_char(d.dateStart,'hh24:mi')||' - '||  to_char(d.dateFinish,'hh24:mi') interviewTime, "+
                     "u.firstName||' '|| u.lastName interviewerName, u.roleId interviewerRole FROM interviewerList l, interview i, interviewDate d, users u "+
                     "WHERE i.interviewDateId = d.interviewDateId AND d.interviewDateId = l.interviewDateId AND l.userId = u.userId AND i.interviewId = ?";
        List <DateAndInterviewer> resultList = jdbcTemplateObject.query(SQL, new Object[] {interviewId}, new DateAndInterviewerRowMapper());
        return resultList;
    }
    
    /**
     * @return general info about the student and 
     * the date and time of interview that student is assigned first
     * and the date ant time that student changed
     */
    public List <ApplicationChange> getAllOldNewInterviewTime() {
        String SQL = "SELECT oldInt.appId, u.firstName, u.lastName, u.email, "+
                             "TO_CHAR(oldDate.dateStart,'hh24:mi')||' - '|| TO_CHAR(oldDate.dateFinish,'hh24:mi')||' '||TO_CHAR(oldDate.dateFinish,'dd.mm.yyyy') oldValue, "+
                             "TO_CHAR(newDate.dateStart,'hh24:mi')||' - '||TO_CHAR(newDate.dateFinish,'hh24:mi')||' '|| TO_CHAR(newDate.dateFinish,'dd.mm.yyyy') newValue, oldInt.interviewId oldId, newInt.interviewId newId "+
                     "FROM InterviewDate oldDate, InterviewDate newDate, Interview oldInt, Interview newInt, Users u, AppForm a "+
                     "WHERE oldDate.InterviewDateId = oldInt.InterviewDateId AND newDate.InterviewDateId = newInt.InterviewDateId "+
                     "AND oldInt.appId = newInt.appId AND oldInt.appId = a.appId AND a.userId = u.userId AND oldInt.status = 1 AND newInt.status = 0 ORDER BY appId DESC";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewInterviewTimeRowMapper());
        return resultList;
    }
    
    /**
     * @return old(status=1) and new(status=0) columnFileds, and general info about the student
     */
    public List <ApplicationChange> getAllOldNewDynamicFields() {
        String SQL = "SELECT a.appId, u.firstName, u.lastName, u.email, c.name fieldName, oldField.value oldValue, newField.value newValue, oldField.columnFieldId oldId, newField.columnFieldId newId "+
                     "FROM appForm a, Users u, NewColumns c, ColumnFields oldField, ColumnFields newField "+
                     "WHERE a.userId = u.userId AND a.appId = oldField.appId AND a.appId = newField.appId "+
                     "AND c.columnId = oldField.columnId AND c.columnId = newField.columnId AND oldField.status = 1 AND newField.status = 0";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewDynamicFieldRowMapper());
        return resultList;
    }

    public List <ApplicationChange> getOldNewPatronymics() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, af.patronymic oldValue, ar.patronymic newValue, af.appId oldId, ar.appRequestId newId "+
                     "FROM AppForm af, AppRequest ar, Users u WHERE af.userId = ar.userId AND af.userId = u.userId AND af.patronymic <> ar.patronymic";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewPatronynicRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewPhoneNumbers() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, af.phoneNumber oldValue, ar.phoneNumber newValue, af.appId oldId, ar.appRequestId newId "+
                      "FROM AppForm af, AppRequest ar, Users u WHERE af.userId = ar.userId AND af.userId = u.userId AND af.phoneNumber <> ar.phoneNumber";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewPhoneNumberRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewEducation() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, 'Универститет: '||univAppReq.universityName||'<br> Факультет: '||facAppReq.facultyName||'<br> Кафедра: '||depAppReq.departmentName||'<br>' oldValue, "+
                            "'Универститет: '||univAppForm.universityName||'<br> Факультет: '||facAppForm.facultyName||'<br> Кафедра: '||depAppForm.departmentName||'<br>' newValue, af.appId oldId, ar.appRequestId newId "+
                     "FROM AppForm af, AppRequest ar, Users u, Department depAppReq, Faculty facAppReq, University univAppReq, Department depAppForm, Faculty facAppForm, University univAppForm "+
                     "WHERE af.userId = ar.userId AND af.userId = u.userId AND depAppReq.departmentId = ar.departmentId AND facAppReq.facultyId = depAppReq.facultyId AND univAppReq.universityId = facAppReq.universityId "+
                     "AND depAppForm.departmentId = af.departmentId AND facAppForm.facultyId = depAppForm.facultyId AND univAppForm.universityId = facAppForm.universityId AND af.departmentId <> ar.departmentId";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewEducationRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewAdvertising() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, 'Узнал от: '||advAppForm.advertisingTitle||'<br> Отзыв: '||NVL(af.advertisingComment, '-') oldValue, "+
                            "'Узнал от: '||advAppReq.advertisingTitle||'<br> Отзыв: '||NVL(ar.advertisingComment, '-') newValue, af.appId oldId, ar.appRequestId newId "+
                     "FROM AppForm af, AppRequest ar, Users u, Advertising advAppForm, Advertising advAppReq WHERE af.userId = ar.userId AND af.userId = u.userId "+
                     "AND advAppForm.advertisingId = af.advertisingId AND advAppReq.advertisingId = ar.advertisingId AND NVL(af.advertisingComment, 0) <> NVL(ar.advertisingComment, 0)";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewAdvertisingRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewCourses() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, af.course oldValue, ar.course newValue, af.appId oldId, ar.appRequestId newId "+
                     "FROM AppForm af, AppRequest ar, Users u WHERE af.userId = ar.userId AND af.userId = u.userId AND af.course <> ar.course";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewCourseRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewGraduatedYears() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, af.graduated oldValue, ar.graduated newValue, af.appId oldId, ar.appRequestId newId "+
                     "FROM AppForm af, AppRequest ar, Users u WHERE af.userId = ar.userId AND af.userId = u.userId AND af.graduated <> ar.graduated";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewGraduatedRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewFirstNames() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, u.firstName oldValue, ar.firstName newValue, u.userId oldId, ar.appRequestId newId "+
                     "FROM AppRequest ar, AppForm af, Users u WHERE af.userId = u.userId AND ar.userId = u.userId AND ar.firstName <> u.firstName";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewFirstNameRowMapper());
        return resultList;
    }
    
    public List <ApplicationChange> getOldNewLastNames() {
        String SQL = "SELECT af.appId, u.firstName, u.lastName, u.email, u.lastName oldValue, ar.lastName newValue, u.userId oldId, ar.appRequestId newId "+
                     "FROM AppRequest ar, AppForm af, Users u WHERE af.userId = u.userId AND ar.userId = u.userId AND ar.lastName <> u.lastName";
        List <ApplicationChange> resultList = jdbcTemplateObject.query(SQL, new OldNewLastNameRowMapper());
        return resultList;
    }
    
    /**
     * update info from AppRequest to AppForm
     */
    public void confirmMainAppInfo(String columnName, Integer appRequestId, Integer id) {
        
        String SQL = columnName.equals("firstName")||columnName.equals("lastName") 
                        ? "UPDATE Users SET "+columnName+" = (SELECT "+columnName+" FROM AppRequest WHERE appRequestId = ?) WHERE userId = ?"
                        : "UPDATE AppForm SET "+columnName+" = (SELECT "+columnName+" FROM AppRequest WHERE appRequestId = ?) WHERE appId = ?";
        
        jdbcTemplateObject.update(SQL, new Object[]{appRequestId, id});
    }
    
    /**
     * 
     * @param columnName
     * @param appRequestId
     * @param id 
     */
//    public void confirmMainAppInfo(List <String> columnNames, List <Integer> appRequestIds, List <Integer> ids) {
//        StringBuilder str = new StringBuilder();
//        
//        if(columnNames.get(0).equals("firstName")||columnNames.get(0).equals("lastName")) {
//            str.append("UPDATE Users SET "+columnNames.get(0)+" = (SELECT "+columnNames.get(0)+" FROM AppRequest WHERE appRequestId = "+appRequestIds.get(0) +") WHERE userId = "+ids.get(0));
//            
//            for(int i=1; i < columnNames.size(); i++) {
//                if(columnNames.get(i).equals("firstName")||columnNames.get(i).equals("lastName")) {
//                    str.append("AND SET "+columnNames.get(i)+" = (SELECT "+columnNames.get(i)+" FROM AppRequest WHERE appRequestId = ?) WHERE userId = ?");
//                }
//            }
//            
//        }
//        String SQL = str.toString();
//        jdbcTemplateObject.update(SQL);
//    }
    
    /**
     * update interview to status 1
     */
    public void confirmInterviewTime(Integer id) {
        String SQL = "UPDATE Interview SET status = 1 WHERE interviewId = ?";
        jdbcTemplateObject.update(SQL, id);
    }
    
    /**
     * update a few interviews to status 1
     * @param ids the ids list of interviewId
     */
    public void confirmInterviewTime(List <Integer> ids) {
        StringBuilder str = new StringBuilder("UPDATE Interview SET status = 1 WHERE interviewId = " + ids.get(0));
        
        for(int i=1; i < ids.size(); i++) {
            str.append(" AND interviewId = " + String.valueOf(ids.get(i)));
        }
        String SQL = str.toString();
        jdbcTemplateObject.update(SQL);
    }
    
    /**
     * delete interview
     */
    public void deleteInterview(Integer interviewId) {
        String SQL = "DELETE FROM Interview WHERE interviewId = ?";
        jdbcTemplateObject.update(SQL, interviewId);
    }
    
    /**
     * delete a few interviews
     * @param ids the ids list of interviewId
     */
    public void deleteInterview(List <Integer> ids) {
        StringBuilder str = new StringBuilder("DELETE FROM Interview WHERE interviewId = " + ids.get(0));
        
        for(int i=1; i < ids.size(); i++) {
            str.append(" AND interviewId = " + String.valueOf(ids.get(i)));
        }
        String SQL = str.toString();
        jdbcTemplateObject.update(SQL);
    }
    
    /**
     * update columnField to status 1
     */
    public void confirmDynamicField(Integer id) {
        String SQL = "UPDATE ColumnFields SET status = 1 WHERE columnFieldId = ?";
        jdbcTemplateObject.update(SQL, id);
    }
    
    /**
     * update a few columnField to status 1
     * @param ids the ids list of columnFieldId
     */
    public void confirmDynamicField(List <Integer> ids) {
        StringBuilder str = new StringBuilder("UPDATE ColumnFields SET status = 1 WHERE columnFieldId = " + ids.get(0));
        
        for(int i=1; i < ids.size(); i++) {
            str.append(" AND columnFieldId = " + String.valueOf(ids.get(i)));
        }
        String SQL = str.toString();
        jdbcTemplateObject.update(SQL);
    }
    
    /**
     * delete columnField
     */
    public void deleteDynamicField(Integer columnFieldId) {
        String SQL = "DELETE FROM ColumnFields WHERE columnFieldId = ?";
        jdbcTemplateObject.update(SQL, columnFieldId);
    }
    
    /**
     * delete a few columnField
     * @param ids the ids list of columnFieldId
     */
    public void deleteDynamicField(List <Integer> ids) {
        StringBuilder str = new StringBuilder("DELETE FROM ColumnFields WHERE columnFieldId = " + ids.get(0));
        
        for(int i=1; i < ids.size(); i++) {
            str.append(" AND columnFieldId = " + String.valueOf(ids.get(i)));
        }
        String SQL = str.toString();
        jdbcTemplateObject.update(SQL);
    }

}
