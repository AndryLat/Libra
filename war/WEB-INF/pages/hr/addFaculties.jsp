<%-- 
    Document   : addFaculties
    Created on : 22.02.2013, 16:36:15
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Управление факультетами - Добавление факультетов</title>
    </head>
    <body>
        <center>
            <form name="Form" action="addFacultiesAdded.html" method="get">
                Введите название факультета: 
                <input type="text" name ="facultyName">
                Выберите университет: 
                <select name="univ" id="univ">
                    <c:forEach items="${univers}" var="u">
                    <option value="${u.universityId}"> ${u.universityName}  </option> 
                    </c:forEach>  
                 </select> 
                <input type="submit" value="Добавить" name="add">
            </form>
            <br>     
            <center> 
            <form name="myForm" action="showFacultiesSearch.html" method="get">
        <select name="facultySearch">
            <option value="0"> - </option>
            <option value="1">№ факультета </option>
            <option value="2">Факультет</option>
            <option value="3">Университет</option>
        </select>
        <input type="text" name ="textBox">
        <input type="submit" value="Показать" name="search">
            </form>
        <br><br>
        <center>
        <table border ="1">
            <tr>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>№ факультета </td>
                <td>Факультет</td>
                <td>Университет</td>
            </tr>
           <c:forEach items="${facts}" var="f">
                <tr>
                    <td><a href= "delFaculty.html?facultyId=<c:out value='${f.facultyId}'/> ">удалить</a> </td>
                    <td><a href="editFaculty.html?facultyId=<c:out value='${u.facultyId}'/>">править</a></td>
                    <td><c:out value="${f.facultyId}"/></td>
                    <td><c:out value="${f.facultyName}"/></td>
                    <td><c:out value="${f.universityName}"/></td>
                </tr>
        </c:forEach>
        </table>
    </body>
</html>