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
        <title>Управление датами интервью - Поиск</title>
        <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
        <link href="../resources/css/bootstrap.css" rel="stylesheet">
        <link href="../resources/css/bootstrap-responsive.css" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="../resources/css/tcal.css" />
        <script type="text/javascript" src="../resources/js/jquery.timePicker.js"></script>
        <link rel="stylesheet" type="text/css" href="../resources/css/timePicker.css" />  
	<script type="text/javascript" src="../resources/js/tcal.js"> </script>
        <script type="text/javascript" src="../resources/js/chili/chili-1.8b.js"></script>
	<script type="text/javascript" src="../resources/js/docs.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.tablesorter.pager.js"></script>
        <link rel="stylesheet" type="text/css" href="../resources/css/table_.css" />
        <script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
    <script>
        $(function() {
		$("table")
			.tablesorter({widthFixed: true, widgets: ['zebra']})
			.tablesorterPager({container: $("#pager")});
	});
    $(document).ready(function() {
        $("#interSearch").bind("change", function(){    
        if ($("#interSearch").val()!=2) {
            $("#justText").css("display","inline");
            $("#calc").css("display","none");
        }
        else {
            $("#justText").css("display","none");
            $("#calc").css("display","inline");
        }
    });
 });
 function closeMessage()
            {
                document.getElementById("alertMessage").innerHTML="";
            }
$(document).ready(function() {
        $("#type").bind("change", function(){    
        if ($("#type").val()==1) {
        $("#hrDiv").css("display","block");
        $("#techDiv").css("display","none");
        }
        else {
        $("#hrDiv").css("display","none");
        $("#techDiv").css("display","block");
        }
       
    });
    
    if ($("#type").val()==1) {
        $("#hrDiv").css("display","block");
        $("#techDiv").css("display","none");
        }
        else {
        $("#hrDiv").css("display","none");
        $("#techDiv").css("display","block");
        }
       
 });
  jQuery(function() {
    $("#time3, #time4").timePicker();
        
    // Store time used by duration.
    var oldTime = $.timePicker("#time3").getTime();
    
    // Keep the duration between the two inputs.
    $("#time3").change(function() {
      if ($("#time4").val()) { 
          // // Only update when second input has a value.
        // Calculate duration.
        var duration = ($.timePicker("#time4").getTime() - oldTime);
        var time = $.timePicker("#time3").getTime();
        // Calculate and update the time in the second input.
        $.timePicker("#time4").setTime(new Date(new Date(time.getTime() + duration)));
        oldTime = time;
      }
    });
    // Validate.
    $("#time4").change(function() {
      if($.timePicker("#time3").getTime() > $.timePicker(this).getTime()) {
        $(this).css("border","1px solid red");

      }
      else {
        $(this).removeClass("error");
        $(this).css("border","");
      }
    });
    
   $("#date").change(function() {
       now= new Date();
       if($.data("date").getDate()<now.getDate()){
            
            $(this).addClass("error"); 
      }
      else {
        $(this).removeClass("error");
       }
  }) 
  $("#time4").change(function() {
  if (($("#time4").val()) && (($("#time3").val())) && ($("#date").val())) {
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
                            <div class="hero-unit" style="padding-bottom: 220px">
                                
        <div class="pull-left" style="width: 300px;margin: -40px;" >
          <form name="Form" action="interviewDateAdded.html" method="get">
            <div class="classForInters" style="text-decoration:underline; width: 250px"> Выберите интервьюеров: </div>           
        <div id="hrDiv">
       <c:forEach items="${Inters}" var="i"> 
            <input type="checkbox" name="checkInterviewers[]" id="interviewers" value=<c:out value="${i.userid}"/>>
            ${i.inters}
             <br>
        </c:forEach>
        </div>
        <div style="display: none;" id="techDiv">
        <c:forEach items="${intersTech}" var="i">
             <input type="checkbox" name="checkInterviewers[]" id="interviewers" value=<c:out value="${i.userid}"/> >                     
             ${i.inters}   <br>             
        </c:forEach>
        </div>          
        
           </div>
        <div class="pull-right" style="margin-right: 150px;margin-top: -40px">
        Тип: 
                    <select name="type" id="type" style="width: 90px">
                        <option value="1" ${typeInt == '1' ? 'selected' : ''}> Hr </option>
                        <option value="2" ${typeInt == '2' ? 'selected' : ''}> Tech </option>
                    </select>
                 &emsp;
                     Дата:
                     <input type="text" id="date" name="dateInter" class="tcal" value="${begin}" style="width: 100px" />
                 <br>
             Время начала и конца: 
             <div class="myClass"> 
            <input name="timeStart" type="text" id="time3" size="10"  value="${end}" style="width: 50px"/> -
             <input name="timeFinish" type="text" id="time4" size="10"  value="${timeStart}" style="width: 50px"/>
            </div>
               Продолжительность (минуты): 
               <input type="text" placeholder="Введите значение" name="interviewDuration" style="width: 50px" value="${duration}"/> <br>
            
            <input type="submit" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Добавить" class="btn btn-large btn-success">    
            </form>
                     
            <form action="showInterviewDateSearch.html" method="GET"><br />
                                <select name="interSearch" id="interSearch">
                                    <option value="0" ${interSearchInt == '0' ? 'selected' : ''}>Все </option>
                                    <option value="1" ${interSearchInt == '1' ? 'selected' : ''}>№ даты</option>
                                    <option value="2" ${interSearchInt == '2' ? 'selected' : ''}>Дата</option>
                                    <option value="3" ${interSearchInt == '3' ? 'selected' : ''}>Интервьер</option>
                        </select>&ensp;
                              <div id="justText" style="width: 180px; display: inline;">
                                    <input type="text" placeholder="Введите значение" value="${textBox}" name ="textBox" style="width: 180px">
                                 </div>
                                <div style="display: none;width: 180px;"  id="calc" >
                                    <input type="text" value="${textBox}" placeholder="Введите дату" id="date" name="textBoxCalc" class="tcal" style="width: 180px">
                                </div>&thinsp;
                                    <input type="submit" value="Показать" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px">
          </form>
           
</div>
        
                        </div>
        <div class="span10">
       <c:if test="${message != null}">
                 <div align="left" id="alertMessage">
                     <div class="alert alert-success"  align="center">
                         <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                         ${message}
                     </div>
                 </div>
             </c:if>
         <c:if test='${!errorMessage.equals("") && errorMessage != null}'>
            <span id="alertMessage">
            <div class="alert alert-error" align="center">
                <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                ${errorMessage}
            </div>
            </span>
        </c:if>
          <form method="GET" action="delInterviewDate.html">
        <table border="1" class="tablesorter">
         <caption><div class="alert alert-info">Расписание собеседований</div></caption>
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
      <input type="hidden" name="interviewDateId" value="<c:out value='${d.interviewDateId}  '/>"/>
       <a href="empty_link.html?interviewDateId=<c:out value='${d.interviewDateId} '/>">
       <img  src="../resources/images/mail.png" width="25" height="25" title="Уведомить"/> 
                        </a>   
      </td>
      <td>
          <a href="editInterviewDate.html?interviewDateId=<c:out value='${d.interviewDateId}'/>&type=<c:out value='${d.typeInterview}'/> ">
              <img  src="../resources/images/edit1.png" width="25" height="25" title="Править"/> 
           </a>
      </td>
      <td>
         <a href="delInterviewDate.html?interviewDateId=<c:out value='${d.interviewDateId} '/>">
             <img  src="../resources/images/delete.png" width="20" height="20" title="Удалить"/> 
                        </a>
        </td>
  </tr>
    </c:forEach>
              </tbody>
            </table>
          </form>
          <div id="pager" class="pager">
	<form><br>
		<img src="../resources/images/icons/first.png" class="first"/>
		<img src="../resources/images/icons/prev.png" class="prev"/>
		<input type="text" class="pagedisplay"/>
		<img src="../resources/images/icons/next.png" class="next"/>
		<img src="../resources/images/icons/last.png" class="last"/>
		<select class="pagesize">
			<option selected="selected" value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
		</select>
	</form>
</div>           
                                </div>
                        </div>
                </div>
    </body>
</html>
