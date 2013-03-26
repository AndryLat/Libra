/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

/**
 * Model for showing  table whith results of interview
 * @author Sashenka
 */
public class InterviewResultsInfo 
{
    String fio;
    double avgMark;
    int appId;
    int r;
    String email;

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }   
    
    public int getR() 
    {
        return r;
    }

    public void setR(int r) 
    {
        this.r = r;
    }
    
    public double getAvgMark() 
    {
        return avgMark;
    }

    public String getFio() 
    {
        return fio;
    }
    
    public int getAppId() 
    {
        return appId;
    }

    public void setFio(String fio) 
    {
        this.fio = fio;
    }

    public void setAvgMark(double avgMark) 
    {
        this.avgMark = avgMark;
    }

    public void setAppId(int appId) 
    {
        this.appId = appId;
    }    
}
