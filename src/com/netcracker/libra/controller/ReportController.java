package com.netcracker.libra.controller;

import com.netcracker.libra.dao.ReportJDBC;
import com.netcracker.libra.model.Reports.AdvertisePOJO;
import com.netcracker.libra.model.Reports.ExcelReportPOJO;
import com.netcracker.libra.model.Reports.StudRecReportPOJO;
import com.netcracker.libra.service.ErrorService;
import com.netcracker.libra.util.security.SessionToken;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 * This controller is responsible for reporting
 *
 * @author MorrDeck
 */
@Controller
@RequestMapping(value = "/hr")
@SessionAttributes("LOGGEDIN_USER")
public class ReportController {

    @Autowired
    ServletContext servletContext;

    /**
     * Генерация отчета: График записи студентов
     *
     * @return
     */
    @RequestMapping(value = "/showStudentRecords", method = RequestMethod.GET)
    public static ModelAndView StudentsRecReport(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        if (token.getUserAccessLevel() == 1) {
            ModelAndView mav = new ModelAndView("StudentRecordsView");
            String data = "";

            List<StudRecReportPOJO> list = ReportJDBC.getStudRecValues();
            int[] val = new int[list.size()];
            String[] date = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                StudRecReportPOJO obj = list.get(i);
                date[i] = obj.getDate();
                val[i] = obj.getValue();
            }

            data = "[";
            for (int i = 0; i < val.length; i++) {
                data += "['" + date[i] + "'," + val[i] + "],";
            }
            data = data.substring(1, data.length() - 1);

            mav.addObject("data", data);
            return mav;
        } else {
            return ErrorService.getHRErrorPage();
        }
    }

    /**
     * Генерация отчета: зарегистрировались/пришли
     *
     * @return
     */
    @RequestMapping(value = "/showRegReport", method = RequestMethod.GET)
    public ModelAndView RegReport(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        if (token.getUserAccessLevel() == 1) {
            ModelAndView mav = new ModelAndView("RegReportView");
            Map map = ReportJDBC.getRegReportData();
            String data2 = "['Зарегестрировались'," + map.get("value1") + "],['Пришли'," + map.get("value2") + "]";
            mav.addObject("data", data2);
            return mav;
        } else {
            return ErrorService.getHRErrorPage();
        }
    }

    /**
     * Генерация отчета: эффективность рекламы
     *
     * @return
     */
    @RequestMapping(value = "/showAdvertise", method = RequestMethod.GET)
    public ModelAndView AdvertiseView(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        if (token.getUserAccessLevel() == 1) {
            ModelAndView mav = new ModelAndView("AdvertiseActivity");
            List<AdvertisePOJO> list = ReportJDBC.getAdvertiseActivity();
            int val[] = new int[list.size()];
            String lab[] = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                AdvertisePOJO obj = list.get(i);
                val[i] = obj.getValue();
                lab[i] = obj.getName();
            }

            String data = "[";
            for (int i = 0; i < lab.length; i++) {
                data += "['" + lab[i] + "'," + val[i] + "],";
            }
            data = data.substring(1, data.length() - 1);
            mav.addObject("data", data);
            return mav;
        } else {
            return ErrorService.getHRErrorPage();
        }
    }

    /**
     * Генерация отчета: все студенты пришли/не пришли
     *
     * @return
     */
    @RequestMapping(value = "/showStudentActivity", method = RequestMethod.GET)
    public ModelAndView StudentsActivity(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {
        if (token.getUserAccessLevel() == 1) {
            ModelAndView mav = new ModelAndView("StudentsActivityView");
            Map map = ReportJDBC.getStudentsActivityData();

            String data2 = "['Записалось', " + map.get("value1") + "],['Пришло', " + map.get("value2") + "]";
            mav.addObject("data", data2);
            return mav;
        } else {
            return ErrorService.getHRErrorPage();
        }
    }

    /**
     * Show JSP with a button to download the report
     *
     * @return
     */
    @RequestMapping(value = "/StudentList", method = RequestMethod.GET)
    public ModelAndView getDownloadExcelJSP(@ModelAttribute("LOGGEDIN_USER") SessionToken token) {        
        if (token.getUserAccessLevel() != 1) {
            return ErrorService.getHRErrorPage();
        } else {
            ModelAndView mav = new ModelAndView("DownloadExcelReport");
            return mav;
        }
    }

    /**
     * Generating report about all student and send to user excel file
     *
     * @param request
     * @param response
     * @return excel file
     */
    @RequestMapping(value = "/getExcelReport", method = RequestMethod.GET)
    public ModelAndView getExcelReport(@ModelAttribute("LOGGEDIN_USER") SessionToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token.getUserAccessLevel() == 1) {
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
                String headers[] = {"Фамилия", "Имя", "Отчество", "Почтовый адрес", "Номер телефона", "Университет", "Кафедра", "Факультет", "Курс"};

                format.setBackground(jxl.format.Colour.RED);
                for (int i = 0; i < headers.length; i++) {
                    label = new Label(i, 0, headers[i], format);
                    sheet.addCell(label);
                }

                format = new WritableCellFormat(font10);

                List list = ReportJDBC.getExcelReportData();
                for (int i = 0; i < list.size(); i++) {
                    ExcelReportPOJO obj = (ExcelReportPOJO) list.get(i);

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
                    label = new Label(5, i + 1, obj.getUniversity(), format);
                    sheet.addCell(label);
                    label = new Label(6, i + 1, obj.getDepartment(), format);
                    sheet.addCell(label);
                    label = new Label(7, i + 1, obj.getFaculty(), format);
                    sheet.addCell(label);
                    label = new Label(8, i + 1, Integer.toString(obj.getCourse()), format);
                    sheet.addCell(label);
                }
                workbook.write();
                workbook.close();

                response.setContentType("file");
                response.setHeader("Content-Disposition", "attachment; filename=\"AllStudentsReport" + strDate + ".xls\"");
                //send file in responce stream
                FileCopyUtils.copy(FileCopyUtils.copyToByteArray(new File(servletContext.getRealPath("AllStudentsReport" + strDate + ".xls"))), response.getOutputStream());

                //delete tempfile from server
                File file = new File(servletContext.getRealPath("AllStudentsReport" + strDate + ".xls"));
                file.delete();

            } catch (IOException ex) {
                return ErrorService.getErrorPage("Ошибка ввода/вывода при попытке формирования списка всех студентов");
            } catch (WriteException ex) {
                return ErrorService.getErrorPage("Ошибка записи при попытке формирования списка всех студентов ");
            }
        } else {
            return ErrorService.getHRErrorPage();
        }
        return null;
    }
}
