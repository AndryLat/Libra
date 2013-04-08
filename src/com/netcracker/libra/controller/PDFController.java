package com.netcracker.libra.controller;


import com.netcracker.libra.util.pdf.PDFCreator;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author MorrisDeck
 */
@Controller
public class PDFController {
    @Autowired
    ServletContext servletContext;

    /*
     * Loads the completed form in PDF to user's computer
     */
    @RequestMapping(value="/getPDFFile",method = RequestMethod.GET)
    public void getPDFAppForm( @RequestParam("appId") int appId, HttpServletRequest request, HttpServletResponse response){
        try {
            System.out.println(request.getSession().getId());
           System.out.println(request.getSession(true).getId()); 
            Random rand = new Random();
            File file = new File(servletContext.getRealPath("WEB-INF/forms/ankete" + rand.nextInt(5000) + ".pdf"));
            String url = "http://localhost:8085/Libra/printPdf.html;jsessionid=" + request.getSession().getId() + "?appId=" + appId;
            PDFCreator cr = new PDFCreator();
            cr.runConverter(url, file);      
            
            response.setContentType("file");
            response.setHeader("Content-Disposition", "attachment; filename=\"Ankete.pdf\"");
            
            FileCopyUtils.copy(FileCopyUtils.copyToByteArray(file),response.getOutputStream());
            
            file.delete();            
        } catch (IOException ex) {
            Logger.getLogger(com.netcracker.libra.util.pdf.PDFCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
