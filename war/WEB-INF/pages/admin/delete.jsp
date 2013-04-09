<%-- 
    Author     : Alexander Lebed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
<!--<![endif]-->
<head>
	<jsp:include page="../resources.jsp" />
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
	<title>Сотрудники</title>
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
                            
         <table width="100%">
         <tr>
             <c:if test="${message != null}">
                 <td align="left" id="alertMessage">
                     <div class="alert alert-success" align="center">
                         <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                         ${message}
                     </div>
                 </td>
             </c:if>
             <td align="right"><a href="addEmployee.html" class="btn"><img  src="../resources/images/admin/glyphicons_006_user_add.png" width="20" height="20" title="Добавить нового сотрудника"/></a></td>
         </tr>
        </table>
        <br>
                            
        <form class="form-inline" action="requiredEmployees.html" method="POST">
        <div align="center">
        <tr>
            <!-- radio buttons "role" -->
            <td><label class="radio"><input type="radio" name="role" value="0" ${checkedAll}>ВСЕ &nbsp;</label></td>
            <td><label class="radio"><input type="radio" name="role" value="2" ${checkedHR}>HR &nbsp;</label></td>
            <td><label class="radio"><input type="radio" name="role" value="3" ${checkedTech}>TECH &nbsp;</label></td>
            <td><label class="radio"><input type="radio" name="role" value="4" ${checkedAdmin}>ADMIN &nbsp;</label></td>
             <td>
                 <!-- dropdown "byWhat" -->
                <select name="byWhat" style="width: 20%">
                    <option ${selectedAll} value="ALL">показать всех</option>
                    <option ${selectedFull} value="FULL_NAME">по имени и фамилии</option>
                    <option ${selectedFirst} value="FIRST_NAME">по имени</option>
                    <option ${selectedLast} value="LAST_NAME">по фамилии</option>
                    <option ${selectedEmail} value="EMAIL">по эл.почте</option>
                </select>
             </td>
             <td><input type="text" placeholder="Введите значение" name="textValue" value="${text}"></td>
             <td><button class="btn btn-primary"><img  src="../resources/images/admin/search-icon-white-one-hi.png" width="12" height="12"/> Поиск &nbsp;</button></td>
        </tr>
        </div>
        </form>
             
        <!-- displaying the message when no results -->
        <div align="center"><c:if test="${employees.isEmpty()}"> ${noResults} </c:if></div>
        
        <c:if test="${employees.isEmpty() == false}">
        <table class="bordered" style="margin-left: auto; margin-right: auto;">
            <caption><div class="alert alert-info">Employees</div></caption>
        <tr>
            <th><a href="sortEmployees.html?orderBy=FIRST_NAME">имя</a> <a href="sortEmployees.html?orderBy=LAST_NAME">фамилия</a></th>
            <th><a href="sortEmployees.html?orderBy=ID">id</a></th>
            <th><a href="sortEmployees.html?orderBy=ROLE">статус</a></th>
            <th><a href="sortEmployees.html?orderBy=EMAIL">email</a></th>
            <th>пароль</th>
            <th>редактировать</th>
            <th>удалить</th>
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
                    <td><a href="resetEmployeePassword.html?employeeId=<c:out value='${emp.getUserId()}'/>"><img  src="../resources/images/admin/glyphicons_081_refresh.png" width="15" height="15" title="Сбросить пароль"/></a></td>
                    </c:if>
                    <c:if test="${currentUserId == emp.getUserId()}">
                    <td><a href="changeOwnPassword.html?employeeId=<c:out value='${emp.getUserId()}'/>"><img  src="../resources/images/admin/glyphicons_044_keys.png" width="15" height="15" title="Изменить пароль"/></a></td>
                    </c:if>
                    <td><a href="editEmployee.html?employeeId=<c:out value='${emp.getUserId()}'/>"><img  src="../resources/images/admin/glyphicons_137_cogwheels.png" width="20" height="20" title="Редактировать"/></a></td>
                    <c:if test="${id != emp.getUserId()}">
                    <td><a href="deleteSure.html?employeeId=<c:out value='${emp.getUserId()}'/>"><img  src="../resources/images/admin/glyphicons_007_user_remove.png" width="17" height="17" title="Удалить"/></a></td>
                    </c:if>
                    <c:if test="${id == emp.getUserId()}">
                        <form action="doneDelete.html" method="POST">
                            <td><div class="alert alert-error" align="center">Удалить?</div><button type="submit" class="btn btn-primary btn-small">Да</button> <button type=button class="btn btn-small" onClick="parent.location='currentEmployees.html'">Нет</button></td>
                            <input type="hidden" name="employeeId" value="${emp.getUserId()}">
                        </form>
                    </c:if>
                </tr>
        </c:forEach>
        </table>
       </c:if>
        
        
			</div>
		</div>
	</div>
</body>
</html>