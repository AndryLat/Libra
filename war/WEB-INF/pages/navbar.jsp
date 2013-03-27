<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a class="brand">Libra</a>
            <div class="navbar-content">
				<ul class="nav">
					<li class="active"><a href="/Libra">Главная</a></li>
					<li><a href="#">Информация</a></li>
					<li><a href="#">Контакты</a></li>
				</ul>
            </div>
                <c:if test="${LOGGEDIN_USER.userId!=null}"><a type="button" href="/Libra/logout.html" class="btn pull-right">Logout</a></c:if>
        </div>
    </div>
</div>