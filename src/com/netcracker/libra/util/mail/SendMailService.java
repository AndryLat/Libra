/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.libra.util.mail;

import com.netcracker.libra.dao.VMTemplateJDBC;
import java.io.File;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author MorrisDeck
 */
//Class for sending e-mails
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
            String text = VelocityEngineUtils.mergeTemplateIntoString(
               velocityEngine,VMTemplateJDBC.getContentVMTemplate(template), "UTF-8", model);
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
            String text = VelocityEngineUtils.mergeTemplateIntoString(
               velocityEngine,VMTemplateJDBC.getContentVMTemplate(template), "UTF-8", model);
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
            String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,VMTemplateJDBC.getContentVMTemplate(template), "UTF-8", model);
            message.setText(text, true);
         }
      };
      mailSender.send(preparator);
        
    }
    
}
