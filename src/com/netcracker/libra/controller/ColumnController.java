/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.ColumnJDBC;
import com.netcracker.libra.dao.InterviewJDBC;
import com.netcracker.libra.dao.StudentJDBC;
import com.netcracker.libra.dao.TemplateJDBC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.netcracker.libra.dao.TypeJDBC;
import com.netcracker.libra.model.AppFormColumns;
import com.netcracker.libra.model.ColumnFieldsModel;
import com.netcracker.libra.model.DisplayAF;
import com.netcracker.libra.model.DisplayCF;
import com.netcracker.libra.model.InfoForDelete;
import com.netcracker.libra.service.TemplateService;
import com.netcracker.libra.util.security.SessionToken;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author Sashenka
 */
@Controller
@SessionAttributes({"regForm", "LOGGEDIN_USER"})
public class ColumnController
{
    
   /* @RequestMapping(value="index2")
    public String index(ModelMap model)  
    {
        return "index2";
    }
    */
    TypeJDBC typeJDBC=new TypeJDBC();
    TemplateJDBC templateJDBC=new TemplateJDBC();
    ColumnJDBC columnJDBC=new ColumnJDBC();
    InterviewJDBC interviewJDBC=new InterviewJDBC();
    StudentJDBC studentJDBC=new StudentJDBC();
    //@Autowired
    //UserPreferences userPreferences;
   // int templateId;
    @RequestMapping(value="showColumn",method = RequestMethod.GET)
    public String showColumns(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam int templateId)  
    {
        if(token.getUserAccessLevel()==1)
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
    
    /**
     * 
     */
    @RequestMapping(value="SubmitColumn",method = RequestMethod.POST)
    public String addColumn(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("name") String name,
    @RequestParam("selType") int selType,
    @RequestParam("parentColumn") int parentColumn,
    @RequestParam("templateId") int templateId)  
    {
        if(token.getUserAccessLevel()==1)
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
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("columnId") int columnId,
    @RequestParam("templateId") int templateId)  
    {
        if(token.getUserAccessLevel()==1)
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
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("name") String name,
    @RequestParam("selType") int selType,
    @RequestParam("parentColumn") int parentColumn,
    @RequestParam("columnId") int columnId,
     @RequestParam("templateId") int templateId)  
    {
        if(token.getUserAccessLevel()==1)
        {
            String message=TemplateService.checkColumn(name);
            if(typeJDBC.existType(selType)==0)
            {
                message+="<p>Такого типа не существует</p>";
            }
            if(columnJDBC.existColumn(parentColumn)==0)
            {
                message+="<p>Такой колонки не существует</p>";
            }
            if(!message.equals(""))
            {
                model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
                model.addAttribute("message", message);
                model.addAttribute("title", "Ошибка");
            }
            columnJDBC.update(name, selType, parentColumn, columnId);
            return "redirect:showColumn.html?templateId="+templateId;
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="delColumns",method = RequestMethod.POST)
    public String delColumns(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam(value="delete[]",required=false) int[] delete,
    @RequestParam("templateId") int templateId) 
    {
        if(delete==null)
        {
            return "redirect:showColumn.html?templateId="+templateId;
        }
        if(token.getUserAccessLevel()==1)
        {
            List<InfoForDelete> info=columnJDBC.getInfoForDelete(delete);
            int infoSize=info.size();
            model.addAttribute("delete", delete);
            model.addAttribute("info", info);
            model.addAttribute("infoSize",infoSize);
            model.addAttribute("title","Удалить колонки");
            model.addAttribute("h1","Вы действительно хотите удалить эти колонки?");
            model.addAttribute("submit","delSubmitColumns");   
            model.addAttribute("templateId",templateId);   
            model.addAttribute("location","showColumn.html?templateId="+templateId);            
            return "delInfoView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="delSubmitColumns",method = RequestMethod.POST)
    public String delSubmiteColumns(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("delete[]") int[] delete,
    @RequestParam("templateId") int templateId)  
    {
        if(token.getUserAccessLevel()==1)
        {   
            columnJDBC.delete(delete);          
            return "redirect:showColumn.html?templateId="+templateId;
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="changeColumn",method = RequestMethod.GET)
    public String ChangeColumn(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("column1") int column1,
    @RequestParam("column2") int column2,
    @RequestParam("templateId") int templateId)  
    {
        if(token.getUserAccessLevel()==1)
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
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("templateId") int templateId)  
    {  
        if(token.getUserAccessLevel()==1)
        {
            ColumnFieldsModel columnFields = new ColumnFieldsModel();
            List<AppFormColumns> appList=columnJDBC.getAppFormColumns(templateId);
            model.addAttribute("columns", appList);
            model.addAttribute("columnFields", columnFields);
            model.addAttribute("acceslevel", token.getUserAccessLevel());
            return "appFormView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    
    @RequestMapping(value="displayAppForm",method = RequestMethod.GET)
    public String displayAppForm(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("appId") int appId)  
    {
        if(token.getUserAccessLevel()==1||token.getUserAccessLevel()==2||(studentJDBC.getAppIdByUserId(token.getUserId())==appId))
        {
        DisplayAF map=columnJDBC.getAppColums(appId);
        File file = new File("/"+appId+".png");
        if (file.exists() && file.isFile()) 
        {
            model.addAttribute("path", "/"+appId+".png");
        }
        else
        {
            model.addAttribute("path","http://www.placehold.it/120x160/EFEFEF/AAAAAA&text=Photo");
        }
        
        try
        {
            model.addAttribute("hr",interviewJDBC.getInterviewForApp(1,appId));
        }
        catch(Exception e)
        {
            model.addAttribute("hr","Не записан на hr интервью");
        }
        try
        {
             model.addAttribute("interview",interviewJDBC.getInterviewForApp(2,appId));
        }
        catch(Exception e)
        {
           
            model.addAttribute("interview","Не записан на техническое интервью");
        
        }
        model.addAttribute("columnFields", map);
        return "displayAppFormView";
        }
        return "redirect:/";
    }
    
     @RequestMapping(value="printPdf",method = RequestMethod.GET)
    public String AppFormPDF(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("appId") int appId)  
    {
         if(token.getUserAccessLevel()==1||token.getUserAccessLevel()==2||(studentJDBC.getAppIdByUserId(token.getUserId())==appId))
        {
        
        DisplayAF map=columnJDBC.getAppColums(appId);
        File file = new File("/"+appId+".png");
        if (file.exists() && file.isFile()) 
        {
            model.addAttribute("path", "/"+appId+".png");
        }
        else
        {
            model.addAttribute("path","http://www.placehold.it/120x160/EFEFEF/AAAAAA&text=Photo");
        }
        try
        {
            model.addAttribute("hr",interviewJDBC.getInterviewForApp(1,appId));
        }
        catch(Exception e)
        {
            model.addAttribute("hr","Не записан на hr интервью");
        }
        try
        {
             model.addAttribute("interview",interviewJDBC.getInterviewForApp(2,appId));
        }
        catch(Exception e)
        {
           
            model.addAttribute("interview","Не записан на техническое интервью");
        
        }
        model.addAttribute("columnFields", map);
        return "printPdf";
        }
        return "redirect:/";
    }
    //submitForm
/*
    @RequestMapping(value="submitForm",method = RequestMethod.POST)
    public String submitForm(ModelMap model,
    @ModelAttribute("columnFields") ColumnFieldsModel columnFields)  
    {
        Map<Integer,String> map=columnFields.getMap();   
        
        Set s=map.entrySet();
        // Move next key and value of HashMap by iterator
        Iterator it=s.iterator();
        while(it.hasNext())
        {
            // key=value separator this by Map.Entry to get key and value
            Map.Entry m =(Map.Entry)it.next();
            // getKey is used to get key of HashMap
            int key=(Integer)m.getKey();
            // getValue is used to get value of key in HashMap
            String value=(String)m.getValue();
            if(!value.equalsIgnoreCase(""))
            {
                columnsJDBS.addColumnField(key, userPreferences.UserId, value, 1);
            }
        }
        return "appFormView";
    }  */
}
