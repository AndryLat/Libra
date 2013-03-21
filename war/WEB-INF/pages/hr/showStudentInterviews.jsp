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
	<title>Интервью</title>
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
                        <td align="left">${view == 0 ? '<a href="showStudentbyIdView.html" class="btn"><i class="icon-arrow-left"></i> Назад</a>' : '<a href="showStudentByEducation.html" class="btn"><i class="icon-arrow-left"></i> Назад</a>'}</td>
                    </tr>
                </table>
                <br>
			
		<% int i = 1; %>
        
        <c:if test='${notAssigned != ""}'>
            <div class="alert alert-info">Собеседование №<%= i++ %>. ${notAssigned} </div><hr><br>
        </c:if>
           
        <c:if test="${!dateAndInterviewerList.isEmpty()}">
            
            <table class="bordered">
                <caption> <div class="alert alert-info">Собеседование №<%= i++ %>. Информация о предстоящем собеседовании </div></caption>
            <tr>
                <th>№ Анкеты</th>
                <th>Дата</th>
                <th>Время</th>
                <th>Имя интервьюера</th>
                <th>Должность интервьюера</th>
            </tr>
            <c:forEach items="${dateAndInterviewerList}" var="date">
                <tr>
                    <td>${date.appId}</td>
                    <td>${date.interviewDate}</td>
                    <td>${date.interviewTime}</td> 
                    <td>${date.interviewerName}</td>
                    <td>${date.interviewerRole==2 ? 'HR-менеджер' : date.interviewerRole==3 ? 'Тех.интервьюер' : date.interviewerRole==4 ? "Администратор" : "Интервьюер"}</td>
                </tr>
            </c:forEach>
            </table>
            <hr><br>
        </c:if>
            
        <c:if test="${!dateAndInterviewerResultsList.isEmpty()}">
            <table class="bordered">
            <caption> <div class="alert alert-info">Собеседование №<%= i++ %>. Информация о пройденном собеседовании </div></caption>
            <tr>
                <th>№ анкеты</th>
                <th>Дата</th>
                <th>Время</th>
                <th>Имя интервьюера</th>
                <th>Должность интервьюера</th>
                <th>Оценка интервьюера</th>
                <th>Комментарий интервьюера</th>
            </tr>
            
            <c:forEach items="${dateAndInterviewerResultsList}" var="result">
                <tr>
                    <td>${result.appId}</td>
                    <td>${result.interviewDate}</td>
                    <td>${result.interviewTime}</td>
                    <td>${result.interviewerName}</td>
                    <td>${result.interviewerRole==2 ? 'HR-менеджер' : result.interviewerRole==3 ? 'Тех.интервьюер' : result.interviewerRole==4 ? "Администратор" : "Интервьюер"}</td>
                    <td>${result.interviewerMark}</td>
                    <td>${result.interviewerComments}</td>
                </tr>
            </c:forEach>
        </table>
            <hr><br>
        </c:if>
            
        <c:if test='${wasAbsent != ""}'>
            <div class="alert alert-info" align="center">Собеседование №<%= i++ %>. ${wasAbsent} </div> <br>
        </c:if>	
			
			</div>
		</div>
	</div>
</body>
</html>