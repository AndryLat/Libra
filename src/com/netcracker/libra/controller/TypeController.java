/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.TypeJDBC;
import com.netcracker.libra.dao.UserPreferences;
import com.netcracker.libra.model.InfoForDelete;
import com.netcracker.libra.model.Type;
import com.netcracker.libra.service.TemplateService;
import com.netcracker.libra.util.security.SessionToken;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Sashenka
 */
@Controller
@SessionAttributes({"regForm", "LOGGEDIN_USER"})
public class TypeController 
{
    TypeJDBC typeJDBC=new TypeJDBC();
    
    //@Autowired
    //UserPreferences userPreferences;
    
    /**
     * Обрабатывает запрос по добавлению нового типа.
     * @param name имя типа, который мы хотим добавить. Передается в POST запросе.
     */
    @RequestMapping(value="SubmitType", method= RequestMethod.POST)
    public ModelAndView processPost(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("name") String name,
            @RequestParam("description") String description)
    {
        if(token.getUserAccessLevel()==1)
        {
                String message=TemplateService.checkType(name);
                if(!message.equals(""))
                {
                    return message("<a href='addType.html'>Вернуться назад</a>", message, "Ошибка");
                } 
                typeJDBC.add(name,description);
                 return new ModelAndView("redirect:showTypes.html");
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    
    /**
     * Метод передает данные о существующих типах
     * Вызывается при запросе по ссылке "showTypes.html"
     */
    @RequestMapping("showTypes")
    public ModelAndView showTypes(@ModelAttribute("LOGGEDIN_USER") SessionToken token)
    {
        ModelAndView mav = new ModelAndView();
        if(token.getUserAccessLevel()==1)
        {
            mav.setViewName("showTypeView");
            List<Type> types=typeJDBC.getAllInfo();
            mav.addObject("types", types);
            return mav;
         } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    /**
     * Обрабатывает запрос по редактированию типа
     * @param name новое имя типа
     * @param selType номер типа, который мы хотим отредактировать. передается POST запросом.
     */
    @RequestMapping(value="showTypes", method= RequestMethod.POST)
    public ModelAndView editSummitPost(
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("selType") int selType,
    @RequestParam("description") String description)
    {
        ModelAndView mav = new ModelAndView();
        if(token.getUserAccessLevel()==1)
        {
            if(typeJDBC.existType(selType)==0)
                 {
                   return message("<a href='showTypes.html'>Посмотреть типы</a>","Такого типа нету","Ошибка");
                 }
                mav.setViewName("messageView");          
                typeJDBC.update(selType, description);
                 return new ModelAndView("redirect:showTypes.html");
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
    @RequestMapping(value="delType", method= RequestMethod.POST)
    public String delType(ModelMap model,
    @ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam(value="types[]",required=false ) int[] delete)  
    {
        if(delete==null)
        {
            return "redirect:showTypes.html";
        }
        if(token.getUserAccessLevel()==1)
        {
            List<InfoForDelete> info=typeJDBC.getInfoForDelete(delete);
            int infoSize=info.size();
            model.addAttribute("delete", delete);
            model.addAttribute("info", info);
            model.addAttribute("infoSize",infoSize);
            model.addAttribute("title","Удалить анкеты");
            model.addAttribute("h1","Вы действительно хотите удалить этот шаблон?");
            model.addAttribute("submit","delSubmitType");
            model.addAttribute("location","showTypes.html");
            return "delInfoView";
        }
        model.addAttribute("link", "<a href='/Libra/'>Вернуться назад</a>");
        model.addAttribute("message", "У вас нету прав на эту страницу");
        model.addAttribute("title", "Ошибка");
        return "messageView";
    }
    /**
     * Передается информацию на страницу для
     * предварительного просмотра типа перед удалением.
     * @param type номер типа
     */
    /*@RequestMapping(value="delType", method= RequestMethod.POST)
   public ModelAndView delType(@RequestParam("types[]") int[] types)
    {
        ModelAndView mav = new ModelAndView();
        if(userPreferences.accessLevel==1)
        {
           // List<InfoForDelete> info=new LinkedList<InfoForDelete>();
            for(int i=0;i<types.length;i++)
            {
                if(typeJDBC.existType(types[i])==0)
                {                
                    return message("<a href='showTypes.html'>Посмотреть все типы</a>", "Нету такого типа", "Ошибка"); 
                }                                    
            }
               // info=typeJDBC.getInfoForDelete(types); 
                mav.addObject("types", types);
                mav.setViewName("delTypeView");
                
                //getInfoUsers
                List<InfoForDelete> info=typeJDBC.getInfoForDelete(types);

                mav.addObject("info", info);
                    
                mav.addObject("infoSize",info.size());
                return mav;
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }*/
    /**
     * Обрабатывает запрос по удалению типа
     * @param typeId номер типа, который удаляем. Он передается POST запросм
     */
     @RequestMapping(value="delSubmitType", method= RequestMethod.POST)
    public ModelAndView delSubmitType(@ModelAttribute("LOGGEDIN_USER") SessionToken token,
    @RequestParam("delete[]") int[] delete)
    {
        if(token.getUserAccessLevel()==1)
        {             
            typeJDBC.delete(delete);
            return new ModelAndView("redirect:showTypes.html");
        } 
        else
        {
            return message("<a href='/Libra/'>Вернуться назад</a>","У Вас нету прав на эту страницу","Ошибка");
        }
    }
     
     public ModelAndView message(String link,String mes,String title)
     {
         ModelAndView mav=new ModelAndView();
         mav.setViewName("messageView");
         mav.addObject("link",link);
         mav.addObject("message",mes);
         mav.addObject("title",title);
         return mav;
     }
    @RequestMapping(value="addType")
    public String index(ModelMap model)  
    {
        return "addTypeView";
    }
}
