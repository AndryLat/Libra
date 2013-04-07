package com.netcracker.libra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

/*import com.netcracker.libra.util.pdf.PDFCreator;
import com.netcracker.libra.util.security.SessionToken;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
*/
/**
 *
 * @author MorrisDeck
 */
@Controller
@SessionAttributes("LOGGEDIN_USER")
public class PDFController {
   /* @Autowired
    ServletContext servletContext;

    
    @RequestMapping(value="/getPDFFile",method = RequestMethod.GET)
    public void getPDFAppForm(@ModelAttribute("LOGGEDIN_USER") SessionToken token, @RequestParam("appId") int appId, HttpServletRequest request, HttpServletResponse response){
        try {
            Random rand = new Random();
            File file = new File(servletContext.getRealPath("WEB-INF/forms/ankete" + rand.nextInt(5000) + ".pdf"));
            String url = "http://localhost:8085/Libra/printPdf.html?appId=168";
            PDFCreator cr = new PDFCreator();
            cr.runConverter(url, file, request, response);      
            
            response.setContentType("file");
            response.setHeader("Content-Disposition", "attachment; filename=\"Ankete.pdf\"");
            
            FileCopyUtils.copy(FileCopyUtils.copyToByteArray(file),response.getOutputStream());
            
            file.delete();            
        } catch (IOException ex) {
            Logger.getLogger(com.netcracker.libra.util.pdf.PDFCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }*/
}
