<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
<!--<![endif]-->
<head>
	<jsp:include page="../resources.jsp" />
	<title>Добро пожаловать</title>
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
					<div id="legend">
      					<legend>Добро пожаловать</legend>
      					
    				</div>
    			<div class="span8">
    				<p>Вы прошли процесс регистрации.</p>
    				<p>Чтобы попасть на собеседование, вам будет необходимо заполнить анкету кандидата. 
    				После этого, на ваш электронный адрес придет письмо с кодом подтверждения.</p>
    				
    			<p>После подтверждения анкеты, вы сможете выбрать удобные для вас дату и время собеседования.</p>
    			<a type="submit" class="btn btn-primary pull-right" href="signup.html">Заполнить анкету</a>
    			</div>
			</div>
			</div>
		</div>
</body>
</html>