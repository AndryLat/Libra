/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

/**
 *  We need this object for edit column for getting current column(which we need edit)
 * @author Sashenka
 */
public class ColumnForEdit extends Column
{    
    private int typeId;

    public int getTypeId() 
    {
        return typeId;
    }

    public void setTypeId(int typeId) 
    {
        this.typeId = typeId;
    }
}
