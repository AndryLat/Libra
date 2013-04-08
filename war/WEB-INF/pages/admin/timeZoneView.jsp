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
<script src="../resources/js/timeZoneJS.js"></script>
<link href="../resources/css/template.css" rel="stylesheet">

<script src="http://bootsnipp.com/js/jquery.js"></script>
	<script src="http://bootsnipp.com/js/bootstrap.min.js"></script>

    <script src="http://bootsnipp.com/js/prettify.js"></script>
    <script src="http://bootsnipp.com/js/codemirror.js"></script>
<script src="http://bootsnipp.com/js/humane.min.js"></script>
<title>Разница во времени</title>
</head>
<body>
    <div class="mincontainer">
	<div class="navmenu">
		<jsp:include page="../navbar.jsp" />
	</div>
	<div class="container-fluid">
		<div class="row-fluid">

		<div class="sidebar">
				<jsp:include page="../sidebar.jsp" />
                </div>              
                <div class="span8 well align-center">
                    <h4>Радактировать разницу во времени с сервером</h4>
                    <hr>
                    <p>Текущее время на сервере: ${time}</p>
                    <button  onclick="set_time_zone('${serverTime}'); return false" class="btn btn-info btn-block">Настроить время</button>    
                <a class="btn btn-info btn-block" data-toggle="modal" href="#myModal" >Ввести вручную разницу</a>
 
                </div>
                
<div class="modal hide" id="myModal">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal">x</button>
    <h3>На сколько время сервера опережает реальное</h3>
  </div>
  <div class="modal-body">
    <form method="post" action='editTimeZoneDiff.html' name="login_form">
      <p><input type="text" class="width96" name="diff" placeholder="Введите разницу в часах"></p>
      <p><button type="submit" class="btn btn-primary ">Ввести</button>
    </form>
  </div>
  
</div>
                
		</div>
	</div>
                </div>
</body>
</html>