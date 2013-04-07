<%-- 
    Document   : ReportOnError
    Created on : 07.04.2013, 3:43:09
    Author     : MorrisDeck
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

        <title>Libra: Сообщить об ошибке</title>
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
                    <div class="page-header">
                        <h1>Вы нашли ошибку на нашем сайте? <small><br>Расскажите нам о ней</small></h1>
                    </div> 
                    <br>
                    <form:form method="POST" action="ReportOnErrorSend.html">
                        <table>
                            <tbody>
                                <tr>
                                    <td align="center"><span class="label label-info">Ваше имя:</span></td>
                                    <td><form:input class="search-query"  path="userName" /></td>
                                    <td align="left"><span class="label label-warning">Обязательно! Не более 100 символов.</span></td>
                                </tr>
                                <tr>
                                    <td align="center"><span class="label label-info">Суть ошибки:</span></td>
                                    <td><form:input class="search-query"  path="textError" /></td>
                                    <td align="left"><span class="label label-warning">Обязательно! Не более 200 символов.</span></td>
                                </tr>
                                <tr>
                                    <td align="right">&nbsp</td>
                                    <td align="center"><button class="btn btn-success" type="submit">Отправить</button></td>
                                    <td align="left">&nbsp</td>
                                </tr>
                            </tbody>
                        </table>
                    </form:form>
                    <br>
                    ${text}
                </div>
            </div>
        </div>
    </body>
</html>
