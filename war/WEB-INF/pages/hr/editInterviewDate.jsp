<%-- 
    Document   : editInterviewDate
    Created on : 12.02.2013, 1:09:51
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="windows-1251"%>
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
        <title>���������� ������ - ������</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js" type="text/javascript">   
</script>
 <style type="text/css" media="all">@import "../resources/css/timePicker.css";</style>
         
        <script type="text/javascript" src="../resources/js/jquery.timePicker.js"></script>
<link rel="stylesheet" type="text/css" href="../resources/css/tcal.css" />
	<script type="text/javascript" src="../resources/js/tcal.js"> </script>
<script>
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
      if ($("#time4").val()) { // Only update when second input has a value.
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
        $(this).addClass("error");
      }
      else {
        $(this).removeClass("error");
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
				<div class="hero-unit">
        <center>
        <h2 align="center"> ������ ���� �������� </h2>
        <form method="POST" action="doneDate.html">
         <table border="1" class="bordered">
             <tr>
                 <td>� ����</td>
                  <td>���</td>
                  <td>����</td>
                  <td>�����</td>
                  <td>����������������� (������)</td>
             </tr>
             <c:forEach items="${dates}" var="d">
        <td>
            <label for="interviewDateId">${d.interviewDateId}</label>
        <input type="hidden" name="interviewDateId" value="<c:out value='${d.interviewDateId}  '/>"/>
        </td>
        <td>
            <select name="type" id="type" style="width: 90px">
            <option value="1" ${typeInt == '1' ? 'selected' : ''}> Hr </option>
            <option value="2" ${typeInt == '2' ? 'selected' : ''}> Tech </option>
            </select>
        </td>
        <td>
            <input type="text" id="date" name="dateInter" class="tcal" value="${d.dateInter}" style="width: 100px" />
        </td>
        <td>
             <input name="timeStart" type="text" id="time3" size="10"  value="${d.timeS}" style="width: 50px"/> :
             <input name="timeFinish" type="text" id="time4" size="10"  value="${d.timeF}" style="width: 50px"/>
        </td>
        <td><input type="text" name="interviewDuration" style="width: 50px" value="${d.interviewDuration}"/></td>
        </c:forEach>
        </table>
        <br>
        �������� ������������:
        <br> 
        
        <div id="hrDiv">
        <c:forEach items="${checkedIntersHr}" var="i">
            <input type="checkbox" name="checkInterviewers[]" id="interviewers" value=<c:out value="${i.userid}"/> checked> 
           <label for="interviwers"> ${i.inters}</label> 
            <br>
        </c:forEach>
        <c:forEach items="${uncheckedIntersHr}" var="i">
            <input type="checkbox" name="checkInterviewers[]" id="interviewers" value=<c:out value="${i.userid}"/> >
            <label for="interviwers"> ${i.inters}</label>  
            <br>
        </c:forEach>
        </div>
        
        <div style="display: none;"  id="techDiv">
       <c:forEach items="${checkedIntersTech}" var="i">
            <input type="checkbox" name="checkInterviewers[]" value=<c:out value="${i.userid}"/> checked>
            <label for="interviwers"> ${i.inters}</label> 
            <br>
        </c:forEach>
        <c:forEach items="${uncheckedIntersTech}" var="i">
            <input type="checkbox" name="checkInterviewers[]" value=<c:out value="${i.userid}"/> > 
            <label for="interviwers"> ${i.inters}</label> 
            <br>
        </c:forEach>
        </div>
        <input value="�����" onclick="location.href='interviewDate.html'" type="button"/>
        <input type="submit" name="submitDate" value="��������">
        </form>
        </center>
                                </div>
                        </div>
                </div>
        </div>
    </body>
</html>
