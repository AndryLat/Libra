<%-- 
    Document   : ErrorPage
    Created on : 07.04.2013, 2:40:43
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

        <title>Libra: ErrorPage</title>
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
                        <h1>Уп-с! Произошла ошибка :( <br> <small> Приносим свои извинения. </small></h1>
                        <h1><small> Если ошибка повторилась более 3-х раз - известите нас о ней (кликните сверху "Сообщить об ошибке")</small></h1>
                    </div>  
                    ${errorText}
                </div>
            </div>
        </div>
    </body>
</html>
