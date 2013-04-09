<%-- 
    Document   : showFacultiesSearch
    Created on : 22.02.2013, 11:30:13
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
        <jsp:include page="../sidebar.jsp" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Управление факультетами - Поиск </title>
        <script type="text/javascript" src="../resources/js/jquery-latest.js"></script>
        <script type="text/javascript" src="../resources/js/jquery.tablesorter.js"></script>
	<script type="text/javascript" src="../resources/js/jquery.tablesorter.pager.js"></script>
	<script type="text/javascript" src="../resources/js/chili/chili-1.8b.js"></script>
	<script type="text/javascript" src="../resources/js/docs.js"></script>
         <link rel="stylesheet" type="text/css" href="../resources/css/table_.css" />
         <script src="http://www.google-analytics.com/urchin.js" type="text/javascript"></script>
         <script>    
 $(function() {
		$("table")
			.tablesorter({widthFixed: true, widgets: ['zebra']})
			.tablesorterPager({container: $("#pager")});
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
				
			</div>
	<div class="span9">
	<div class="hero-unit" style="padding-bottom: 170px">
        <div class="pull-left" style="margin-top: -40px">
        <form name="Form" action="addFacultiesAdded.html" method="get"> 
                Введите название факультета: <br>
                <input type="text" placeholder="Введите название факультета" name ="facultyName"><br>
                Выберите университет: <br>
                <select name="univ" id="univ">
                    <c:forEach items="${univers}" var="u">
                    <option value="${u.universityId}"> ${u.universityName}  </option> 
                    </c:forEach>  
                 </select> <br>
                <input type="submit" value="Добавить" class="btn btn-large btn-success" style="width:35x;height:30px;font-size:15px; line-height: 5px" name="add">
            </form>
        </div>
            <div class="pull-right" style="margin-right: 100px">
            <form name="myForm" action="showFacultiesSearch.html" method="get">
        <select name="facultySearch">
            <option value="0" ${facultySearchInt == '0' ? 'selected' : ''}>Все </option>
            <option value="1" ${facultySearchInt == '1' ? 'selected' : ''}>№ факультета </option>
            <option value="2" ${facultySearchInt == '2' ? 'selected' : ''}>Факультет </option>
            <option value="3" ${facultySearchInt == '3' ? 'selected' : ''}>Университет</option>
        </select><br>
                <input type="text" name ="textBox" value="${textBoxString}"><br>
        <input type="submit" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Показать" name="search"><br>
            </form>
            </div>
                                </div>
                        </div>
        <div class="span9">
        <table border ="1" class="tablesorter">
            <caption><div class="alert alert-info">Факультеты</div></caption>
            <thead>
            <tr>
                <th><a href="#">№ факультета</a> </th>
                <th><a href="#">Факультет</a></th>
                <th><a href="#">Университет</a></th>
                <th>Править</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
           <c:forEach items="${facts}" var="f">
                <tr>             
                    <td><c:out value="${f.facultyId}"/></td>
                    <td><c:out value="${f.facultyName}"/></td>
                    <td><c:out value="${f.universityName}"/></td>
                    <td>
                        <a href="editFaculty.html?facultyId=<c:out value='${f.facultyId}'/>">
                            <img  src="../resources/images/edit1.png" width="25" height="25" title="Править"/>
                        </a>
                    </td>
                    <td>
                        <a href= "delFaculty.html?facultyId=<c:out value='${f.facultyId}'/> ">
                            <img  src="../resources/images/delete.png" width="20" height="20" title="Удалить"/>
                        </a> 
                    </td>
                </tr>
        </c:forEach>
            </tbody>
        </table>
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
                                </div>
                        </div>
                </div>
    </body>
</html>
