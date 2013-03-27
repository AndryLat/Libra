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
	<title>Добавить сотрудника</title>
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
                            
                            <form class="form-horizontal" action="doneAdd.html" method="POST">
                                
                                
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
                                        <option ${hrSelected} value="2">HR</option>
                                        <option ${techSelected} value="3">TECH</option>
                                        <option ${adminSelected} value="4">ADMIN</option>
                                    </select>
                                    </div>
                                </div>
                                
                                <div class="control-group ${firstNameMark}">
                                    <div class="controls">
                                        <input type="text" name="firstName" placeholder="Имя" value="${firstName}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${lastNameMark}">
                                    <div class="controls">
                                        <input type="text" name="lastName" placeholder="Фамилия" value="${lastName}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${emailMark}">
                                    <div class="controls">
                                        <input type="text" name="email" placeholder="Эл. почта" value="${email}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${passwordMark}">
                                    <div class="controls">
                                        <input type="password" name="password" placeholder="Пароль" value="${password}">
                                    </div>
                                </div>
                                
                                <div class="control-group">
                                    <div class="controls">
                                        <button class="btn btn-success" type="submit"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15"/> Добавить</button>
                                        <button type=button class="btn" onClick="parent.location='currentEmployees.html'">Отмена</button>
                                    </div>
                                </div>
                                    
                            </form>
                            
                            
                    </div>
            </div>
    </div>
</body>
</html>