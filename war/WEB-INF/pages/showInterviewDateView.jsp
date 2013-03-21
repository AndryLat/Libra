<%-- 
    Document   : showInterviewDateView
    Created on : 02.02.2013, 12:57:40
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Записаться на собеседование</title>
        <jsp:include page="resources.jsp" />
        <link href="resources/css/template.css" rel="stylesheet">	
    
        <script src="http://bootsnipp.com/bundles/bootstrapper/js/bootstrap.min.js"></script>

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
                        <div class="span8">    
<c:if test="${request==1}">
<div class="bs-docs-example">
                   
<div class="alert alert-info">
  <button type="button" class="close" data-dismiss="alert">&times;</button>
  <h4>Внимание!</h4>
  <p> <c:out value="${requestDateHr}"></c:out></p>
  <p><c:out value="${requestDateInterview}"></c:out></p>
</div>	
    
</div>
</c:if>              
                            
                            <div class="well-template shift-div-right align-center">
        <c:if test="${interviewDateHr==-1}">
            <form  method="post" action="chooseDate.html">
        </c:if>
        <c:if  test="${interviewDateHr!=-1}">
            <form  method="post" action="changeDate.html">
        </c:if>
               
        <table  border="1" cellspacing="0" cellpadding="4">
            <caption> <h6>Зарегистрироваться на собеседование с HR-ом</h6></caption>
            <tr>
                <th>Выбрать</th>
                <th>Дата</th>
                <th>Время</th>
                <th>Количестов свободных мест</th>
            </tr>            
            <c:forEach items="${interviewDatesHr}" var="idate">  
                <tr>
                    <c:if test="${idate.getId()==interviewDateHr}">
                        <td><input checked name="selDate" type="radio" value="<c:out value='${idate.getId()}'/>"/></td>
                    </c:if> 
                   <c:if test="${idate.getId()!=interviewDateHr}">
                   	<!-- убрал лишние скобки в следующем условиях -->
                       <c:if test="${idate.getFreePlaces()<=0||idate.getCorrect()==-1}">
                       <td>
                           <input disabled name="selDate" type="radio" value="<c:out value='${idate.getId()}'/>"/></td>
                       </c:if>
                       <c:if test="${idate.getFreePlaces()>0&&idate.getCorrect()==1}">
                       <td><input  name="selDate" type="radio" value="<c:out value='${idate.getId()}'/>"/></td>
                       </c:if>
                   </c:if>
                <td>
                    ${idate.getDay()}
                </td>
                <td>
                    ${idate.gethTime()}
                </td>
                <td>${idate.getFreePlaces()}</td>
                </tr>
            </c:forEach>            
                <tr>
                    <td colspan="4">
                        <c:if test="${interviewDateHr==-1}">
                    <input class="btn btn-primary pull-right" type="submit" value="Записаться на собеседование"/>
        </c:if>
        <c:if test="${interviewDateHr!=-1}">
                    <input class="btn btn-primary pull-right" type="submit" value="Перезаписаться на собеседование"/>
        </c:if>
                    </td>
                </tr>
        </table>
        
        </form>
             
                
                
                                
        <c:if test="${interviewDateInterview==-1}">
            <form  method="post" action="chooseDate.html">
        </c:if>
        <c:if test="${interviewDateInterview!=-1}">
            <form  method="post" action="changeDate.html">
        </c:if>
                
        <table  border="1" cellspacing="0" cellpadding="4">
           <caption><h6>Зарегистрироваться на собеседование с техническим специалистом</h6></caption>
            
            <tr>
                <th>Выбрать</th>
                <th>Дата</th>
                <th>Время</th>
                <th>Количестов свободных мест</th>
            </tr>            
            <c:forEach items="${interviewDatesInterview}" var="idate">  
                <tr>
                    <c:if test="${idate.getId()==interviewDateInterview}">
                        <td><input checked name="selDate" type="radio" value="<c:out value='${idate.getId()}'/>"/></td>
                    </c:if> 
                   <c:if test="${idate.getId()!=interviewDateInterview}">
                   	<!-- убрал лишние скобки в следующем условиях -->
                       <c:if test="${idate.getFreePlaces()<=0||idate.getCorrect()==-1}">
                       <td>
                           <input disabled name="selDate" type="radio" value="<c:out value='${idate.getId()}'/>"/></td>
                       </c:if>
                       <c:if test="${idate.getFreePlaces()>0&&idate.getCorrect()==1}">
                       <td><input  name="selDate" type="radio" value="<c:out value='${idate.getId()}'/>"/></td>
                       </c:if>
                   </c:if>
                <td>
                    ${idate.getDay()}
                </td>
                <td>
                    ${idate.gethTime()}
                </td>
                <td>${idate.getFreePlaces()}</td>
                </tr>
            </c:forEach> 
            <tr>
                    <td colspan="4">
                        <c:if test="${interviewDateInterview==-1}">
                    <input class="btn btn-primary pull-right" type="submit" value="Записаться на собеседование"/>
        </c:if>
        <c:if test="${interviewDateInterview!=-1}">
                    <input class="btn btn-primary pull-right" type="submit" value="Перезаписаться на собеседование"/>
        </c:if>
                    </td>
                </tr>    
        </table>
        
        </form>   
           </div>     
               
		</div>
	</div>
        
       </div> 
    </body>
</html>