/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.ColumnJDBC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.netcracker.libra.dao.TypeJDBC;
import com.netcracker.libra.dao.UserPreferences;
import com.netcracker.libra.model.AppFormColumns;
import com.netcracker.libra.model.ColumnFieldsModel;
import com.netcracker.libra.model.InfoForDelete;
import com.netcracker.libra.service.TemplateService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
/**
 *
 * @author Sashenka
 */
@Controller
public class ColumnController
{
    TypeJDBC typeJDBC=new TypeJDBC();
    ColumnJDBC columnJDBC=new ColumnJDBC();
    @Autowired
    UserPreferences userPreferences;
   // int templateId;
    @RequestMapping(value="showColumn",method = RequestMethod.GET)
    public String showColumns(ModelMap model,
    @RequestParam int templateId)  
    {
        if(userPreferences.accessLevel==1)
        {
            model.addAttribute("types", typeJDBC.getAllInfo());
            model.addAttribute("templateId", templateId);    
            model.addAttribute("columns", columnJDBC.getColumnsInfo(templateId));
            return "ShowNewColumnsView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="changeOrder",method = RequestMethod.POST)
    public String changeOrder(ModelMap model,
    @RequestParam(required=true,value="column1") int column1,
    @RequestParam(required=true,value="column2") int column2)  
    {
        
        return "redirect:showColumn.html?templateId="+templateId;
    }
    @RequestMapping(value="SubmitColumn",method = RequestMethod.POST)
    public String addColumn(ModelMap model,
    @RequestParam("name") String name,
    @RequestParam("selType") int selType,
    @RequestParam("parentColumn") int parentColumn,
    @RequestParam("templateId") int templateId)  
    {
        if(userPreferences.accessLevel==1)
        {
            String message=TemplateService.checkColumn(name);
            if(typeJDBC.existType(selType)==0&&selType!=0)
            {
                message+="<p>Такого типа не существует</p>";
            }
            if(columnJDBC.existColumn(parentColumn)==0&&parentColumn!=0)
            {
                message+="<p>Такой колонки не существует</p>";
            }
            if(!message.equals(""))
            {
                model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
                model.addAttribute("message", message);
                model.addAttribute("title", "Ошибка");
                return "messageView";
            }
            columnJDBC.add(templateId, name, selType, parentColumn);
            return "redirect:showColumn.html?templateId="+templateId;
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="editColumn",method = RequestMethod.GET)
    public String editColumn(ModelMap model,
    @RequestParam("columnId") int columnId,
    @RequestParam("templateId") int templateId)  
    {
        if(userPreferences.accessLevel==1)
        {
            String message="";
            if(columnJDBC.existColumn(columnId)==0)
            {
                message+="<p>Такой колонки не существует</p>";
            }
            if(!message.equals(""))
            {
                model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
                model.addAttribute("message", message);
                model.addAttribute("title", "Ошибка");
            }
            model.addAttribute("columnId", columnId);
            model.addAttribute("columns", columnJDBC.getColumns(templateId,columnId));
            model.addAttribute("types", typeJDBC.getAllInfo());
            model.addAttribute("current",columnJDBC.getColumnInfo(columnId));
            model.addAttribute("templateId", templateId);    
            return "editNewColumnView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="editSubmitColumn",method = RequestMethod.POST)
    public String editSubmitColumn(ModelMap model,
    @RequestParam("name") String name,
    @RequestParam("selType") int selType,
    @RequestParam("parentColumn") int parentColumn,
    @RequestParam("columnId") int columnId,
     @RequestParam("templateId") int templateId)  
    {
        columnJDBC.update(name, selType, parentColumn, columnId);
        return "redirect:showColumn.html?templateId="+templateId;
    }
    
    @RequestMapping(value="delColumns",method = RequestMethod.POST)
    public String delColumns(ModelMap model,
    @RequestParam("delete[]") int[] delete)  
    {
        if(delete==null)
        {
            return "redirect:showTemplates.html";
        }
        if(userPreferences.accessLevel==1)
        {
            List<InfoForDelete> info=columnJDBC.getInfoForDelete(delete);
            int infoSize=info.size();
            model.addAttribute("delete", delete);
            model.addAttribute("info", info);
            model.addAttribute("infoSize",infoSize);
            model.addAttribute("title","Удалить колонки");
            model.addAttribute("h1","Вы действительно хотите удалить эти колонки?");
            model.addAttribute("submit","delSubmitColumns");
            model.addAttribute("location","redirect:showTemplates.html");            
            return "delInfoView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="delSubmitColumns",method = RequestMethod.POST)
    public String delSubmiteColumns(ModelMap model,
    @RequestParam("delete[]") int[] delete)  
    {
        if(userPreferences.accessLevel==1)
        {   
            columnJDBC.delete(delete);          
            return "redirect:showTemplates.html";
        }
        return "redirect:showColumn.html?templateId="+templateId;
    }
    
    @RequestMapping(value="changeColumn",method = RequestMethod.POST)
    public String ChangeColumn(ModelMap model,
    @RequestParam("column1") int column1,
    @RequestParam("column2") int column2,
    @RequestParam("templateId") int templateId)  
    {
        if(userPreferences.accessLevel==1)
        {
            String message="";
            
            if((columnJDBC.existColumn(column1)==0)||(columnJDBC.existColumn(column2)==0))
            {
                message+="<p>Одной из колонок не существует колонки не существует</p>";
            }
            if(!message.equals(""))
            {
                model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
                model.addAttribute("message", message);
                model.addAttribute("title", "Ошибка");
            }
            columnJDBC.swop(column1, column2);
            return "redirect:showColumn.html?templateId="+templateId;
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
   }
    
    @RequestMapping(value="showAppForm",method = RequestMethod.GET)
    public String showAppForm(ModelMap model,
    @RequestParam("templateId") int templateId)  
    {
        ColumnFieldsModel columnFields = new ColumnFieldsModel();
        List<AppFormColumns> appList=columnJDBC.getAppFormColumns(templateId);
        model.addAttribute("columns", appList);
        model.addAttribute("columnFields", columnFields);
        return "appFormView";
    }
    
    //submitForm
    
    @RequestMapping(value="submitForm",method = RequestMethod.POST)
    public String submitForm(ModelMap model,
    @ModelAttribute("columnFields") ColumnFieldsModel columnFields)  
    {
       Map<Integer,String> map=columnFields.getMap();   
        return "appFormView";
    }
    
    @RequestMapping(value="changeColumn",method = RequestMethod.POST)
    public String ChangeColumn(ModelMap model,
    @RequestParam("column1") int column1,
    @RequestParam("column2") int column2)  
    {
        columnJDBC.swop(column1, column2);
        //model.addAttribute("columns", columnJDBC.getBrothers(column,parentColumn));
          return "redirect:showColumn.html?templateId="+templateId;
   }
}
