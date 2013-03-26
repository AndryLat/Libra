/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.model;

/**
 * We need this object for  displaying template for filling student
 * @author Sashenka
 */
public class AppFormColumns extends Column
{
    private abstractType cT;
    private String typeName;
    
    public void setcT(String s) 
    {
        switch(s)
        {
            case "textstring": 
            case "areastring": 
                this.cT=new StringTextDefinition(columnType.valueOf(s)); 
                break;
            case "integer" : 
                this.cT=new IntegerDefinition(columnType.valueOf(s+"Type")); 
                break;
            case "selectenum" : 
            case "checkboxenum":
            case "radioenum":
                this.cT=new EnumDefinition(columnType.valueOf(s)); 
                break;
            case "show":
                this.cT=new abstractType(); 
                break;
        }
    }
    public void setDefinition(String d)
    {
        String className=cT.getClass().getSimpleName();
        if(className.equals("StringTextDefinition"))
        {
            StringTextDefinition b=(StringTextDefinition)(cT);
                b.setMaxLength(Integer.parseInt(d));
        }
        if(className.equalsIgnoreCase("IntegerDefinition"))
        {
            IntegerDefinition b=(IntegerDefinition)(cT);
                b.setMinMax(d);
        }
        if(className.equalsIgnoreCase("EnumDefinition"))
        {
            EnumDefinition b=(EnumDefinition)(cT);
                b.setEmums(d);
        }
    }

    public abstractType getcT() 
    {
        return cT;
    }

    
    public String getTypeName() 
    {
        return typeName;
    }

    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }
    
}
