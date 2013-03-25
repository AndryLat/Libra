/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.controller;

import com.netcracker.libra.dao.HrJDBC;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Yuliya
 */
@Controller
public class LanguageController {
    HrJDBC hr = new HrJDBC();
    
   @RequestMapping(value="hr/showLanguages")
      public ModelAndView showAllLangs(){
          List<Map<String,Object>> langs=hr.getAllLanguages();
          ModelAndView mav = new ModelAndView();
          mav.addObject("languages", langs);
          mav.setViewName("hr/showLanguages");
          return mav;
      }
      @RequestMapping(value="/hr/delLanguage", method=RequestMethod.GET)
      public ModelAndView delLang(@RequestParam("languageId") int languageId){
          List<Map<String,Object>> langs = hr.showLangById(languageId);
          ModelAndView mav = new ModelAndView();
          mav.addObject("languages", langs);
          return mav;
    }
      @RequestMapping(value="/hr/deletedLang", method=RequestMethod.POST)
      public ModelAndView deletedLang(@RequestParam("languageId") int languageId){
          hr.deleteLanguage(languageId);
          ModelAndView mav = new ModelAndView();
          List<Map<String,Object>> langs = hr.getAllLanguages();
          mav.addObject("languages", langs);
          mav.addObject("message", "Язык успешно удален!");
          mav.setViewName("/hr/showLanguages");
          return mav;
    }
      
      @RequestMapping(value="/hr/addLanguage", method= RequestMethod.GET)
      public ModelAndView addLang(
      org.springframework.web.context.request.WebRequest webRequest){
        String langSearch = webRequest.getParameter("langSearch");
        String textbox = webRequest.getParameter("textBox");
        ModelAndView mav = new ModelAndView();
        List<Map<String,Object>> langs=null;
        try{
            int langSearchInt=Integer.parseInt(langSearch);
            mav.addObject("textBox", textbox);
            mav.addObject("langSearchInt", langSearch);
            if (langSearchInt==0){
                langs = hr.getAllLanguages();
            }
            if (langSearchInt==1){
            try{
            int n=Integer.parseInt(textbox);
             langs = hr.showLangById(n);
            }
            catch(Exception e){
                mav.addObject("errorMessage", "Номер языка иммет неверный формат!");
                langs = hr.getAllLanguages();
                    }
                }
            if (langSearchInt==2){
            langs = hr.showLangByName(textbox);
            }
        }
        catch(Exception ex){
            langs = hr.getAllLanguages();
        }
        finally{
            mav.addObject("languages", langs);
            mav.setViewName("/hr/addLanguage");
            return mav;
    }
    }
      @RequestMapping(value="hr/addLanguageAdded", method= RequestMethod.GET)
      public ModelAndView addedLang(
      @RequestParam("langName") String name){
        ModelAndView mav = new ModelAndView();
        if (name.equals("")) {
            mav.addObject("errorMessage", "Название языка имеет неверный формат!");
            mav.setViewName("/hr/addLanguage");
            List<Map<String,Object>> langs = hr.getAllLanguages();
            mav.addObject("languages", langs);
            return mav;
        }
        hr.createLanguage(name);
        List<Map<String,Object>> langs = hr.getAllLanguages();
        mav.addObject("languages", langs);
        mav.addObject("message", "Язык успешно добавлен!");
        mav.setViewName("/hr/showLanguages");
        return mav;
    }
      
      @RequestMapping(value="/hr/deletedLanguage", method=RequestMethod.POST)
      public ModelAndView deletedUnivers(@RequestParam("languageId") int languageId){
          hr.deleteLanguage(languageId);
          ModelAndView mav = new ModelAndView();
          List<Map<String,Object>> langs = hr.getAllLanguages();
          mav.addObject("languages", langs);
          mav.addObject("message", "Язык успешно удален!");
          mav.setViewName("/hr/showLanguages");
          return mav;
    }
    
      @RequestMapping(value="hr/editLanguage", method = RequestMethod.GET)
      public ModelAndView editLang(@RequestParam("languageId") int languageId){
        ModelAndView mav = new ModelAndView();
        mav.addObject("languages", hr.showLangById(languageId));
        return mav;
    }
      @RequestMapping(value="hr/editedLang", method = RequestMethod.POST)
      public ModelAndView editedLang(@RequestParam("languageId") int languageId,
      @RequestParam("languageName") String languageName){
          ModelAndView mav = new ModelAndView();
            try{
                if (languageName.equals("")){
                    mav.addObject("languages", hr.showLangById(languageId));
                    mav.addObject("errorMessage", " Правка не удалась, язык имеет неверный формат");
                    mav.setViewName("/hr/editLanguage"); 
                    return mav;
                    }
                hr.updateLanguage(languageId, languageName);
                List<Map<String,Object>> langs = hr.getAllLanguages();
                mav.addObject("languages", langs);
                mav.addObject("message", "Язык успешно изменен!");
                mav.setViewName("hr/showLanguages");
                return mav;
                    }
                catch(Exception ex){
                    mav.addObject("errorMessage", "Правка не выполнена!");
                    mav.setViewName("/hr/editLanguage"); 
                    return mav;
                }
        }
      
      @RequestMapping(value="hr/showLanguagesSearch", method= RequestMethod.GET)
      public ModelAndView searchUnivs(
      @RequestParam("textBox") String name, 
      @RequestParam("langSearch") int langSearchInt){
        ModelAndView mav = new ModelAndView();
        mav.addObject("textBoxString", name);
        mav.addObject("langSearchInt", langSearchInt);
        List<Map<String,Object>> langs=null;
        if (langSearchInt==1){
            try{
            int n=Integer.parseInt(name);
            langs = hr.showLangById(n);
            }
            catch(Exception e){
                mav.addObject("errorMessage", "№ языка имеет неверный формат!");
                langs = hr.getAllLanguages();
            }
        }
        if (langSearchInt==2){
            langs = hr.showLangByName(name);
        }
        if(langSearchInt==0){
            langs = hr.getAllLanguages();
        }
        mav.addObject("languages", langs);
        return mav;
    }
    
}
