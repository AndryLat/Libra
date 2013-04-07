/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

import java.util.List;

/**
 *
 * @author Sashenka
 */
public class DisplayAF 
{
    private Integer appId;
   /* private String firstName;
    private String lastName;
    private String patronymic;*/
    private String email;
    private String phoneNumber;
    private String departmentName;
    private String FacultyName;
    private String universityName;
    private Integer course;
    private Integer graduated;
    private String advertisingName;
    private String advertisingComment;
    private String fio;

    
    private List<DisplayCF> cf;

    public List<DisplayCF> getCf() 
    {
        return cf;
    }

    public void setCf(List<DisplayCF> cf) 
    {
        this.cf = cf;
    }
      
    public String getAdvertisingComment() 
    {
        return advertisingComment;
    }

    public String getAdvertisingName() 
    {
        return advertisingName;
    }

    public Integer getAppId() 
    {
        return appId;
    }

    public Integer getCourse() 
    {
        return course;
    }

    public String getDepartmentName() 
    {
        return departmentName;
    }

    public String getFacultyName() 
    {
        return FacultyName;
    }

    public String getEmail() 
    {
        return email;
    }

   /* public String getFirstName() 
    {
        return firstName;
    }
*/
    public Integer getGraduated() 
    {
        return graduated;
    }

  /*  public String getLastName() 
    {
        return lastName;
    }

    public String getPatronymic() 
    {
        return patronymic;
    }
*/
    public String getPhoneNumber() 
    {
        return phoneNumber;
    }

    public String getUniversityName() 
    {
        return universityName;
    }

    public String getFio() 
    {
        return fio;
    }
    
    public void setAdvertisingComment(String advertisingComment) 
    {
        this.advertisingComment = advertisingComment;
    }

    public void setAdvertisingName(String advertisingName) 
    {
        this.advertisingName = advertisingName;
    }

    public void setAppId(Integer appId) 
    {
        this.appId = appId;
    }

    public void setCourse(Integer course) 
    {
        this.course = course;
    }

    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public void setFacultyName(String FacultyName) 
    {
        this.FacultyName = FacultyName;
    }

  /*  public void setFirstName(String firstName) 
    {
        this.firstName = firstName;
    }
*/
    public void setGraduated(Integer graduated) 
    {
        this.graduated = graduated;
    }

   /* public void setLastName(String lastName) 
    {
        this.lastName = lastName;
    }

    public void setPatronymic(String patronymic) 
    {
        this.patronymic = patronymic;
    }
*/
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public void setUniversityName(String universityName) 
    {
        this.universityName = universityName;
    }

    public void setFio(String fio) 
    {
        this.fio = fio;
    }
    
    
    
}
