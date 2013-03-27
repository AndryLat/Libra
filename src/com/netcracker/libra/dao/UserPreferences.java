/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.dao;

import org.springframework.stereotype.Component;

/**
 *
 * @author Sashenka
 */
@Component
public class UserPreferences
{
    public Integer UserId= new Integer(-1);
    public int accessLevel=-1;
    public boolean isAppformFilled=false;
    public String userEmail=null;
    /*private int UserId = -1;   
    private int accessLevel=-1;

    public int getAccessLevel() 
    {
        return accessLevel;
    }

    public int getUserId() 
    {
        return UserId;
    }

    public void setAccessLevel(int accessLevel) 
    {
        this.accessLevel = accessLevel;
    }

    public void setUserId(int UserId) 
    {
        this.UserId = UserId;
    }*/
}
