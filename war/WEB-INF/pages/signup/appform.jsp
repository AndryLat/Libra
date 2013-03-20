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
	<title>Регистрация</title>
	
	<style>
            .form-item { margin: 20px 0; }
            .form-label { font-weight: bold; }
            .form-error-field { background-color: #FFC; }
            .form-error-message { font-weight: bold; color: #900; }
        </style>
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
    			
		<form:form commandName="regForm" action="register/next.html" method="POST" enctype="multipart/form-data">

			<div class="row-fluid">

				<div class="span3">
					<div class="fileupload fileupload-new" data-provides="fileupload">
						<div class="fileupload-new thumbnail" style="width: 180px; height: 240px;">
							<img src="http://www.placehold.it/180x240/EFEFEF/AAAAAA&text=Photo" />
						</div>
						<div class="fileupload-preview fileupload-exists thumbnail"
							style="max-width: 180px; max-height: 240px; line-height: 10px;"></div>
						<div>
							<span class="btn btn-file"><span class="fileupload-new">Обзор..</span><span
								class="fileupload-exists">Изменить</span><input name="photo"
								type="file" /></span> <a href="#" class="btn fileupload-exists"
								data-dismiss="fileupload">Удалить</a>
						</div>
					</div>
				</div>

				<div class="span3">
					
					<div class="controls-group">
						<div class="controls">
							<input class="input" name="lastName" placeholder="Фамилия" type="text" required/>
						</div>
					</div>
										
					<div class="controls-group">
						<div class="controls">
							<input class="input" name="name" placeholder="Имя" type="text" required/>
						</div>
					</div>
					
					<div class="controls-group">
						<div class="controls">
							<input class="input" name="patronymic" placeholder="Отчество" type="text" required />
						</div>
					</div>
					
					<div class="controls-group">
						<div class="controls"> 
							<input class="input" name="phoneNumber" placeholder="Номер телефона" type="text" required />
						</div>
					</div>
				</div>
				
				<div class="span3">
					<form:select class="dropdown" required="1" path="university">
						<option value="" disabled selected>Университет</option>
						<form:options items="${uniList}" />
					</form:select>
					
					<form:select class="dropdown" required="1" path="faculty">
						<option value="" disabled selected>Факультет</option>
						<form:options items="${facList}" />
					</form:select>
					
					<form:select class="dropdown" required="1" path="department">
						<option value="" disabled selected>Кафедра</option>
						<form:options items="${deptList}" />
					</form:select>

					<div class="controls controls-row">
						<input class="input-small" name="course"
							placeholder="Курс" type="number" min="0" max="5" />
						<input class="input-small" name="graduated"
							placeholder="Год выпуска" type="number" min="2000" max="2020">
					</div>
				</div>
			</div>
			
			<div class="row-fluid">
				<div class="well span9">
					<div class="alert alert-info"><b>Владение языками программированния</b></div>
						<div class="span8">
							<div class="row-fluid">
								<label class="span6">Java</label>
								<input type="number" min="0" max="5" class="span2" name="languagesGrades[1${languagesGrades.value}]">
							</div>
							<div class="row-fluid">
								<label class="span6">C++</label>
								<input type="number" min="0" max="5" class="span2"  name="languagesGrades[2${languagesGrades.value}]">
							</div>
							
							<div class="row-fluid">
								<div class="span6">
									<form:select class="input-small" path="${id}">
										<option value="" disabled selected>Другой...</option>
											<c:forEach items="${languages}" var="l">
												<option value="${l.key}">${l.value}</option>
											</c:forEach>
										<option value="0">(пусто)</option>
									</form:select>
								</div>
								<input value="0" name="grade" type="number" min="0" max="5" class="span2">
							</div>
							<div class="row-fluid">
								<div class="span6">
									<form:select class="input-small" path="id2">
										<option value="" disabled selected>Другой...</option>
											<c:forEach items="${languages}" var="l">
												<option value="${l.key}">${l.value}</option>
											</c:forEach>
										<option value="0">(пусто)</option>
									</form:select>
								</div>
								<input value="0" name="grade2" type="number" min="0" max="5" class="span2">
							</div>
							<div class="row-fluid">
								<div class="span6">
									<form:select class="input-small" path="id3">
										<option value="" disabled selected>Другой...</option>
											<c:forEach items="${languages}" var="l">
												<option value="${l.key}">${l.value}</option>
											</c:forEach>
										<option value="0">(пусто)</option>
									</form:select>
								</div>
								<input value="0" name="grade3" type="number" min="0" max="5" class="span2">
							</div>
				</div>
			</div>
			</div>
			<div class="row-fluid">
				<div class="well span9" >
					<div class="alert alert-info"><b>Данные для входа в систему.</b></div>
			
						<div class="control-group">
							<label>Электронная почта</label>
								<div class="controls">
									<input type="email" name="email" placeholder=""
										class="input-large" required>
								</div>
						</div>
			
						<div class="control-group">
							<label>Введите пароль</label>
								<div class="controls">
									<input type="password" name="password" placeholder=""
										class="input-large" required>
								</div>
						</div>
						
						<div class="control-group">
							<label>Подтвердите пароль</label>
								<div class="controls">
									<input type="password" name="confirmedPassword" placeholder=""
										class="input-large" required>
								</div>
						</div>
						
						<div class="form-actions">
							<button class="btn btn-large btn-block btn-success" type="submit">Зарегистрироваться</button>
						</div>
				</div>
			</div>
		
		</form:form>
		
			</div>
		</div>
	</div>
</body>
</html>