<%-- 
    Document   : editUniversity
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
        <title>Управление университетами - Правка</title>
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
        <h2>Редактирование университета</h2>
        <h3>${msg}</h3>
        <form method="POST" action="editedUniversity.html">
         <table border="1" class="bordered">
             <tr>
                 <td>№ университета</td>
                  <td>Университет</td>
             </tr>
             <c:forEach items="${u}" var="u">
        <td>
            <label for="universityId">${u.universityId}</label>
        <input type="hidden" name="universityId" value="<c:out value='${u.universityId}  '/>"/></td>
        <td>
            <input type="text" name="universityName" value="${u.universityName}"/></td>
        </c:forEach>
        </table>
            <br>
         <input value="Назад" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" onclick="location.href='showUniversities.html'" type="button"/>
         <input type="submit" class="btn btn-large btn-warning" style="width:35x;height:30px;font-size:15px; line-height: 5px" name="submitUniversity" value="Изменить">
         </form>
</center>
                         </div>
                        </div>
                </div>
        </div>
    </body>
</html>
