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

<title>Вход</title>
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
				<form:form class="form-horizontal" method="POST">
					<fieldset>
					<div id="legend">
      					<legend class="">Войти</legend>
    				</div>
					<div class="control-group">
						<label class="control-label">Электропочта</label>
						<div class="controls">
							<input type="text" id="email" name="email">
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">Пароль</label>
						<div class="controls">
							<input type="password" name="password">
						</div>
					</div>
					<div class="control-group">
						<div class="controls">
							<button type="submit" class="btn btn-success">Войти</button>
						</div>
					</div>
					</fieldset>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>