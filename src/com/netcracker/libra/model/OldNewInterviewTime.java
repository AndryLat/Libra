package com.netcracker.libra.model;

/**
 * This class contain general info about the student and 
 * the date and time of interview that student is assigned first
 * and the date ant time that student changed
 * @author Alexander Lebed
 */
public class OldNewInterviewTime {
    private int appId;
    private String firstName;
    private String lastName;
    private String email;
    private String fieldName = "Дата интервью";
    private String oldTimeInterview;
    private String oldDateInterview;
    private String newTimeInterview;
    private String newDateInterview;
    private int oldInterviewId;
    private int newInterviewId;

    /**
     * @return the appId
     */
    public int getAppId() {
        return appId;
    }

    /**
     * @param appId the appId to set
     */
    public void setAppId(int appId) {
        this.appId = appId;
    }
    
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * @return the filedName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param filedName the filedName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the oldTimeInterview
     */
    public String getOldTimeInterview() {
        return oldTimeInterview;
    }

    /**
     * @param oldTimeInterview the oldTimeInterview to set
     */
    public void setOldTimeInterview(String oldTimeInterview) {
        this.oldTimeInterview = oldTimeInterview;
    }

    /**
     * @return the oldDateInterview
     */
    public String getOldDateInterview() {
        return oldDateInterview;
    }

    /**
     * @param oldDateInterview the oldDateInterview to set
     */
    public void setOldDateInterview(String oldDateInterview) {
        this.oldDateInterview = oldDateInterview;
    }

    /**
     * @return the newTimeInterview
     */
    public String getNewTimeInterview() {
        return newTimeInterview;
    }

    /**
     * @param newTimeInterview the newTimeInterview to set
     */
    public void setNewTimeInterview(String newTimeInterview) {
        this.newTimeInterview = newTimeInterview;
    }

    /**
     * @return the newDateInterview
     */
    public String getNewDateInterview() {
        return newDateInterview;
    }

    /**
     * @param newDateInterview the newDateInterview to set
     */
    public void setNewDateInterview(String newDateInterview) {
        this.newDateInterview = newDateInterview;
    }
    
    /**
     * @return the oldInterviewId
     */
    public int getOldInterviewId() {
        return oldInterviewId;
    }

    /**
     * @param oldInterviewId the oldInterviewId to set
     */
    public void setOldInterviewId(int oldInterviewId) {
        this.oldInterviewId = oldInterviewId;
    }

    /**
     * @return the newInterviewId
     */
    public int getNewInterviewId() {
        return newInterviewId;
    }

    /**
     * @param newInterviewId the newInterviewId to set
     */
    public void setNewInterviewId(int newInterviewId) {
        this.newInterviewId = newInterviewId;
    }

}
