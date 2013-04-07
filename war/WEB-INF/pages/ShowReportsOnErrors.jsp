<%-- 
    Document   : ShowReportsOnErrors
    Created on : 07.04.2013, 3:44:10
    Author     : MorrisDeck
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
    <!--<![endif]-->
    <head>
        <jsp:include page="resources.jsp" />

        <title>Libra: Список ошибок</title>
    </head>

    <body>
        <div class="navmenu">
            <jsp:include page="navbar.jsp" />
        </div>

        <div class="container-fluid">
            <div class="row-fluid">

                <div class="sidebar">
                    <jsp:include page="sidebar.jsp" />
                </div>

                <div class="span9">
                    <table  class="table table-bordered table-hover">
                        <caption><strong>Список созданных шаблонов</strong></caption>
                        <thead>
                            <tr>
                                <th>Номер</th>
                                <th>Суть ошибки</th>
                                <th>Автор</th>
                                <th>Состояние</th>
                            </tr>
                        </thead>
                        <tbody>  
                            <c:forEach items="${errorList}" var="e">
                                <tr>
                                    <td align="center">${e.getId()}</td>
                                    <td align="center">${e.getText()}</td>
                                    <td align="center">${e.getAuthor()}</td>
                                    <td align="center">
                                        <c:if test="${e.getStatus()!=0}">Исправлено</c:if>
                                        <c:if test="${e.getStatus()==0}">Аkтивно</c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <center>
                        <form:form class="form-search" method="POST" action="ShowReportOnErrorFix.html">
                            <span class="label">Номер ошибки:</span><form:input class="search-query"  path="id" />
                            <button type="submit" class="btn btn-success">Исправить</button>
                        </form:form>
                            <br>
                        <form:form class="form-search" method="POST" action="ShowReportOnErrorClear.html">
                            <span class="label">Очистка таблицы от исправленных ошибок:</span>
                            <button type="submit" class="btn btn-success">Очистить</button>
                        </form:form>    
                            
                    </center>
                    <br>
                    ${text}
                </div>
            </div>
        </div>
    </body>
</html>
