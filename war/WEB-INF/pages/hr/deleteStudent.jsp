<%-- 
    Document   : deleteStudent
    Created on : 09.04.2013, 22:56:11
    Author     : Yuliya
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
	<title>Удаление студента</title>
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
                         <div class="hero-unit">
                            <center>
                            <h3>Удаление студента</h3>
                            Вы действительно хотите удалить выбранного студента?
                            <br/>
                            <form  action="deletedStudent.html" method="GET">
                                <table class="bordered">
                                    <thead>
                                        <tr>
                                            <th>№</th>    
                                            <th>Имя</th> 
                                            <th>Фамилия</th> 
                                            <th>Email</th>
                                        </tr>
                                    </thead>
                                    <tr>
                                    <tbody>
                                       <c:forEach items="${Model}" var="s">
                                        <tr>
                                            <td>
                                                ${s.getAppId()} 
                                                <input type="hidden" name="appId" value="${s.getAppId()}">
                                            </td>
                                            <td>${s.getName()}</td>
                                            <td>${s.getLastName()}</td>
                                            <td>${s.getEmail()}</td>
                                        </tr>
                                        </c:forEach> 
                                    </tbody>                                       
                                </table>
                            <br/>
                           <div style="text-decoration:underline; width: 250px">         
                            У этого студента:</div>
                            <table>
                                <tr><td>Анкета ${hasForm} заполнена</td></tr>
                                <tr><td>Интервью ${hasInter} назначено</td></tr>
                                <tr><td>Интервью ${passedInter} пройдено</td></tr>
                            </table>
                            <br/>
                           <input value="Назад" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" onclick="location.href='showStudentbyIdView.html'" type="button"/> 
                           <input type="submit" class="btn btn-large btn-danger" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Удалить" name="delete">
                            </form>
			</center>
                    </div>
                </div>
		</div>
	</div>
</body>
</html>