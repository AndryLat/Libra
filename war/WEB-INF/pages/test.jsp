<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="description" content="Libra">
<link rel="stylesheet" href="<c:url value="/resources/css/main.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap-responsive.min.css"/>" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/bootstrap.min.css"/>" />
<link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>" />
<script src="<c:url value="/resources/js/jquery-1.9.0.min.js" />"></script>
<script
	src="<c:url value="/resources/js/modernizr-2.6.2-respond-1.1.0.min.js"/>"></script>

<title>Регистрация</title>
</head>
<body>
	<div class="container">
	<form:form commandName="fieldset" method="POST">
		<c:forEach items="${form}" var="t">
			<h6>${t.header}</h6>
			<c:if test="${t.typeId == 51}">
			<c:forEach items="${t.fieldList}" var="y">
				<label class="checkbox">${y.value} <input
								type="checkbox" 
								name="fieldList['${y.key}']"
								value="1" />
							</label>
			</c:forEach>
			</c:if>
			<c:if test="${t.typeId == 50}">
			<c:forEach items="${t.fieldList}" var="y">
				<textarea name="fieldList['${y.key}']" rows="2"></textarea><br>
			</c:forEach>
			</c:if>
			<c:if test="${t.typeId == 52}">
				<c:forEach items="${t.fieldList}" var="y">
					${y.value}<input type="text" name="fieldList['${y.key}']" class="span1"><br>
				</c:forEach>
			</c:if>
		</c:forEach>
		<button type="submit">Success</button>
	</form:form>
	</div>
</body>
</html>