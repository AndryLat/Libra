<%-- 
    Document   : DownloadExcelReport
    Created on : 21.03.2013, 0:31:10
    Author     : MorrisDeck
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
    <!--<![endif]-->
    <head>
        <jsp:include page="resources.jsp" />

        <title>Libra: Список всех студентов</title>
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
                    <br>
                    <br>
                    <center> <h4>Вы можете скачать список всех студентов в формате xls </h4> </center>
                    <br>
                    <center><button class="btn btn-primary" type="button" onclick="getFile()">Download</button> </center>
                </div>
            </div>
        </div>
    </body>
    <script>
      function getFile() {
        location.href = "/Libra/hr/getExcelReport.html";
      }
    </script>
</html>
