<%-- 
    Document   : showLanguagesSearch
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
        <title>Управление языками - Поиск</title>
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript" charset="utf-8">   
</script>
       <script type="text/javascript" src="../resources/js/sort.js"></script>
       <script>
           function closeMessage()
            {
                document.getElementById("alertMessage").innerHTML="";
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
        <div class="pull-left">
           <form name="Form" action="addLanguageAdded.html" method="get">
                Язык: <br> 
                <input type="text"  placeholder="Введите название языка" name ="langName"><br>
                <input type="submit" class="btn btn-large btn-success" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Добавить" name="add">
            </form>
        </div>
            <div class="pull-right" style="margin-right: 100px">
            <form name="myForm" action="showLanguagesSearch.html" method="get">
        <select name="langSearch">
            <option value="0" ${langSearchInt == '0' ? 'selected' : ''}>Все </option>
            <option value="1" ${langSearchInt == '1' ? 'selected' : ''}>№ языка </option>
            <option value="2" ${langSearchInt == '2' ? 'selected' : ''}>Язык</option>
        </select><br>
                <input type="text"  placeholder="Введите значение" name ="textBox" value="${textBoxString}"><br>
        <input type="submit" class="btn btn-large btn-primary" style="width:35x;height:30px;font-size:15px; line-height: 5px" value="Показать" name="search"> </form>
            </form>
            </div>
                                </div>
                        </div>
                      <div class="span9">
        <center>
       <table width="100%">
         <tr>
         <c:if test='${!errorMessage.equals("") && errorMessage != null}'>
                                    <span id="alertMessage">
                                    <div class="alert alert-error" align="center">
                                        <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                                        ${errorMessage}
                                    </div>
                                    </span>
                                </c:if>
                                        </tr>
         </table>
         <table width="100%">
         <tr>
         <c:if test="${message != null}">
                 <td align="left" id="alertMessage">
                     <div class="alert alert-success" align="center">
                         <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                         ${message}
                     </div>
                 </td>
             </c:if>
         </tr>
		 </table>
       
        <table border ="1" class="bordered">
            <caption><div class="alert alert-info">Языки программирования</div></caption>
            <thead>
                <tr>
                    <th><a href="#">№</a> </th>
                    <th><a href="#">Язык</a></th>
                    <th>Править</th>
                    <th>Удалить</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${languages}" var="l">
                    <tr>
                    <td><c:out value="${l.languageId}"/></td>
                    <td><c:out value="${l.languageName}"/></td>
                    <td>
                        <a href="editLanguage.html?languageId=<c:out value='${l.languageId}'/>">
                            <img  src="../resources/images/edit1.png" width="25" height="25" title="Править"/>
                        </a>
                    </td>
                    <td>
                        <a href= "delLanguage.html?languageId=<c:out value='${l.languageId}'/> ">
                            <img  src="../resources/images/delete.png" width="20" height="20" title="Удалить"/>
                        </a> 
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table></center>
                        </div>
                </div>
        </div>
    </body>
</html>
