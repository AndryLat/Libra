<%-- 
    Document   : addUniversities
    Created on : 22.02.2013, 13:14:51
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
        <title>Управление университетами - Добавить университет </title>
        <link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
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
				<div class="hero-unit">
        <center>
            <h3>${msg}</h3>
            <form name="Form" action="addUniversitiesAdded.html" method="get">
                Введите название университета: 
                <input type="text" name ="univerName">
                <input type="submit" value="Добавить" name="add">
            </form>
            <br>
        <form name="myForm" action="showUniversitiesSearch.html" method="get">
        <select name="univerSearch">
            <option value="0"  ${univerSearchInt == '0' ? 'selected' : ''}>Все </option>
            <option value="1"  ${univerSearchInt == '1' ? 'selected' : ''}>№ университета </option>
            <option value="2"  ${univerSearchInt == '2' ? 'selected' : ''}>Университет</option>
        </select>
        <input type="text" name ="textBox" value="${textBox}">
        <input type="submit" value="Показать" name="search">
            </form>
        <br><br>
        <table border ="1" class="bordered">
            <thead>
            <tr>
                <th><a href="#">№ университета</a> </th>
                <th><a href="#">Университет</a></th>
                <th>Править</th>
                <th>Удалить</th>
            </tr>
            </thead>
            <tbody>
           <c:forEach items="${univers}" var="u">
                <tr>
                    <td><c:out value="${u.universityId}"/></td>
                    <td><c:out value="${u.universityName}"/></td>
                    <td>
                        <a href="editUniversity.html?universityId=<c:out value='${u.universityId}'/>">
                            править
                        </a>
                    </td>
                    <td>
                        <a href= "delUniversity.html?universityId=<c:out value='${u.universityId}'/> ">
                            удалить
                        </a> 
                    </td>
                </tr>
        </c:forEach>
                </tbody>
        </table>
        </center>
                                </div>
                        </div>
                </div>
        </div>
    </body>
</html>
