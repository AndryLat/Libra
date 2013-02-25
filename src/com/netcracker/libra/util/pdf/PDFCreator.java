package com.netcracker.libra.util.pdf;

import com.itextpdf.text.DocumentException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
 
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.PrettyXmlSerializer;
import org.htmlcleaner.SimpleHtmlSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.htmlcleaner.Utils;
 
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


/**
 *
 * @author MorrDeck
 */
public class PDFCreator {
    private static ServletContext servletContext;

    public  ServletContext getServletContext() {
        return servletContext;
    }

    public  void setServletContext(ServletContext servletContext) {
        PDFCreator.servletContext = servletContext;
    }

//    public  void createFormPDF(int id, String url) {
//        try {
//            String outputFile = servletContext.getRealPath("WEB-INF/forms/form"+id+".pdf"); 
//            OutputStream os = new FileOutputStream(outputFile);
//            ITextRenderer renderer = new ITextRenderer();
//            renderer.setDocument(url);
//            renderer.layout();
//            renderer.createPDF(new FileOutputStream(outputFile));
//        } catch (DocumentException ex) {
//        } catch (IOException ex) {
//        }
//    }
    
    
    
    public static void createFormPDF(int id, String url) {
        PdfWriter pdfWriter = null;
        Document document = new Document();
        System.out.println(servletContext.getRealPath("WEB-INF/forms/form"+id+".pdf"));
        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(servletContext.getRealPath("WEB-INF/forms/form"+id+".pdf")));
            document.addCreationDate();
            document.setPageSize(PageSize.A4);
            document.open();
            String html = cleanProps(url);
            Reader sr = new StringReader(html);
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            worker.parseXHtml(pdfWriter, document, sr);
            document.close();
            pdfWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
 
    }
 
    private static String cleanHtml(final String url) throws MalformedURLException, IOException {
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean(new URL(url));
        node.traverse(new TagNodeVisitor() {
            public boolean visit(TagNode tagNode, HtmlNode htmlNode) {
                if (htmlNode instanceof TagNode) {
                    TagNode tag = (TagNode) htmlNode;
                    String tagName = tag.getName();
                    if ("img".equals(tagName)) {
                        String src = tag.getAttributeByName("src");
                        if (src != null) {
                            tag.setAttribute("src", Utils.fullUrl(url, src));
                        }
                    }
                }
                return true;
            }
        });
 
        SimpleHtmlSerializer serializer = new SimpleHtmlSerializer(cleaner.getProperties());
        StringWriter sw = new StringWriter();
        serializer.write(node, sw, "UTF-8");
        return sw.toString();
    }
 
    private static String cleanProps(final String url) {
        CleanerProperties props = new CleanerProperties();
        props.setTranslateSpecialEntities(true);
        props.setTransResCharsToNCR(true);
        props.setOmitComments(true);
        TagNode tagNode = null;
        try {
            tagNode = new HtmlCleaner(props).clean(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringWriter sw = new StringWriter();
        try {
            new PrettyXmlSerializer(props).write(tagNode, sw, "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }
}
