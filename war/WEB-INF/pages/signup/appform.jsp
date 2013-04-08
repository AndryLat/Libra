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
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-fileupload.min.css"/>" />
	<script src="<c:url value="/resources/js/bootstrap-fileupload.min.js"/>"></script>
	<script src="<c:url value="/resources/js/education-dropdown.js"/>"></script>
	<title>Регистрация</title>
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
      					<legend>Регистрация в системе</legend>
    			</div>
    			
		<form:form commandName="regForm" method="POST">
			<div class="row-fluid"><form:errors path="*" cssClass="alert alert-error span6"></form:errors></div>
			<div class="row-fluid">
				<div class="well span6" >
					<div class="alert alert-info"><b>Данные для входа в систему</b></div>
					
						<div class="controls">
							<div class="controls">
								<div class="span5">Электронная почта</div><form:input type="email" path="email" required="1"/>
							</div>
						</div>
						<div class="controls">
							<div class="controls">
								<div class="span5">Введите пароль</div><form:input type="password" path="password"/>
							</div>
						</div>
						<div class="controls">
							<div class="controls">
								<div class="span5">Подтвердите пароль</div><form:input type="password" path="confirmedPassword"/>
							</div>
						</div>
					</div>
				</div>			

						
			<div class="row-fluid">
				<div class="well span6">
				<div class="alert alert-info"><b>Личные данные</b></div>
					<div class="controls-group">
						<div class="controls">
							<div class="span5">Фамилия</div><form:input class="input" path="lastName" type="text" required="1"/>
						</div>
					</div>				
					<div class="controls-group">
						<div class="controls">
							<div class="span5">Имя</div><form:input class="input" path="name" type="text" required="1"/>
						</div>
					</div>
					<div class="controls-group">
						<div class="controls">
							<div class="span5">Отчество</div><form:input class="input" path="patronymic" type="text" required="1" />
						</div>
					</div>
					<div class="controls-group">
						<div class="controls"> 
							<div class="span5">Номер телефона</div><form:input class="input" path="phoneNumber" type="text" required="1" />
						</div>
					</div>
				</div>
			</div>
			
			<div class="row-fluid">
				<div class="well span6">
					<div class="alert alert-info"><b>Образование</b></div>
					
					<div class="row-fluid">
						<div class="span5">Университет</div>
						<select onchange="getFact();" name="univ" id="univ"> 
                				<c:forEach items="${univers}" var="u">
                    				<option value="${u.universityId}"> ${u.universityName}</option> 
                				</c:forEach>  
               			 </select> 
					</div>
					
					<div class="row-fluid">
						<div class="span5">Факультет</div>
            				<select onchange="getDept();" name="fact" id="fact">
            					<option value="0" disabled selected>Выберите университет</option> 
            				</select>
					</div>
					
					<div class="row-fluid">
						<div class="span5">Кафедра</div>
                			<form:select path="department" id="dept" required="1">
                				<option value="0" disabled selected>Выберите факультет</option> 
                			</form:select>
					</div>
					
					<div class="controls-group">
						<div class="controls"> 
							<div class="span5">Курс</div><form:input class="input-small" path="course" type="number" min="0" max="5" required="1" />
						</div>
					</div>
					
					<div class="controls-group">
						<div class="controls"> 
							<div class="span5">Год выпуска</div><form:input class="input-small" path="graduated" type="number" min="2000" max="2020" required="1" />
						</div>
					</div>
					
					<div class="form-actions">
						<button class="btn btn-large pull-left" type="reset">Очистить форму</button>
						<button class="btn btn-large btn-success pull-right" type="submit">Зарегистрироваться</button>
					</div>
				</div>

			</div>

		</form:form>
			</div>
		</div>
	</div>
</body>
</html>