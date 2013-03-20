<%-- 
    Document   : showUniversitiesSearch
    Created on : 20.02.2013, 17:45:27
    Author     : Yuliya
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title>Управление университетами - Поиск</title>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript" charset="utf-8">   
</script>
        <script type="text/javascript" src="../resources/js/sort.js"></script>
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
        <form name="Form" action="addUniversitiesAdded.html" method="get">
                Введите название университета: <br>
                <input type="text" name ="univerName"> <br>
                <input type="submit" class="btn btn-large btn-success" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Добавить" name="add">
            </form>
        </div>                        
         <div class="pull-right" style="margin-right: 100px">
           
            <form name="myForm" action="showUniversitiesSearch.html" method="get">
        <select name="univerSearch">
            <option value="0" ${univerSearchInt == '0' ? 'selected' : ''}>Все </option>
            <option value="1" ${univerSearchInt == '1' ? 'selected' : ''}>№ университета </option>
            <option value="2" ${univerSearchInt == '2' ? 'selected' : ''}>Университет</option>
        </select><br>
                <input type="text" name ="textBox" value="${textBoxString}"><br>
        <input type="submit" value="Показать" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" name="search">
            </form>
         </div></div></div>
        <div class="span9">
				<div class="hero-unit">
         <h4>${msg}</h4>
        <table border ="1" class="bordered">
            <thead>
                <tr>
                    <th><a href="#">№ университета</a> </th>
                    <th><a href="#">Университет</a></th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${univers}" var="u">
                    <tr>
                    <td><c:out value="${u.universityId}"/></td>
                    <td><c:out value="${u.universityName}"/></td>
                    <td>
                      <a href="editUniversity.html?universityId=<c:out value='${u.universityId}'/>">
                            <img  src="../resources/images/edit.png" width="25" height="25" title="Править"/>
                        </a>
                        <a href= "delUniversity.html?universityId=<c:out value='${u.universityId}'/> ">
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
