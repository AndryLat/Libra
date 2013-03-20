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
          <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Поиск студентов</title>
        <script>
function getFact(){
 $.post("faculty.html",{"universityId":$("#univ").val() },
                function(data) {
                   $("#fact").html(data);
                      getDept();
                }   
           );
    }
  function getDept(){
 $.post("department.html",{"facultyId":$("#fact").val() },
                function(data) {
                   $("#dept").html(data);
                }        
           );
    }  
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
             <div class="pull-left" style="margin-top: -15px">
             <form method="post" action="showStudentByEducation.html">
        <table border="0px">
        <tr>   
            <td>
                Университет:
            </td>
            <td>
                <select onchange="getFact();" name="univ" id="univ">
                <option value="0"> Все </option> 
                <c:forEach items="${univers}" var="u">
                    <option value="${u.universityId}"> ${u.universityName}</option> 
                </c:forEach>  
                </select> 
            </td>
       </tr>
       <tr>
            <td>
                Факультет:
            </td>
            <td>
            <select onchange="getDept();" name="fact" id="fact">
             <option value="0"> Выберите университет </option> 
            </select>
            </td>
       </tr>
       <tr>
            <td>
                Кафедра:
            </td> 
            <td> 
                <select name="dept" id="dept">
                <option value="0"> Выберите факультет </option> 
                </select>
            </td>
       </tr>
      </table>   
        </form>          
             </div>
         <div class="pull-right" style="margin-right: 330px;margin-top: -15px;">
        <form method="post" action="showStudentbyIdView.html">
            
        <select name="filter">
        <option value="1" ${filterInt == '1' ? 'selected' : ''}>Показать всех</option>
        <option value="2" ${filterInt == '2' ? 'selected' : ''}>Номер анкеты</option>
        <option value="3" ${filterInt == '3' ? 'selected' : ''}>Имя</option>
        <option value="4" ${filterInt == '4' ? 'selected' : ''}>Фамилия</option>
        <option value="5" ${filterInt == '5' ? 'selected' : ''}>Email</option>
        <option value="6" ${filterInt == '6' ? 'selected' : ''}>По всем полям</option>
</select><br>
   <form method="GET">
    <input type="text" name="textBox" value="${textBox}"><br>
    
    <br>
    <center>
    <input type="submit" class="btn btn-large btn-primary" style="width:35x;height:30px;margin-left: 100px;font-size:15px; line-height: 5px" value="Показать">
         </center> 
            </form>
    </div>
                            
    </div></div>
                        <div class="span9">
				<div class="hero-unit">
    <form method="GET">
        <h4>${msg}</h4>
        <TABLE border ="1" class="bordered">
    <th>
        <a href="sortedBy.html?orderBy=appId&direction=asc&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">№ анкеты
        </a>
    </th>
    <th>
        <a href="sortedBy.html?orderBy=firstName&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">Имя
        </a>
    </th>
    <th>
        <a href="sortedBy.html?orderBy=lastName&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">Фамилия
        </a>
    </th >
   <th>
       <a href="sortedBy.html?orderBy=email&textBox=<c:out value='${textBox}'/>&filter=<c:out value='${filterInt}'/>">Email
       </a>
   </th>
            <th>Действия</th>
    </form>
    <c:forEach items="${Model}" var="s">
    <tr>
            <td><input type="hidden" name="appId" value="<c:out value='${s.getAppId()}'/>"/>${s.getAppId()}</td>
            <td><input type="hidden" name="firstName" value="<c:out value='${s.getName()}'/>"/>${s.getName()}</td>
            <td><input type="hidden" name="lastName" value="<c:out value='${s.getLastName()}'/>"/>${s.getLastName()}</td>
            <td><input type="hidden" name="email" value="<c:out value='${s.getEmail()}'/>"/>${s.getEmail()}</td>
            <td> 
                <a href="">
                            <img  src="../resources/images/appForm.png" width="25" height="25" title="Анкета"/>
                        </a>
                <a href="showStudentInterview.html?appId=<c:out value='${s.getAppId()}'/>&firstName=<c:out value='${s.getName()}'/>&lastName=<c:out value='${s.getLastName()}'/>&view=0">
                            <img  src="../resources/images/inter.png" width="25" height="25" title="Интервью"/>
                        </a>
                    <a href="deleteStudent.html?appId=<c:out value='${s.getAppId()}'/>">
                            <img  src="../resources/images/delete.png" width="25" height="25" title="Удалить"/>
                        </a>
      </td>
  </tr>
    </c:forEach>
    </TABLE>
    <h4>${msg1}</h4>
                        </div>
                        </div>
                </div>
        </div>
    </body>
</html>