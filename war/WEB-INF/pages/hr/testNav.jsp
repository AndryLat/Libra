<%-- 
    Document   : testNav
    Created on : 25.03.2013, 1:15:30
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<head>
         <jsp:include page="../resources.jsp" />   
         <jsp:include page="../navbar.jsp" />
         <jsp:include page="../sidebar.jsp" />
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Поиск студентов</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
        <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.tablesorter.pager.js"></script>
	<script type="text/javascript" src="../resources/js/chili/chili-1.8b.js"></script>
	<script type="text/javascript" src="../resources/js/docs.js"></script>
	<script type="text/javascript">
	$(function() {
		$("table")
			.tablesorter({widthFixed: true, widgets: ['zebra']})
			.tablesorterPager({container: $("#pager")});
	});
	</script>
        <script>          
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
         <div class="pull-left" style="margin-top: -15px">
             <input type="hidden" name="myUniver" id="myUniver" value="${selectedUniv}">
             <input type="hidden" name="myFact" id="myFact" value="${selectedFact}">
             <input type="hidden" name="myDept" id="myDept" value="${selectedDept}">
             <table border="0px" class="tablesorter">
            <thead>
        <tr>   
            <td>
                Университет:
            </td>
            <td>
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
           
            <td>
                Факультет:
            </td>
            <td>
            <select onchange="getDept();" name="fact" id="fact">
             <option value="0"> Все </option> 
            </select>
            </td>
       </tr>
       <tr>
            <td>
                Кафедра:
            </td> 
            <td> 
                <select name="dept" id="dept">
                <option value="0"> Все </option> 
                </select>
            </td>
       </tr>
            </tbody>
      </table>  
        
<div class="pull-right" style="margin-right: 250px;margin-top: -15px;">
    <form method="POST" action="showStudentbyIdView.html">
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
    
    <br>
    <center>
    <input type="submit" class="btn btn-large btn-primary" style="width:35x;height:30px;margin-left: 100px;font-size:15px; line-height: 5px" value="Показать">
      </center>   
    </form>
    </div>                        
   
        </div>
    
 <TABLE border ="1" class="tablesorter">
        <caption><div class="alert alert-info">Студенты</div></caption>
        <thead>
        <tr>
    <th>
        <a href="sortedBy.html?orderBy=appId&direction=asc&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">
            №
        </a>
    </th>
    <th>
        <a href="sortedBy.html?orderBy=firstName&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">
            Имя
        </a>
    </th>
    <th>
        <a href="sortedBy.html?orderBy=lastName&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">
            Фамилия
        </a>
    </th >
   <th>
       <a href="sortedBy.html?orderBy=email&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">
           Email
       </a>
   </th>
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
          <div id="pager" class="pager">
	<form>
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
<script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
 </div>
                        </div>
       
    </body>
</html>


