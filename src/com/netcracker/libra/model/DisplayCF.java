/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

/**
 *
 * @author Sashenka
 */
public class DisplayCF 
{
    private String name;
    private String value;
    private Integer level;

    public Integer getLevel() 
    {
        return level;
    }

    public void setLevel(Integer level) 
    {
        this.level = level;
    }
    
    
    public String getName() 
    {
        return name;
    }

    public String getValue() 
    {
        return value;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public void setValue(String value) 
    {
        this.value = value;
    }
}
