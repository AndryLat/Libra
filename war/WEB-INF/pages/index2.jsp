<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
<head>
<jsp:include page="resources.jsp" />
<title>Добро пожаловать</title>
</head>
<body>

	<div class="navmenu">
		<jsp:include page="navbar.jsp" />
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="sidebar">
				<jsp:include page="sidebar.jsp" />
			</div>
			<div class="span9">
			<c:if test="${logoutMessage!=null}">
				<div class="row-fluid"><div class="alert alert-error">${logoutMessage}</div></div>
			</c:if>
				<div class="hero-unit">
					<h1>Libra&nbsp;</h1>
					<c:if test="${LOGGEDIN_USER.userId==null}">
					<p>Войдите или зарегистрируйтесь.</p>
					<p>
						<a href="login.html"><button class="btn btn-large btn-primary" type="button">Вход</button></a>
						<a href="register.html"><button class="btn btn-large btn-success" type="button">Регистрация</button></a>
					</p>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>