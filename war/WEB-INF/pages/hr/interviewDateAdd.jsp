<%-- 
    Document   : interviewDateAdd
    Created on : 26.02.2013, 14:12:36
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
         <jsp:include page="../navbar.jsp" />
         <jsp:include page="../sidebar.jsp" />
        <title>Управление датами интервью - добавление</title>
          <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.js">    
          </script>    
         
        <script type="text/javascript" src="../resources/js/jquery.timePicker.js"></script>
      <script type="text/javascript">
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
        //$(this).attr("class","error");
        //$(this).addClass("error"); 
      }
      else {
        $(this).removeClass("error");
        $(this).css("border","");
      }
    });
    
   $("#date").change(function() {
       now= new Date();
       console.log("hello");
       if($.data("date").getDate()<now.getDate()){
            
            $(this).addClass("error"); 
            console.log("error");
      }
      else {
        $(this).removeClass("error");
        console.log("not error");
       }
  }) 
  $("#time4").change(function() {
  if (($("#time4").val()) && (($("#time3").val())) && ($("#date").val())) {
      
  }
  
});
});
</script>
       <link rel="stylesheet" type="text/css" href="../resources/css/tcal.css" />
       <link rel="stylesheet" type="text/css" href="../resources/css/timePicker.css" />
	<script type="text/javascript" src="../resources/js/tcal.js">   
        </script> 
    </head>
    <body>
        <div class="navmenu">
		
	</div>

	<div class="container-fluid">
		<div class="row-fluid">
		<div class="sidebar">
		
			</div>
			<div class="span6">
				<div class="hero-unit">
        <center>
        <h3>Добавление новой даты интервью</h3>
        </center>
        
         <form name="Form" action="interviewDateAdded.html" method="get">
         <table border="0px">
             <tr>
                 <td> Тип:</td>  
                 <td>
                     <select name="type" id="type" style="width: 115px">
                     <option value="1" > Hr </option>
                     <option value="2"> Tech </option>
                     </select> 
                </td>
             <tr>
                 <td> 
                     Дата:
                 </td>
                 <td> 
                     <input type="text" id="date" name="begin" class="tcal" value=""  style="width: 100px" />
                 </td>
             </tr>
	</table>
             <form name="Form" action="interviewDateAdded.html" method="get">
                 <input type="hidden" value='${type}' name="type"> 
                 <input type="hidden" value='${begin}' name="begin"> 
             Время начала и конца: 
             <div class="myClass">
                <input name="timeStart" type="text" id="time3" size="10" value="08:00" style="width: 50px"/> 
            - 
            <input name="end" type="text" id="time4" size="10" value="09:00" style="width: 50px"/>
            </div>
             
               Продолжительность (минуты): 
               <input type="text"  name="duration" style="width: 50px" > 
        <br> 
        <div class="classForInters" style="text-decoration:underline;">
        Выберите интервьюеров:</div> 
        <div id="hrDiv"> 
            <table border="0">
        <c:forEach items="${Inters}" var="i">
                <tr>
                    <td>
                        <input type="checkbox" style="margin: 0px;" name="checkInterviewers[]" id="<c:out value="${i.userid}"/>" value=<c:out value="${i.userid}"/> >
                    </td>
                    <td>
                        <label style="font-size:18px;margin-bottom: 0px;" for="<c:out value="${i.userid}"/>">${i.inters}</label>
                    </td>
               </tr>   
        </c:forEach>
            </table>
        </div>
        <div style="display: none;"  id="techDiv">
            <table border="0">
        <c:forEach items="${intersTech}" var="i"> 
            <tr>
                    <td>
                        <input type="checkbox" style="margin: 0px;" name="checkInterviewers[]" id="<c:out value="${i.userid}"/>" value=<c:out value="${i.userid}"/> >
                    </td>
                    <td>
                        <label style="font-size:18px;margin-bottom: 0px;" for="<c:out value="${i.userid}"/>">${i.inters}</label>
                    </td>
               </tr>   
        </c:forEach>
            </table>
        </div>
            </br> 
        <input value="Назад" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" onclick="location.href='interviewDate.html'" type="button"/>   
        <input type="submit" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Добавить" class="btn btn-large btn-success" >    
    </form>                      
                                </div>
                        </div>
                </div>
        </div>
    </body>
</html>
