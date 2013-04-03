package com.netcracker.libra.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class contain main info about the student, 
 * old value of someone field in app.form and new one that
 * student has been changed
 * @author Alexander Lebed
 */
public class ApplicationChange {
    
    static AtomicInteger nextId = new AtomicInteger();
    private int objectId;
    
    private int appId;
    private String firstName;
    private String lastName;
    private String email;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private int oldId;
    private int newId;
    private String columnName;

    public ApplicationChange () {
        objectId = nextId.incrementAndGet();
    }
    
    /**
     * @return the objectId
     */
    public int getObjectId() {
        return objectId;
    }

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
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
    /**
     * @return the oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * @return the newValue
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * @param newValue the newValue to set
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * @return the oldId
     */
    public int getOldId() {
        return oldId;
    }

    /**
     * @param oldId the oldId to set
     */
    public void setOldId(int oldId) {
        this.oldId = oldId;
    }

    /**
     * @return the newId
     */
    public int getNewId() {
        return newId;
    }

    /**
     * @param newId the newId to set
     */
    public void setNewId(int newId) {
        this.newId = newId;
    }

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
}
