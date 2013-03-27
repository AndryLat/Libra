<%-- 
    Author     : Alexander Lebed
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	
	<title>Подтверждение</title>
	<script>
            function closeMessage()
            {
                document.getElementById("alertMessage").innerHTML="";
            }
    </script>
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
			<div class="span9">
                            
        <table class="bordered">
            <caption><div class="alert alert-info">Подтверждение анкетных изменений </div></caption>
        <tr>
            <th><a href="sortEmployees.html?orderBy=ID">№ анкеты</a></th>
            <th><a href="sortEmployees.html?orderBy=FIRST_NAME">имя</a> <a href="sortEmployees.html?orderBy=LAST_NAME">фамилия</a></th>
            <th><a href="sortEmployees.html?orderBy=EMAIL">email</a></th>
            <th><a href="sortEmployees.html?orderBy=ROLE">поле</a></th>
            <th>старое значение</th>
            <th>новое значение</th>
            <th>подтвердить</th>
            <th>отменить</th>
        </tr>
        <c:forEach items="${employees}" var="emp">
                <tr>
                    <td>${emp.getFirstName()} ${emp.getLastName()}</td>
                    <td>${emp.getUserId()}</td>
                    <td>
                        <c:if test="${emp.getRoleId() == 2}">HR</c:if>
                        <c:if test="${emp.getRoleId() == 3}">TECH</c:if>
                        <c:if test="${emp.getRoleId() == 4}">ADMIN</c:if>
                    </td>
                    <td>${emp.getEmail()}</td>
                    <c:if test="${currentUserId != emp.getUserId()}">
                    <td><a href="resetEmployeePassword.html?employeeId=<c:out value='${emp.getUserId()}'/>"><i class="icon-refresh" title="Сбросить пароль"></i>icon-refresh</a></td>
                    </c:if>
                    <c:if test="${currentUserId == emp.getUserId()}">
                    <td><a href="changeOwnPassword.html?employeeId=<c:out value='${emp.getUserId()}'/>"><i class="icon-pencil" title="Изменить пароль"></i>icon-pencil</a></td>
                    </c:if>
                    <td><a href="editEmployee.html?employeeId=<c:out value='${emp.getUserId()}'/>"><i class="icon-wrench" title="Редактировать"></i>icon-wrench</a></td>
                    <td><a href="deleteEmployee.html?employeeId=<c:out value='${emp.getUserId()}'/>"><i class="icon-remove" title="Удалить"></i>icon-remove</a></td>
                </tr>
        </c:forEach>
        </table>
                            
			</div>
		</div>
	</div>
</body>
</html>