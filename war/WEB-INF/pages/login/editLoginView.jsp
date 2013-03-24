<%-- 
    Author     : Sashenka
--%>

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
<link href="../Libra/resources/css/template.css" rel="stylesheet">
    
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
                        
                            <form class="form-horizontal" action="editLoginSubmit.html" method="POST">
                                <fieldset>
                                  <div id="legend">
                                    <legend class="">Поменять пароль</legend>
                                  </div>
                                  <div class="control-group">
                                    <!-- Password-->
                                    <label class="control-label" for="password">Старый пароль</label>
                                    <div class="controls">
                                      <input type="password" id="password" name="AldPassword" placeholder="" class="input-xlarge">
                                    </div>
                                  </div>

                                  <div class="control-group">
                                    <!-- Password-->
                                    <label class="control-label" for="password">Новый пароль</label>
                                    <div class="controls">
                                      <input type="password" id="password" name="password1" placeholder="" class="input-xlarge">
                                    </div>
                                  </div>
                                   
                                    <div class="control-group">
                                    <!-- Password-->
                                    <label class="control-label" for="password">Подтверждение</label>
                                    <div class="controls">
                                      <input type="password" id="password" name="password2" placeholder="" class="input-xlarge">
                                    </div>
                                  </div>

                                  <div class="control-group">
                                    <!-- Button -->
                                    <div class="controls">
                                      <button class="btn btn-primary">Изменить</button>
                                    </div>
                                  </div>
                                </fieldset>
                              </form>

		</div>
	</div>
</body>
</html>