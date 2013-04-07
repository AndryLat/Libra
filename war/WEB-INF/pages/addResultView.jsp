<%-- 
    Author     : Sashenka
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLine", "\n"); %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Добавить результаты</title>
        <jsp:include page="resources.jsp" />
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <link href="resources/css/bootstrap.css" rel="stylesheet">
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="resources/css/docs.css" rel="stylesheet">
    <link href="resources/js/google-code-prettify/prettify.css" rel="stylesheet">
    <link href="resources/css/template.css" rel="stylesheet">
         <script src="resources/js/template.js"></script>
         <script src="resources/js/template.js"></script>
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
			<c:if test="${existsComment==0}">
                        <form class="well span8" method="POST" action="addResultSubmit.html">
                            <input type="hidden" name="appId" value="${appId}"/>
                            <label>Оценка:</label><input class="width96" onclick="javascript:number_control()" id="quantity" type="text" name="mark"/>
                            </br>
                            <label>Комментарий:</label><textarea rows="5" class="width96" name="comment" ></textarea>
                            </br>
                            <input type="submit" value="Добавить"/>
                        </form>   
                        </c:if>

        <c:forEach items="${interviewResult}" var="ir">
            <div class="well-template span8"> 
            <h3>${ir.getFio()}</h3>
        <c:if test="${userId==ir.getUserId()}">
        <form method="POST" action="updateResultSubmit.html">
            <input type="hidden" name="appId" value="${appId}"/>
        </c:if>
            <p>Оценка: </p>
                <div class="nya<c:out value='${ir.getUserId()}'/>">${ir.getMark()}</div>
                <div class="nya<c:out value='${ir.getUserId()}'/>" style="display:none">
                    <c:if test="${userId==ir.getUserId()}">
                        <input type="text" class="width96" name="mark" value="<c:out value='${ir.getMark()}'/>"/>
                    </c:if>
                </div>
            
            <p>Комментарий: </p>
                <div class="nya<c:out value='${ir.getUserId()}'/>">
                  <%--  ${ir.getComments()}    --%> 
                    ${fn:replace(ir.getComments(), newLine, "</br>")}
                </div>
                <div class="nya<c:out value='${ir.getUserId()}'/>" style="display:none">
                    <c:if test="${userId==ir.getUserId()}">
                        <textarea class="width96" rows="5" name="comment">${ir.getComments()}</textarea>
                    </c:if>
                </div>
            
        <c:if test="${userId==ir.getUserId()}">
            <div style="display:none" class="nya<c:out value='${ir.getUserId()}'/>">
                <input type="submit" value="Внести изменения"/>
            </div>
        </form>   
        </c:if>
            <c:if test="${userId==ir.getUserId()}">
                <a href="#" onclick="javascript:toggleedit('.nya<c:out value='${userId}'/>');">Редактировать</a> <a href="deleteResult.html?appId=<c:out value='${appId}'/>">Удалить</a>              
            </c:if>
            </div>    
        </c:forEach>
		</div>
	</div>
        
    </body>
</html>
