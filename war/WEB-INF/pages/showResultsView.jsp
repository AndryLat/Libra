<%-- 
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Результаты собеседования</title>
    <link href="resources/css/docs.css" rel="stylesheet">
    <link href="resources/js/google-code-prettify/prettify.css" rel="stylesheet">
    <link href="resources/css/template.css" rel="stylesheet">	
        <jsp:include page="resources.jsp" />
    </head>
    <body>
        <div class="navmenu">
		<jsp:include page="navbar.jsp" />
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="sidebar">
				<jsp:include page="sidebar.jsp" />
			</div>
                        
                        <div class="well-template span8">
			<table class="table-striped table-condensed table-template" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr>
                    <th>№</th>
                    <th>№ анкеты</th>
                    <th>ФИО</th>
                    <th>Дата собеседования</th>
                    <th>Средний бал</th>
                    <th>Просмотреть комментарии</th>
                </tr>
            </thead>
            <% int i=1;%>
            <c:forEach items="${showStudents}" var="student">
            <tr>
                <td>
                    <%=i%>
                </td>
                <td class="align-center">
                    ${student.getAppId()}
                </td>
                <td>
                    ${student.getFio()}
                </td>
                <td class="align-center">
                    ${student.getiDate()}
                </td>
                <td>
                    ${student.getAvgMark()}
                </td>
                <td>
                    <a href="addResult.html?InterviewId=<c:out value='${student.getInterviewId()}'/>">Комментарии</a>
                </td>
            </tr>
            <% i++;%>
            </c:forEach>
        </table>
            </div>
		</div>
	</div>
        
    </body>
</html>
