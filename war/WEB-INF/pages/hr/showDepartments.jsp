<%-- 
    Document   : showDepartments
    Created on : 22.02.2013, 12:05:03
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
        <title>Управление кафедрами</title>
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript" charset="utf-8">   
            </script>
     <script type="text/javascript" src="../resources/js/sort.js"></script>
     <script type="text/javascript">			
        function getFact(){
                $.post("faculty.html",{"universityId":$("#univ").val() },
                    function(data) {
                    $("#fact").html(data);
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
				<div class="hero-unit" style="padding-bottom: 170px">
        <div class="pull-left" style="margin-top: -20px">
            <form name="Form" action="addDepartmentAdded.html" method="get">
                <table border="0px">
                    <tr>
                        <td>
                            Кафедра: </td>
                        <td>
                <input type="text" name ="deptName">
                        </td>
                    </tr>
                    <tr>
                        <td>
                Университет: 
                </td>
                <td> 
                <select onchange="getFact();" name="univ" id="univ">
                    <option value="0"> - </option> 
                    <c:forEach items="${univers}" var="u">
                    <option value="${u.universityId}"> ${u.universityName}  
                    </option> 
                    </c:forEach>  
                </select> 
                </td></tr>
                    <tr>
                        <td>
                            Факультет:
                        </td>
                        <td>
                <select name="fact" id="fact">
                    <option value="0"> Выберите университет </option> 
                </select>
                            <br></td>
                    </tr>
                </table>
                <input type="submit" class="btn btn-large btn-success" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Добавить" name="add">
            </form>
        </div>
                  <div class="pull-right" style="margin-right: 100px;margin-top: -20px">                           
            <form name="myForm" action="showDepartmentsSearch.html" method="get">
        <select name="departmentSearch">
            <option value="0">Все </option>
            <option value="1">№ кафедры </option>
            <option value="2">Кафедра</option>
            <option value="3">Факультет</option>
            <option value="4">Университет</option>
        </select>
                <br>
        <input type="text" name ="textBox"><br>
        <input type="submit" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Показать" name="search">
            </form>
                  </div></div></div>
                        <div class="span9">
				<div class="hero-unit">
       <h4>${msg}</h4>
        <table border ="1" class="bordered">
            <thead>
            <tr>  
                <th><a href="#">№ кафедры</a> </th>
                <th><a href="#">Кафедра</a></th>
                <th><a href="#">Факультет</a></th>
                <th><a href="#">Университет</a></th>
                <th>Действия</th>
            </tr>
            </thead>
            <tbody>
               
           <c:forEach items="${depts}" var="d">
                <tr>                
                    <td><c:out value="${d.departmentId}"/></td>
                    <td><c:out value="${d.departmentName}"/></td>
                    <td><c:out value="${d.facultyName}"/></td>
                    <td><c:out value="${d.universityName}"/></td>
                    <td>
                        <a href="editDepartment.html?departmentId=<c:out value='${d.departmentId}'/>">
                           <img  src="../resources/images/edit.png" width="25" height="25" title="Править"/> 
                        </a>
                        <a href= "delDepartment.html?departmentId=<c:out value='${d.departmentId}'/> ">
                            <img  src="../resources/images/delete.png" width="25" height="25" title="Удалить"/>
                        </a> 
                    </td>
                </tr>        
        </c:forEach>
            </tbody>
        </table>
                                </div>
                                </div>
                        </div>
                </div>
    </body>
</html>
