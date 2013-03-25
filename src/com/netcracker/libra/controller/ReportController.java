package com.netcracker.libra.controller;

import com.netcracker.libra.dao.ReportJDBC;
import com.netcracker.libra.model.ReportPOJOs;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author MorrDeck
 */
@Controller
public class ReportController {
    @Autowired
    ServletContext servletContext;

    /**
     * Генерация отчета: График записи студентов
     *
     * @return
     */
    @RequestMapping(value = "/showStudentRecords", method = RequestMethod.GET)
    public static ModelAndView StudentsRecReport() {
        ModelAndView mav = new ModelAndView("StudentRecordsView");
        String data = "";

        List<ReportPOJOs.StudRecReportPOJO> list = ReportJDBC.getStudRecValues();
        int[] val = new int[list.size()];
        String[] date = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ReportPOJOs.StudRecReportPOJO obj = list.get(i);
            date[i] = obj.getDate();
            val[i] = obj.getValue();
        }
        //int val[] = {15, 24, 11, 18, 10};
        //String lab[] = {"2012-04-16", "2012-04-17", "2012-04-18", "2012-04-19", "2012-04-20"};// y/m/d
        data = "[";
        for (int i = 0; i < val.length; i++) {
            data += "['" + date[i] + "'," + val[i] + "],";
        }
        data = data.substring(1, data.length() - 1);

        mav.addObject("data", data);
        return mav;
    }

    /**
     * Генерация отчета: зарегистрировались/пришли
     *
     * @return
     */
    @RequestMapping(value = "/showRegReport", method = RequestMethod.GET)
    public ModelAndView RegReport() {
        ModelAndView mav = new ModelAndView("RegReportView");
        //Map map = ReportJDBC.getRegReportData();
        //String data2 = "['Зарегестрировались'," + map.get("value1") + "],['Пришли'," + map.get("value2") + "]";
        String data = "['Зарегестрировались',78],['Пришли',52]";
        mav.addObject("data", data);
        return mav;
    }

    /**
     * Генерация отчета: эффективность рекламы
     *
     * @return
     */
    @RequestMapping(value = "/showAdvertise", method = RequestMethod.GET)
    public ModelAndView AdvertiseView() {
        ModelAndView mav = new ModelAndView("AdvertiseActivity");

        int val[] = {15, 11, 24, 10, 18};
        String lab[] = {"Друг привел", "Флаер", "На стенде в УЗ", "Телереклама", "Другое"};
        String data = "[";
        for (int i = 0; i < lab.length; i++) {
            data += "['" + lab[i] + "'," + val[i] + "],";
        }
        data = data.substring(1, data.length() - 1);
        mav.addObject("data", data);

        return mav;
    }

    /**
     * Генерация отчета: все студенты пришли/не пришли
     *
     * @return
     */
    @RequestMapping(value = "/showStudentActivity", method = RequestMethod.GET)
    public ModelAndView StudentsActivity() {
        ModelAndView mav = new ModelAndView("StudentsActivityView");
        //Map map = ReportJDBC.getStudentsActivityData();
        //String data2 = "['Записалось', " + map.get("value1") + "],['Пришло', " + map.get("value2") + "]";
        String data = "['Записалось', 74],['Пришло', 52]";
        mav.addObject("data", data);
        return mav;
    }
    
    @RequestMapping(value = "/StudentList", method = RequestMethod.GET)
    public ModelAndView getDownloadExcelJSP(){
        ModelAndView mav = new ModelAndView("DownloadExcelReport");
        return mav;
    }
    
    
    /**
     * Generating report about all student and send to user excel file
     *
     * @param request
     * @param response
     * @return excel file
     */
    @RequestMapping(value = "/getExcelReport", method = RequestMethod.GET)
    public ModelAndView getExcelReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            Date date = new Date();
            
            String strDate = String.valueOf(date.getYear()) + String.valueOf(date.getDay()) + String.valueOf(date.getMonth());
            // create workbook
            WritableWorkbook workbook = Workbook.createWorkbook(new File(servletContext.getRealPath("AllStudentsReport" + strDate + ".xls")));
            // create sheet
            WritableSheet sheet = workbook.createSheet("Report Sheet", 0);
            // create styles
            WritableFont font13 = new WritableFont(WritableFont.ARIAL, 13);
            WritableFont font10 = new WritableFont(WritableFont.ARIAL, 10);
            WritableCellFormat format = new WritableCellFormat(font13);

            Label label;
            String headers[] = {"LastName", "FirstName", "Patronimyc", "Email", "PhoneNumber", "Course"};

            format.setBackground(jxl.format.Colour.RED);
            for (int i = 0; i < headers.length; i++) {
                label = new Label(i, 0, headers[i], format);
                sheet.addCell(label);
            }

            format = new WritableCellFormat(font10);

            List list = ReportJDBC.getExcelReportData();
            for (int i = 0; i < list.size(); i++) {
                ReportPOJOs.ExcelReportPOJO obj = (ReportPOJOs.ExcelReportPOJO) list.get(i);

                label = new Label(0, i + 1, obj.getLastName(), format);
                sheet.addCell(label);
                label = new Label(1, i + 1, obj.getFirstName(), format);
                sheet.addCell(label);
                label = new Label(2, i + 1, obj.getPatronymic(), format);
                sheet.addCell(label);
                label = new Label(3, i + 1, obj.getEmail(), format);
                sheet.addCell(label);
                label = new Label(4, i + 1, obj.getPhoneNumber(), format);
                sheet.addCell(label);
                label = new Label(5, i + 1, Integer.toString(obj.getCourse()), format);
                sheet.addCell(label);
            }
            workbook.write();
            workbook.close();

            response.setContentType("file");
            response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentsReport" + strDate + ".xls\"");
            //send file in responce stream
            FileCopyUtils.copy(FileCopyUtils.copyToByteArray(new File(servletContext.getRealPath("AllStudentsReport" + strDate + ".xls"))), response.getOutputStream());
            return null;
        } catch (IOException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (WriteException ex) {
            Logger.getLogger(ReportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
