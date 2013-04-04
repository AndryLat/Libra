<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="well span3">
	<ul class="nav  nav-pills nav-stacked">

                <c:if test="${LOGGEDIN_USER!=null}">	
			<li><a href="/Libra/editLogin.html">Изменить пароль</a></li>
		</c:if>
                                
		<c:if test="${LOGGEDIN_USER.userAccessLevel==0}">
			<li class="nav-header">Собеседования</li>
				<c:if test="${LOGGEDIN_USER.appFormFlag}"><li><a href="#">Открыть анкету</a></li></c:if>
				<c:if test="${!LOGGEDIN_USER.appFormFlag}"><li><a href="/Libra/register/signup.html">Заполнить анкету</a></li></c:if>
			<li class="nav-header">Собеседования</li>
				<li><a href="/Libra/showInterviewDate.html">Записаться на собеседование</a></li>
		</c:if>

		<c:if test="${LOGGEDIN_USER.userAccessLevel==1}">
			<li class="nav-header">Студенты</li>
				<li><a href="/Libra/hr/showStudentbyIdView.html">Поиск студентов</a></li>
				<li><a href="/Libra/hr/сonfirmEditing.html">Подтверждение изменений дат интервью</a></li>
			<li class="nav-header">Собеседования</li>
				<li><a href="/Libra/hr/interviewDate.html">Расписание собеседований</a></li>
				<li><a href="/Libra/showResults.html">Показать результаты</a></li>
			<li class="nav-header">Отчеты</li>
				<li><a href="/Libra/hr/showStudentRecords.html">График записи студентов</a></li>
				<li><a href="/Libra/hr/showRegReport.html">Зарегистрировались/пришли</a></li>
				<li><a href="/Libra/hr/showAdvertise.html">Эффективность рекламы</a></li>
				<li><a href="/Libra/hr/showStudentActivity.html">Посещаемость
				собеседований</a></li>
			<li class="nav-header">Управление шаблонами</li>
				<li><a href="/Libra/showTemplates.html">Шаблоны</a></li>
				<li><a href="/Libra/showTypes.html">Просмотреть типы</a></li>

			<li class="nav-header">Управление университетами</li>
				<li><a href="/Libra/hr/showUniversities.html">Изменить университеты</a></li>
				<li><a href="/Libra/hr/showFaculties.html">Изменить факультеты</a></li>
				<li><a href="/Libra/hr/showDepartments.html">Изменить кафедры</a></li>
		</c:if>

		<c:if test="${LOGGEDIN_USER.userAccessLevel==2}">
		</c:if>

		<c:if test="${LOGGEDIN_USER.userAccessLevel==3}">
			<li class="nav-header">Действия</li>
				<li><a href="/Libra/admin/employees.html">Панель админа</a></li>
                                <li><a href="/Libra/admin/timeZone.html">Настройки времени</a></li>
		</c:if>

		<c:if test="${LOGGEDIN_USER==null}">
			<li class="nav-header">Навигация</li>
				<li><a href="/Libra/register.html">Зарегистрироваться</a></li>
				<li><a href="/Libra/login.html">Войти</a></li>
		</c:if>
	</ul>
</div>
