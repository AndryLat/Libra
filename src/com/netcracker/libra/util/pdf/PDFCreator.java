
package com.netcracker.libra.util.pdf;

import javax.servlet.ServletContext;
import org.springframework.stereotype.Service;

/*import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;*/

/**
 *
 * @author MorrisDeck
 */
@Service("PDFService")
public class PDFCreator {
    
    private static ServletContext servletContext;

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

   /* protected Dimension format = PD4Constants.A4;
    protected boolean landscapeValue = false;
    protected int topValue = 10;
    protected int leftValue = 10;
    protected int rightValue = 10;
    protected int bottomValue = 10;
    protected String unitsValue = "mm";
    protected String proxyHost = "";
    protected int proxyPort = 0;
    protected int userSpaceWidth = 780;

    public void runConverter(String urlstring, File output, HttpServletRequest request, HttpServletResponse response) throws IOException {


        if (urlstring.length() > 0) {
            if (!urlstring.startsWith("http://") && !urlstring.startsWith("file:")) {
                urlstring = "http://" + urlstring;
            }

            java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

            if (proxyHost != null && proxyHost.length() != 0 && proxyPort != 0) {
                System.getProperties().setProperty("proxySet", "true");
                System.getProperties().setProperty("proxyHost", proxyHost);
                System.getProperties().setProperty("proxyPort", "" + proxyPort);
            }

            PD4ML pd4ml = new PD4ML();
            pd4ml.useHttpRequest(request, response);
            pd4ml.setSessionID(request.getSession().getId());
            pd4ml.useServletContext(servletContext);

            try {
                pd4ml.setPageSize(landscapeValue ? pd4ml.changePageOrientation(format) : format);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (unitsValue.equals("mm")) {
                pd4ml.setPageInsetsMM(new Insets(topValue, leftValue,
                        bottomValue, rightValue));
            } else {
                pd4ml.setPageInsets(new Insets(topValue, leftValue,
                        bottomValue, rightValue));
            }

            pd4ml.setHtmlWidth(userSpaceWidth);
            pd4ml.enableDebugInfo();
            pd4ml.overrideDocumentEncoding("UTF-8");
            pd4ml.useTTF(servletContext.getRealPath("resources/fonts"), true);
            pd4ml.setDefaultTTFs("Times New Roman", "Arial", "Courier New");
            pd4ml.render(urlstring, fos);
        }
    }*/
}