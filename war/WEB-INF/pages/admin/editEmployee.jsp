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
	
	<title>Редактировать</title>
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

                            <form class="form-horizontal" action="doneEdit.html" method="POST">
                                
                                <c:if test='${!errorMessage.equals("") && errorMessage != null}'>
                                    <span id="alertMessage">
                                    <div class="alert alert-error" align="center">
                                        <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                                        ${errorMessage}
                                    </div>
                                    </span>
                                </c:if>
                                
                                <div class="control-group">
                                    <div class="controls">
                                    <select name="roleId">
                                        <c:if test="${roleId == 2}">
                                            <option selected value="2">HR</option>
                                            <option value="3">TECH</option>
                                            <option value="4">ADMIN</option>
                                        </c:if>
                                        <c:if test="${roleId == 3}">
                                            <option value="2">HR</option>
                                            <option selected value="3">TECH</option>
                                            <option value="4">ADMIN</option>
                                        </c:if>
                                        <c:if test="${roleId == 4}">
                                            <option value="2">HR</option>
                                            <option value="3">TECH</option>
                                            <option selected value="4">ADMIN</option>
                                        </c:if>
                                    </select>
                                    </div>
                                </div>
                                
                                <div class="control-group ${firstNameMark}">
                                    <label class="control-label" for="firstName">Имя:</label>
                                    <div class="controls">
                                    <input type="text" id="firstName" name="firstName" value="${firstName}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${lastNameMark}">
                                    <label class="control-label" for="lastName">Фамилия:</label>
                                    <div class="controls">
                                    <input type="text" id="lastName" name="lastName" value="${lastName}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${emailMark}">
                                    <label class="control-label" for="email">Эл. почта:</label>
                                    <div class="controls">
                                    <input type="text" id="email" name="email" value="${email}">
                                    </div>
                                </div>
                                    
                                <div class="control-group">
                                    <div class="controls">
                                        <button class="btn btn-primary" type="submit"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15"/> Редактировать</button>
                                        <button type=button class="btn" onClick="parent.location='currentEmployees.html'">Отмена</button>
                                    </div>
                                </div>
                                
                                <input type="hidden" name="employeeId" value="${employeeId}"/>
                                
                            </form>
        
        
        
        
        
        
        
			</div>
		</div>
	</div>
</body>
</html>