<%-- 
    Document   : showStudentbyIdView
    Created on : 04.02.2013, 0:42:45
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
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Поиск студентов</title>
        <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.tablesorter.pager.js"></script>
	<script type="text/javascript" src="../resources/js/chili/chili-1.8b.js"></script>
	<script type="text/javascript" src="../resources/js/docs.js"></script>
         <link rel="stylesheet" type="text/css" href="../resources/css/table_.css" />

        <script>    
 $(function() {
		$("table")
			.tablesorter({widthFixed: true, widgets: ['zebra']})
			.tablesorterPager({container: $("#pager")});
	});           
 $(document).ready(function() {
    getUniv();  
 });
function getUniv(){
 $.get("university.html",
                function(data) {
                   $("#univ").html(data);
                   $("#univ").val($("#myUniver").val());
                   getFact();
                }   
           );
    }
function getFact(){
 $.post("faculty.html",{"universityId":$("#univ").val() },
                function(data) {
                   $("#fact").html(data);
                   $("#fact").val($("#myFact").val());
                   getDept();
                      
                }   
           );
    }
function getDept(){
 $.post("department.html",{"facultyId":$("#fact").val() },
                function(data) {
                   $("#dept").html(data);
                   $("#dept").val($("#myDept").val());
                }        
           );
    }
function closeMessage()
            {
                document.getElementById("alertMessage").innerHTML="";
            }
</script>
    </head>
    <body>
        <div class="navmenu">
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
		<div class="sidebar">
				
			</div>
			<div class="span9" >
         <div class="hero-unit" style="padding-bottom: 150px">
             <form method="POST" action="showStudentbyIdView.html">
             <div class="pull-left" style="margin-top: -15px">
             <input type="hidden" name="myUniver" id="myUniver" value="${selectedUniv}">
             <input type="hidden" name="myFact" id="myFact" value="${selectedFact}">
             <input type="hidden" name="myDept" id="myDept" value="${selectedDept}">
             <table class="tablesorter,special">
            <thead>
        <tr>   
            <td >
                Университет:
            </td>
            <td >
                <select onchange="getFact();"  name="univ" id="univ">
                <option value="0"> Все </option> 
                <c:forEach items="${univers}" var="u">
                    <option value="${u.universityId}"> ${u.universityName}</option> 
                </c:forEach>  
                </select> 
            </td>
       </tr>
            </thead>
            <tbody>
       <tr>
            
            <td style="border:0px;padding: 2px">
                Факультет:
            </td>
            <td style="border:0px;padding: 2px">
            <select onchange="getDept();" name="fact" id="fact">
             <option value="0"> Все </option> 
            </select>
            </td>
       </tr>
       <tr>
            <td style="border:0px;padding: 2px">
                Кафедра:
            </td> 
            <td style="border:0px;padding: 2px"> 
                <select name="dept" id="dept">
                <option value="0"> Все </option> 
                </select>
            </td>
       </tr>
            </tbody>
      </table> 
            
             </div>
         <div class="pull-right" style="margin-right: 250px;margin-top: -15px;">
        <select name="filter">
        <option value="1" ${filterInt == '1' ? 'selected' : ''}>Показать всех</option>
        <option value="2" ${filterInt == '2' ? 'selected' : ''}>Номер анкеты</option>
        <option value="3" ${filterInt == '3' ? 'selected' : ''}>Имя</option>
        <option value="4" ${filterInt == '4' ? 'selected' : ''}>Фамилия</option>
        <option value="5" ${filterInt == '5' ? 'selected' : ''}>Email</option>
        <option value="6" ${filterInt == '6' ? 'selected' : ''}>По всем полям</option>
</select>
<br>

    <input type="text" placeholder="Введите значение" name="textBox" value="${textBox}">
    <br>
    <center>
    <input type="submit" class="btn btn-large btn-primary" style="width:35x;height:30px;margin-left: 100px;font-size:15px; line-height: 5px" value="Показать">
      </center>                        
    </div>   
    </form>                     
    </div>
                        </div>
         <div class="span8">
           <table width="100%"><thead>
         <c:if test='${!errorMessage.equals("") && errorMessage != null}'>
             
             <tr><th></th>
                                    <span id="alertMessage">
                                    <div class="alert alert-error" align="center">
                                        <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                                        ${errorMessage}
                                    </div>
                                    </span>
                                </c:if>
           </tr></thead>
         <tbody>
          <tr>
          <td></td>
           </tr>
           </tbody>
           </table>
           <table width="100%">
            <thead>
                <tr>
                    <td></td>
                </tr>
            </thead>
            <tbody>
         <tr>
         <c:if test="${message != null}">
                 <td align="left" id="alertMessage">
                     <div class="alert alert-success" align="center">
                         <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                         ${message}
                     </div>
                 </td>
             </c:if>
         </tr></tbody>
                                        </table>
             <c:if test="${Model.isEmpty() == false}">
        <TABLE border ="1" class="tablesorter">
        <caption><div class="alert alert-info">Студенты</div></caption>
        <thead>
        <tr>
            <th><a href="">№</a></th>    
            <th><a href="">Имя</a></th> 
            <th><a href="">Фамилия</a></th> 
            <th><a href="">Email</a></th>
            <th>Анкета</th>
            <th>Интервью</th>
            <th>Удалить</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${Model}" var="s">
            <tr>
            <td><input type="hidden" name="appId" value="<c:out value='${s.getAppId()}'/>"/>${s.getAppId()}</td>
            <td><input type="hidden" name="firstName" value="<c:out value='${s.getName()}'/>"/>${s.getName()}</td>
            <td><input type="hidden" name="lastName" value="<c:out value='${s.getLastName()}'/>"/>${s.getLastName()}</td>
            <td><input type="hidden" name="email" value="<c:out value='${s.getEmail()}'/>"/>${s.getEmail()}</td>
            <td> 
                <a href="">
                            <img  src="../resources/images/appForm.png" width="15" height="15" title="Анкета"/>
                        </a>
            </td>
            <td>
                <a href="showStudentInterview.html?appId=<c:out value='${s.getAppId()}'/>&firstName=<c:out value='${s.getName()}'/>&lastName=<c:out value='${s.getLastName()}'/>&view=0">
                            <img  src="../resources/images/inter.png" width="25" height="25" title="Интервью"/>
                        </a>
            </td>
            <td>
                    <a href="deleteStudent.html?appId=<c:out value='${s.getAppId()}'/>">
                            <img  src="../resources/images/delete.png" width="20" height="20" title="Удалить"/>
                        </a>
      </td>
  </tr>
    </c:forEach>
        </tbody>
    </TABLE>
        
       </form>
       <center>
           
          <div id="pager" class="pager">
	<form><br>
		<img src="../resources/images/icons/first.png" class="first"/>
		<img src="../resources/images/icons/prev.png" class="prev"/>
		<input type="text" class="pagedisplay"/>
		<img src="../resources/images/icons/next.png" class="next"/>
		<img src="../resources/images/icons/last.png" class="last"/>
		<select class="pagesize">
			<option selected="selected"  value="10">10</option>
			<option value="20">20</option>
			<option value="30">30</option>
			<option value="40">40</option>
		</select>
	</form>
</div>
           </c:if>
       </center>
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
 </div>
                        </div>
                </div>
       
    </body>
</html>