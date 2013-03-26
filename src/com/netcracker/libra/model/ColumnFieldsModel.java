/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

import java.util.HashMap;
import java.util.Map;

/**
 * We need this object for getting filled template
 * @author Sashenka
 */
public class ColumnFieldsModel
{
    Map<Integer,String> map = new HashMap<Integer,String>();
    public void setMap(Map<Integer,String> map)
    {
        this.map = map;  
    }
    
    public Map<Integer,String> getMap()
    {
        return map;
    }       
}
