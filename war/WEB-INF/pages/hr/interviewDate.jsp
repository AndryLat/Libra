<%-- 
    Document   : interviewDate
    Created on : 05.02.2013, 0:34:43
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html class="no-js">
<!--<![endif]-->
    <head>
         <jsp:include page="../resources.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Управление датами интервью</title>
 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js">    
          </script> 
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"> </script>
            <link href="../resources/css/bootstrap.css" rel="stylesheet">
            <link href="../resources/css/bootstrap-responsive.css" rel="stylesheet">
            <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
            <script type="text/javascript" src="../resources/js/sort.js"></script>
            <link rel="stylesheet" type="text/css" href="../resources/css/tcal.css" />
	<script type="text/javascript" src="../resources/js/tcal.js"> </script>
    <script type="text/javascript">
   
    $(document).ready(function() {
        $("#interSearch").bind("change", function(){    
        if ($("#interSearch").val()!=2) {
            $("#justText").css("display","block");
            $("#calc").css("display","none");
        }
        else {
            $("#justText").css("display","none");
            $("#calc").css("display","block");
        }
    });
 });

</script>
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
                            <div class="hero-unit" style="padding-bottom: 150px">
<div class="pull-left">
            <a href="interviewDateAdd.html">Добавить новую дату интервью</a>
</div>
        <div class="pull-right" style="margin-right: 100px">
        <form name="myForm" action="showInterviewDateSearch.html" method="get">
        <select name="interSearch" id="interSearch" >
            <option value="0">Все </option>
            <option value="1">№ даты</option>
            <option value="2">Дата</option>
            <option value="3">Интервьер</option>
        </select>
        <div id="justText">
         <input type="text" placeholder="Введите значение" name ="textBox" style="width: 210px">
        </div>
        <div style="display: none;"  id="calc">
          <input type="text" placeholder="Введите дату" id="date" name="textBoxCalc" class="tcal" style="width: 210px" />
        </div>
            <input type="submit" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Показать" name="search" class="btn btn-large btn-primary" >
            </form>
                                    </div>
            
                                </div>
                        </div>
                                <div class="span9">
                                    <h4>${msg}</h4>
          <form  method="GET" >
              <table border="1" class="bordered">
                  <thead>
          <tr>
            <th><a href="#">№ даты</a></th>
            <th><a href="#">Тип</a></th>
            <th><a href="#">Дата</a></th>
            <th><a href="#">Время</a></th>
            <th><a href="#">Продолжительность</a></th>
            <th><a href="#">Интервьюеры</a></th>
            <th>Действия</th>
            
          </tr>
                  </thead>
                  <tbody>
    <c:forEach items="${Model}" var="d">
    <tr>
      <td><c:out value="${d.interviewDateId}"/></td>
      <td><c:out value="${d.typeInterview}"/></td>
      <td><c:out value="${d.dateInter}"/></td>
      <td><c:out value="${d.timeInter}"/></td>
      <td><c:out value="${d.interviewDuration}"/> минут </td>
      <td><c:out value="${d.listInterviewers}"/></td>
      <td>
           <a href="empty_link.html?interviewDateId=<c:out value='${d.interviewDateId} '/>">
       <img  src="../resources/images/mail.png" width="25" height="25" title="Уведомить"/> 
                        </a>   
          <a href="editInterviewDate.html?interviewDateId=<c:out value='${d.interviewDateId}'/>&type=<c:out value='${d.typeInterview}'/> ">
              <img  src="../resources/images/edit1.png" width="25" height="25" title="Править"/> 
           </a>
         <a href="delInterviewDate.html?interviewDateId=<c:out value='${d.interviewDateId} '/>">
             <img  src="../resources/images/delete.png" width="20" height="20" title="Удалить"/> 
                        </a>
        </td>
    </tr>
        </c:forEach>
                  </tbody>
            </table>
          </form>
                                  
         </div>
                        </div>
                </div>
    </body>
</html>
