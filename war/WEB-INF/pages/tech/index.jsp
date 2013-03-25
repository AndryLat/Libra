<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
<!--<![endif]-->
<head>
<jsp:include page="../resources.jsp" />

<title>Tech :: Личный кабинет</title>
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
      					<legend class="">Welcome</legend>
    				</div>
    				<p>Это личный кабинет Tech</p>
			</div>
		</div>
	</div>
</body>
</html>