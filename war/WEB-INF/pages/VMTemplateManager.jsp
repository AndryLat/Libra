<%-- 
    Document   : VMTemplateManager
    Created on : 07.04.2013, 1:04:36
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

        <title>Libra: Менеджер шаблонов</title>
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
                                <th>Название</th>
                                <th>Описание</th>
                                <th>Автор</th>
                            </tr>
                        </thead>
                        <tbody>  
                            <c:forEach items="${templates}" var="t">
                                <tr>
                                    <td align="center">${t.getID()}</td>
                                    <td align="center">${t.getName()}</td>
                                    <td align="center">${t.getDescribe()}</td>
                                    <td align="center">${t.getAuthor()}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <br>
                    <center>
                        <form:form class="form-search" method="POST" action="/Libra/VMTemplateManagerDelete.html">
                            <span class="label">Название шаблона: </span>
                            <form:input type="text" class="input-medium search-query" path="name"/>
                            <button type="submit" class="btn btn-danger">Удалить</button>
                        </form:form>
                    </center>
                    <br>
                    ${text}
                </div>
            </div>
        </div>
    </body>
</html>
