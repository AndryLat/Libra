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
				<div class="row-fluid">
					<div class="hero-unit span12">
						<div class="span6">
							<h1>Libra&nbsp;</h1>
							<p>Be all you can be.</p>
							<a class="btn btn-success btn-large" href="#">Узнай больше</a>
						</div>

						<div class="span6">
							<blockquote>
								<p><h2>Hello, World</h2></p>
								<small>Неизвестный программист</small>
							</blockquote>
						</div>
					</div>
				</div>

				<div class="row-fluid">
					<div class="span4">
						<h2>Учись</h2>
						<p>Пройди обучение в нашем Центре и получи необходимые навыки
							и знания востребованных технологий для разработки и тестирования
							программного обеспечения</p>
						<p>
							<a class="btn btn-primary btn-large" href="#">Учебный центр</a>
						</p>
					</div>

					<div class="span4">
						<h2>Развивайся</h2>
						<p>После окончания учебного центра у тебя есть возможность
							попасть на стажировку в компанию и получить неоценимый опыт
							разработки и работы в команде.</p>
						<p>
							<a class="btn btn-primary btn-large" href="#">Стажировка</a>
						</p>
					</div>

					<div class="span4">
						<h2>Создавай</h2>
						<p>Если ты успешно закончил обучение и прошел стажировку, у
							тебя есть неплохие шансы получить работу в компании. Список
							вакансий постоянно обновляется</p>
						<p>
							<a class="btn btn-primary btn-large" href="#">Работа в
								компании</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>