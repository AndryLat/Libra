<%-- 
    Document   : editLanguage
    Created on : 23.02.2013, 16:33:04
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
    <head>
        <jsp:include page="../resources.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Управление языками - Правка</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
    </head>
    <body>
        <div class="navmenu">
		<jsp:include page="../navbar.jsp" />
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
		<div class="sidebar">
				<jsp:include page="../sidebar.jsp" />
			</div>
			<div class="span6">
				<div class="hero-unit">
        <center>
        <h3>Редактирование языка</h3>
        </center>
        <h4>${msg}</h4>
        <form method="POST" action="editedLang.html">
         <table border="1" class="bordered">
             <tr>
                 <td>№ языка</td>
                  <td>Язык</td>
             </tr>
             <c:forEach items="${languages}" var="l">
        <td>
            <label for="languageId">${l.languageId}</label>
        <input type="hidden" name="languageId" value="<c:out value='${l.languageId}  '/>"/></td>
        <td>
            <input type="text"  name="languageName" value="${l.languageName}"/></td>
        </c:forEach>
        </table><br>
        <center>
         <input value="Назад" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" onclick="location.href='showLanguages.html'" type="button"/>
         <input type="submit" class="btn btn-large btn-warning" style="width:35x;height:30px;font-size:15px; line-height: 5px" name="submitLang" value="Изменить">
        </center>
         </form>
        
                                </div>
                        </div>
                </div>
        </div>
    </body>
</html>
