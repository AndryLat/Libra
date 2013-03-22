/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import org.springframework.web.servlet.ModelAndView;
import com.netcracker.libra.model.Template;
import com.netcracker.libra.dao.TemplateJDBC;
import com.netcracker.libra.dao.TypeJDBC;
import com.netcracker.libra.dao.UserPreferences;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.netcracker.libra.service.TemplateService;
import org.springframework.web.bind.annotation.RequestParam;
import com.netcracker.libra.model.InfoForDelete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
/**
 *
 * @author Sashenka
 */
@Controller
public class TemplateController
{    
    TemplateJDBC templateJDBC=new TemplateJDBC();
    
    @Autowired
    UserPreferences userPreferences;
    /**
     * Метод обрабатывает запрос по добавлению нового шаблона.
     * @param name имя нового шаблона
     */
    @RequestMapping(value="SubmitTemplate", method= RequestMethod.POST)
    public ModelAndView addPost(@RequestParam("name") String name)
    {
        ModelAndView mv = new ModelAndView();
        if(userPreferences.accessLevel==1)
        {
            mv.setViewName("messageView");
            String message=TemplateService.checkTemplate(name);
            if(!message.equals(""))
            {
                 return message("template.html", message, "Ошибка");
            }
            Template lastTemplate=templateJDBC.getTemplate(templateJDBC.add(name));
            return new ModelAndView("redirect:showTemplates.html");
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
      
    /**
     * Устанавливает активный шаблон
     * @param activeTemplate номер шаблона, который мы хотим установить активным
     */
    @RequestMapping(value="ActiveTemplate", method= RequestMethod.POST)
    public ModelAndView addActivePost(@RequestParam("activeTemplate") int activeTemplate)
    {
        if(userPreferences.accessLevel==1)
        {
            templateJDBC.setActive(activeTemplate);
            return new ModelAndView("redirect:showTemplates.html");
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    /**
     * Метод для отображения всех шаблонов. Он передает в вид список шаблонов.
     * При введение ссылки showTemplates.html вызывается этот метод.
     */
    @RequestMapping("showTemplates")
    public ModelAndView showTemplate()
    {
       if(userPreferences.accessLevel==1)
        { 
            ModelAndView mav = new ModelAndView();
            mav.setViewName("showTemplatesView");
            mav.addObject("activeTemplate",templateJDBC.getActive());
            mav.addObject("templates", templateJDBC.getAll());
            return mav;
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    /**
     * Метод который обрабатывает запрос на редактирование шаблона.
     * @param name новое имя шаблона, которое передается POST запросом
     * @param selTemplate номер шаблона, который мы хотим отредактировать, 
     * который передается POST запросом
     */
    @RequestMapping(value="showTemplates", method= RequestMethod.POST)
    public ModelAndView editPost(@RequestParam("name") String name,
    @RequestParam("selTemplate") int selTemplate)
    {
        ModelAndView mav = new ModelAndView();
        if(userPreferences.accessLevel==1)
        {
            mav.setViewName("messageView");
            String message=TemplateService.checkTemplate(name);
            if(!message.equals(""))
            {
                return message("<a href='showTemplates.html'>Назад</a>", message, "Ошибка");
            }
            if(templateJDBC.existTemplate(selTemplate)==0)
            {
                return message("<a href='showTemplates.html'>Посмотреть все типы</a>", "Такого шаблона нету", "Ошибка"); 
            }
            templateJDBC.update(selTemplate, name);
            return new ModelAndView("redirect:showTemplates.html");
        } 
         else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    
    @RequestMapping(value="delTemplate", method= RequestMethod.POST)
    public String delTemplate(ModelMap model,
    @RequestParam(value="templates[]",required=false ) int[] delete)  
    {
        if(delete==null)
        {
            return "redirect:showTemplates.html";
        }
        if(userPreferences.accessLevel==1)
        {
            List<InfoForDelete> info=templateJDBC.getInfoForDelete(delete);
            int infoSize=info.size();
            model.addAttribute("delete", delete);
            model.addAttribute("info", info);
            model.addAttribute("infoSize",infoSize);
            model.addAttribute("title","Удалить анкеты");
            model.addAttribute("h1","Вы действительно хотите удалить этот шаблон?");
            model.addAttribute("submit","delSubmitTemplate");
            model.addAttribute("location","showTemplates.html");
            return "delInfoView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    /**
     * Метод, который передает в вид информацию о шаблоне(для предварительного просмотра),
     * перед удаленим удалить
     * @param templateId номер шаблона, который хотим удалить. Передается GET запросом
     */
   /* @RequestMapping(value="delTemplate", method= RequestMethod.POST)
    public ModelAndView delTemplate(@RequestParam(value="templates[]",required=false ) int[] templates)
  //  public ModelAndView delTemplate()
    {
        if(templates==null)
        {
            return showTemplate();
        }
        ModelAndView mav = new ModelAndView();
        if(userPreferences.accessLevel==1)
        {        
            for(int i=0; i<templates.length;i++)
            {
                if(templateJDBC.existTemplate(templates[i])==0)
                {                
                    return message("<a href='showTemplates.html'>Назад</a>", "Нету такого шаблона", "Ошибка"); 
                }  
            }
            List<InfoForDelete> info=templateJDBC.getInfoForDelete(templates);
            int infoSize=info.size();
            mav.setViewName("delTemplateView");
            mav.addObject("templates", templates);
            mav.addObject("info", info);
            mav.addObject("infoSize",infoSize);
            return mav;
          } 
         else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }*/
    /**
     * Обрабатывает запрос по удалению шаблона
     * @param templateId номер шаблона, который удаляется. передается POST запросом 
     */
     @RequestMapping(value="delSubmitTemplate", method= RequestMethod.POST)
    public ModelAndView delSubmitTemplate(@RequestParam("delete[]") int[] delete)
    {
        if(userPreferences.accessLevel==1)
        {
            for(int i=0;i<delete.length;i++)
            {
                if(templateJDBC.existTemplate(delete[i])==0)
                {
                    return message("<a href='showTemplates.html'>Посмотреть все типы</a>", "Такого шаблона нету", "Ошибка"); 
                }
                templateJDBC.delete(delete[i]);
            }
            return new ModelAndView("redirect:showTemplates.html");
        } 
         else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    // ColumnsJDBC columnsJDBS=new ColumnsJDBC();
     TypeJDBC typeJDBC=new TypeJDBC();
     /*@RequestMapping(value="appForm", method= RequestMethod.GET)
     public ModelAndView showTemplateById(@RequestParam("template") int template)
     {
         ModelAndView mav=new ModelAndView();
         List<AppFormTopics> list=topicJDBC.getAppFormTopics(template);
         Map<String, String> map = new HashMap<String, String>();
         mav.addObject("topics", list);
         mav.addObject("columns", columnsJDBS.getAllByemplate(template));
         mav.addObject("types", typeJDBC.getAll());
        // mav.addObject("map", map);
         mav.setViewName("appFormView");
         return mav;
     }*/
    /* @RequestMapping(value="appForm", method= RequestMethod.GET)
    public ModelAndView showTemplateById(@RequestParam("template") int template)
    {            
        ModelAndView mav = new ModelAndView();
        List<AppFormTopics> list=topicJDBC.getAppFormTopics(template);
        ColumnFieldsModel columnFields = new ColumnFieldsModel();
        mav.addObject("topics", list);
        mav.addObject("columns", columnsJDBS.getAllByTemplate(template));
       // mav.addObject("types", typeJDBC.getAll());
        List<Type> types=typeJDBC.getAll();
        Map<Integer,List<String>> enums = new HashMap<Integer,List<String>>();
        for(int i=0;i<types.size();i++)
        {
            Type t=types.get(i);
            if(t.getTypeName().equalsIgnoreCase("enum")||t.getTypeName().equalsIgnoreCase("multienum"))
            {
               enums.put(t.getTypeId(), TemplateService.getEnumTypes(t.getDescription()));
            }
        }
        mav.addObject("enums", enums);

        mav.addObject("columnFields", columnFields);
        mav.setViewName("appFormView");
        return mav;
    }

    @RequestMapping(value="appForm", method=RequestMethod.POST)
    public ModelAndView addColumnFields(@ModelAttribute("columnFields") ColumnFieldsModel columnFields)
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
        return message("<a href='/Libra/'>Вернуться назад</a>","Вы заполнили анкету","Успех");
    }
*/
     public ModelAndView message(String link,String message,String title)
     {
         ModelAndView mav=new ModelAndView();
         mav.setViewName("messageView");
         mav.addObject("link",link);
         mav.addObject("message",message);
         mav.addObject("title",title);
         return mav;
     }
}
