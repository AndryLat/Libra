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
	<title>Изменить пароль</title>
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
                            
                            <form class="form-horizontal" action="doneChangeOwnPassword.html" method="POST">
                                
                                <c:if test='${!errorMessage.equals("") && errorMessage != null}'>
                                    <span id="alertMessage">
                                    <div class="alert alert-error" align="center">
                                        <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                                        ${errorMessage}
                                    </div>
                                    </span>
                                </c:if>
                                
                                <div class="control-group ${currentPasswordMark}">
                                    <label class="control-label" for="currentPassword">Текущий пароль:</label>
                                    <div class="controls">
                                    <input type="password" id="currentPassword" name="currentPassword" value="${currentPassword}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${newPasswordMark}">
                                    <label class="control-label" for="newPassword">Новый пароль:</label>
                                    <div class="controls">
                                    <input type="password" id="newPassword" name="newPassword" value="${newPassword}">
                                    </div>
                                </div>
                                
                                <div class="control-group ${repeatNewPasswordMark}">
                                    <label class="control-label" for="repeatNewPassword">Повторите новый пароль:</label>
                                    <div class="controls">
                                    <input type="password" id="repeatNewPassword" name="repeatNewPassword" value="${repeatNewPassword}">
                                    </div>
                                </div>
                                
                                <div class="control-group">
                                    <div class="controls">
                                        <button class="btn btn-success" type="submit"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15"/> Сменить пароль </button>
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