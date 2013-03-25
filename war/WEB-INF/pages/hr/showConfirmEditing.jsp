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
	<link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
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
         </tr>
        </table>
        <br>
        
        <table class="bordered">
            <caption><div class="alert alert-info">Подтверждение</div></caption>
        <tr>
            <th><a href="sortOldNewInterview.html?orderBy=APP_ID">№ анкеты</a> &nbsp; ${idOrder}</th>
            <th><a href="sortOldNewInterview.html?orderBy=FIRST_NAME">имя</a> <a href="sortOldNewInterview.html?orderBy=LAST_NAME">фамилия</a> &nbsp; ${nameOrder}</th>
            <th><a href="sortOldNewInterview.html?orderBy=EMAIL">email</a> &nbsp; ${emailOrder}</th>
            <th><a href="sortOldNewInterview.html?orderBy=FIELD_NAME">название поля</a> &nbsp; ${fieldNameOrder}</th>
            <th>старое значение</th>
            <th>новое значение</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${timeList}" var="obj">
                <tr>
                    <td>${obj.getAppId()}</td>
                    <td>${obj.getFirstName()} ${obj.getLastName()}</td>
                    <td>${obj.getEmail()}</td>
                    <td>${obj.getFieldName()}</td>
                    <td>${obj.getOldTimeInterview()} ${obj.getOldDateInterview()}</td>
                    <td>${obj.getNewTimeInterview()} ${obj.getNewDateInterview()}</td>
                    <td><a href="doneConfirmEditingInterviewTime.html?oldInterviewId=<c:out value='${obj.getOldInterviewId()}'/>&newInterviewId=<c:out value='${obj.getNewInterviewId()}'/>" class="btn">Подтвердить</a></td>
                    <td><a href="cancelConfirmEditingInterviewTime.html?newInterviewId=<c:out value='${obj.getNewInterviewId()}'/>" class="btn">Отменить</a></td>
                </tr>
        </c:forEach>
        </table>
        
        
			</div>
		</div>
	</div>
</body>
</html>