/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model.errorreport;

/**
 * Use to obtain data on the new bug
 * @author MorrisDeck
 */
public class ReportOnErrorModel {
    private String userName;
    private String textError;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTextError() {
        return textError;
    }

    public void setTextError(String textError) {
        this.textError = textError;
    }
    
}
