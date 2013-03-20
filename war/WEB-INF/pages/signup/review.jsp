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
<jsp:include page="../resources.jsp" />

<title>Вы успешно заполнили анкету</title>
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
      					<legend>Подтверждение регистрации</legend>
    			</div>
				<div class="row-fluid span7">На ваш электронный адрес <b>${regForm.email}</b> был выслан проверочный код.<br> Для завершения процесса регистрации, пожалуйста, введите код из письма.<br><br></div>
					<form:form commandName="regForm" method="POST" class="span7" action="verifyCode.html">
						<div class="row-fluid well">
							<label class="span3">Проверочный код:</label>
								<form:input path="enteredCode" type="text" class="span3" />
								<button class="btn btn-success pull-right span3" type="submit">Подтвердить</button>
						</div>
						</form:form>
						
				</div>

			</div>
		</div>
</body>
</html>