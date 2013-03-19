<%-- 
    Document   : showInterviewDateSearch
    Created on : 07.03.2013, 16:47:58
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
        <title>Управление датами - поиск</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js">    
          </script> 
    <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"> </script>
      <script type="text/javascript" src="../resources/js/sort.js"></script>
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

if ($("#interSearch").val()==2) {
            $("#justText").css("display","none");
            $("#calc").css("display","block");
             
        }
        else {
            $("#justText").css("display","block");
            $("#calc").css("display","none");
        }
         });
</script>
       <link rel="stylesheet" type="text/css" href="../resources/css/tcal.css" />
	<script type="text/javascript" src="../resources/js/tcal.js">   
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
				<div class="hero-unit">
        <center>
            <a href="interviewDateAdd.html">Добавить новую дату интервью</a>
            <br>
            <h3>${msg}</h3>
            <form name="myForm" action="showInterviewDateSearch.html" method="get">
        <select name="interSearch" id="interSearch">
            <option value="0" ${interSearchInt == '0' ? 'selected' : ''}>Все </option>
            <option value="1" ${interSearchInt == '1' ? 'selected' : ''}>№ даты</option>
            <option value="2" ${interSearchInt == '2' ? 'selected' : ''}>Дата</option>
            <option value="3" ${interSearchInt == '3' ? 'selected' : ''}>Интервьер</option>
        </select>
        <div id="justText">
            <input type="text" name ="textBox" style="width: 100px" value="${textBox}">
        </div>
        <div style="display: none;"  id="calc">
            <input type="text" value="${textBox}" id="date" name="textBoxCalc" class="tcal" style="width: 100px" />
        </div>
            <input type="submit" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Показать" name="search" class="btn btn-large btn-primary" >
            </form>
            
            <br>
         <h2 align="center">Информация о рассписании собеседований</h2>
         <br> 
          <form method="GET" action="delInterviewDate.html">
              <table border="1" class="bordered">
                  <thead>
           <tr>
            <th><a href="#">№ даты</a></th>
            <th><a href="#">Тип</a></th>
            <th><a href="#">Дата</a></th>
            <th><a href="#">Время</a></th>
            <th><a href="#">Продолжительность</a></th>
            <th><a href="#">Интервьюеры</a></th>
            <th>Уведомить</th>
            <th>Править</th>
            <th>Удалить</th>
           </tr>
                  </thead>
                  <tbody>
    <c:forEach items="${dates}" var="d">
    <tr>
      <td><c:out value="${d.interviewDateId}"/></td>
      <td><c:out value="${d.typeInterview}"/></td>
      <td><c:out value="${d.dateInter}"/></td>
      <td><c:out value="${d.timeInter}"/></td>
      <td><c:out value="${d.interviewDuration}"/> минут </td>
      <td><c:out value="${d.listInterviewers}"/></td>
      <td>
      <input type="submit" name="sentEmails" value="Уведомить">
      <input type="hidden" name="interviewDateId" value="<c:out value='${d.interviewDateId}  '/>"/>
      </td>
      <td>
          <a href="editInterviewDate.html?interviewDateId=<c:out value='${d.interviewDateId}'/>">
              править
          </a>
      </td>
      <td>
       <a href="delInterviewDate.html?interviewDateId=<c:out value='${d.interviewDateId} '/>">
         удалить
         </a>
        </td>
  </tr>
    </c:forEach>
              </tbody>
            </table>
          </form>
         </center>
                                </div>
                        </div>
                </div>
        </div>
    </body>
</html>
