package com.netcracker.libra.util.mail;

import com.netcracker.libra.dao.VMTemplateJDBC;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Random;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *Class for sending e-mails
 * @author MorrisDeck
 */
@Service("SendMailService")
public class SendMailService {
    private static JavaMailSender mailSender;
    private static VelocityEngine velocityEngine;
    private static ServletContext servletContext;

    public  void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public  ServletContext getServletContext() {
        return servletContext;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public  JavaMailSender getMailSender() {
        return mailSender;
    }

    public  void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    
    /**
     * Send simple message
     * @param adress e-mail adress 
     * @param model data for template
     * @param template template name
     */
    public static void sendMail(final String adress, final Map model, final String template){
         MimeMessagePreparator preparator = new MimeMessagePreparator() {
         public void prepare(MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
            message.setTo(adress);
            String text = getTemplateContent(template, model);
            message.setText(text, true);
         }
      };
      mailSender.send(preparator);
    }
    
    /**
     * Send message with attachment
     * @param adress user's e-mail adress
     * @param model data for template
     * @param template template name
     * @param FileName name attachment file
     */
    public static void SendMailWithAttach(final String adress, final Map model, final String template, final String FileName){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
         public void prepare(MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true,"UTF-8");
            message.setTo(adress);
            String text = getTemplateContent(template, model);
            message.setText(text, true);
            File file = new  File(servletContext.getRealPath("WEB-INF/files/"+FileName));
            message.addAttachment(FileName,file) ;
         }
      };
      mailSender.send(preparator);
        
    }
    
    /**
     * Send message for several users
     * @param adress array of user's e-mails
     * @param model data for template
     * @param template template name
     */
    public static void SendMultiMail(final String[] adress, final Map model, final String template){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
         public void prepare(MimeMessage mimeMessage) throws Exception {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");
            message.setTo(adress);
            String text = getTemplateContent(template, model);
            message.setText(text, true);
         }
      };
      mailSender.send(preparator);
        
    }
    
    /**
     * get the contents of the template
     * @param templateName template name
     * @param model model with data for templater
     * @return
     * @throws IOException 
     */
    private static String getTemplateContent(String templateName, Map model) throws IOException{
        String htmlText = VMTemplateJDBC.getContentVMTemplate(templateName);
        Random rand = new  Random();
        String fileName = templateName + rand.nextInt(10000) + ".vm";
        String path = servletContext.getRealPath("WEB-INF/templates/" + fileName);
        
        // create temp file
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path),"UTF-8"));
        pw.write(htmlText);
        pw.flush();
        pw.close();
        
        htmlText =  VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,"/" + fileName , "UTF-8", model);
        
        // Destroy temp file
        File file = new File(path);
        file.delete();
        file = null;
        
        return htmlText;
        
    }
}
