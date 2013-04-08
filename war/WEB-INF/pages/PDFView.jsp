<%-- 
    Document   : PDFView
    Created on : 07.04.2013, 21:20:05
    Author     : MorrisDeck
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% pageContext.setAttribute("curLevel", 1);%> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Анкета ${columnFields.getAppId()}</title>
    </head>
    <body style="width: 100%; height: 100%; margin: 2em;">
        <div style="position: relative; width: 1000px; height: auto; margin: 0 auto; font-family: tahoma, arial, verdana, sans-serif, 'Lucida Sans'; font-size: 14px;">
            <div style="position: relative; display: inline-block; margin: 10px 0;">
                <div style="width: 200px; height: 200px; float: left;">
                    <img src="<c:out value='${path}'/>" alt="" style="width: 150px; height: 200px; margin: 0 auto;" />
                </div>
                <div style="width: 800px; height: 200px; float: left;">
                    <div style="font-size: 22px; font-weight: bold; color: #0099ff;font-family: tahoma, arial, verdana, sans-serif, 'Lucida Sans';">${columnFields.getFio()}</div>
                    <div style="margin: 5px 0;"><span></span>${interview}</div>
                    <div style="margin: 5px 0;"> <span>${hr}</span></div>
                    <div style="margin: 5px 0;"><span>№ анкеты:</span> <span>${columnFields.getAppId()}</span></div>
                    <div style="margin: 5px 0;"><span>email:</span> <span>${columnFields.getEmail()}</span></div>
                    <div style="margin: 5px 0;"><span>Номер телефона:</span> <span>${columnFields.getPhoneNumber()}</span></div>
                    <div style="margin: 5px 0;"><span>Я узнал о центре:</span> <span>${columnFields.getAdvertisingName()} 
                                <c:if test="${columnFields.getAdvertisingComment()!=null}">
                                    (${columnFields.getAdvertisingComment()})
                                </c:if></span></div>
                </div>
            </div>
                                
            <div style="position: relative; margin: 10px 0;">
                <div style="font-size: 20px; font-weight: bold; color: #0099ff;text-align: left;font-family: tahoma, arial, verdana, sans-serif, 'Lucida Sans';">ОБРАЗОВАНИЕ</div>
                <div style="margin: 5px 0;"><span>ВУЗ:</span> <span>${columnFields.getUniversityName()}</span></div>
                <div style="margin: 5px 0;"><span>Факультет:</span> <span>${columnFields.getFacultyName()}</span></div>
                <div style="margin: 5px 0;"><span>Кафедра:</span> <span>${columnFields.getDepartmentName()}</span></div>
                <div style="margin: 5px 0;"><span>Курс:</span> <span>${columnFields.getCourse()}</span></div>
                <div style="margin: 5px 0;"><span>Год окончания:</span> <span>${columnFields.getGraduated()}</span></div>
            </div>           
            
            <c:forEach items="${columnFields.getCf()}" var="contactMap" varStatus="status">

                <c:if test="${contactMap.getLevel()==1}">
                    <div style="font-size: 20px; font-weight: bold; color: #0099ff;text-align: left; font-family: tahoma, arial, verdana, sans-serif, 'Lucida Sans';">
                        <h4><strong>${contactMap.getName()}</strong></h4>
                    </div>
                </c:if>
                <c:if test="${contactMap.getLevel()!=1}">
                    <c:forEach var="i" begin="${contactMap.getLevel()}" end="${curLevel-1}"></ul></c:forEach>
                <c:forEach var="i" begin="${curLevel+1}" end="${contactMap.getLevel()}"><ul></c:forEach>
                    <c:set var="curLevel" value="${contactMap.getLevel()}" />
                    <li>${contactMap.getName()}${contactMap.getValue()}</li>
                    </c:if>

            </c:forEach>           
            
            
            <div style="font-size: 20px; font-weight: bold; color: #0099ff;text-align: center;font-family: tahoma, arial, verdana, sans-serif, 'Lucida Sans';">СОГЛАШЕНИЕ</div>
            <div style="text-align: center;">Я даю согласие на хранение, обработку и использование моих данных с целью возможного обучения и трудоустройства в компании НЕТКРЕКЕР на данный момент и в будущем</div>
            <br>
            <hr>
            <div style="text-align: center;"><font size="small">(ФИО, подпись)</font></div>
            <br>
            <table border="2" style="width: 100%">
                <tr>
                    <th>HR или Аналитик</th>
                    <th>Разработчик</th>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </table>  
            
        </div>
    </body>
</html>
