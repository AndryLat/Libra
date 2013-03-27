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
     <script src="resources/css/bootstrap.jsp"></script>
    <script src="resources/css/bootstrap.mini.jsp"></script>    
    
    <script src="http://bootsnipp.com/js/jquery.js"></script>
	<script src="http://bootsnipp.com/js/bootstrap.min.js"></script>

    <script src="http://bootsnipp.com/js/prettify.js"></script>
    <script src="http://bootsnipp.com/js/codemirror.js"></script>
<script src="http://bootsnipp.com/js/humane.min.js"></script>
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>  
<script src="resources/js/template.js"></script>
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
                        
                            
                            <div class="navbar">
	<div class="navbar-inner">
		<div class="container-fluid">
			
			<a class="brand" href="showResults.html" name="top">Все</a>
			<div class="nav-collapse collapse">
				<ul class="nav">
                                    <li><a href="#">Отправить письмо</a></li>
					<li class="divider-vertical"></li>
                                    <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Сортировать <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="showResults.html?order=results<c:if test='${currentpage!=null && count!=null}'>&page=<c:out value='${currentpage}'/>&count=<c:out value='${count}'/></c:if><c:if test="${order=='results'}">&desc=<c:out value='${!desc}'/></c:if>">По результатам</a></li>
              <li><a href="showResults.html?order=appId<c:if test='${currentpage!=null && count!=null}'>&page=<c:out value='${currentpage}'/>&count=<c:out value='${count}'/></c:if><c:if test="${order=='appId'}">&desc=<c:out value='${!desc}'/></c:if>">По номеру анкеты</a></li>
              <li><a href="showResults.html?order=email<c:if test='${currentpage!=null && count!=null}'>&page=<c:out value='${currentpage}'/>&count=<c:out value='${count}'/></c:if><c:if test="${order=='email'}">&desc=<c:out value='${!desc}'/></c:if>">По email</a></li>
              <li><a href="showResults.html?order=lastname<c:if test='${currentpage!=null && count!=null}'>&page=<c:out value='${currentpage}'/>&count=<c:out value='${count}'/></c:if><c:if test="${order=='lastname'}">&desc=<c:out value='${!desc}'/></c:if>">По фамилие</a></li>            

</ul>
          </li>     
					<li class="divider-vertical"></li>
					<li class="dropdown">
						<a class="dropdown-toggle" href="#" data-toggle="dropdown">Формат вывода <strong class="caret"></strong></a>
						<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
							<form method="post" action="showResults.html?page=1<c:if test='${order!=null}'>&order=<c:out value='${order}'/>&desc=<c:out value='${desc}'/></c:if>" accept-charset="UTF-8">	
                                                            <input style="margin-bottom: 15px;" type="text" placeholder="Количество строк на экране" name="count">
                                                                <input class="btn btn-primary btn-block" type="submit" id="sign-in-twitter" value="Применить">
							</form>
						</div>
					</li>
					
					
				</ul>
				<ul class="nav ">					
                  	<li class="divider-vertical"></li>
					<li class="dropdown">
						<a class="dropdown-toggle" href="#" data-toggle="dropdown">Поиск <strong class="caret"></strong></a>
						<div class="dropdown-menu" style="padding: 15px; padding-bottom: 0px;">
							<form method="post" action="showResults.html" accept-charset="UTF-8">
								<input style="margin-bottom: 15px;" type="text" placeholder="Поле поиска"  name="serch">
                                                                <input class="btn btn-primary btn-block" type="submit" id="sign-in-twitter" value="Найти">
							</form>
						</div>
					</li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
		<!--/.container-fluid -->
	</div>
	<!--/.navbar-inner -->
</div>
<!--/.navbar -->
 
<script>
$(document).ready(function()
{
  //Handles menu drop down
  $('.dropdown-menu').find('form').click(function (e) {
        e.stopPropagation();
        });
  });
</script>
                            
                            
<c:if test="${serch!=null}">
    <h4 class="align-center">Результат поиска</h4> 
</c:if>      
    <input type="button" value="click me" onclick="ajax_result()" />
			<table class="table-striped table-condensed table-template" border="1" cellspacing="0" cellpadding="4">
            <thead>
                <tr>
                    <th>№</th>
                    <th>
                        № анкеты 
                        <c:if test="${desc==false &&order=='appId'}">&darr;</c:if>
                        <c:if test="${desc==true &&order=='appId'}">&uarr;</c:if>
                    </th>
                    <th>
                        ФИО 
                        <c:if test="${desc==false &&order=='lastname'}">&darr;</c:if>
                        <c:if test="${desc==true &&order=='lastname'}">&uarr;</c:if>
                    </th>
                    <th>
                        email
                        <c:if test="${desc==false &&order=='email'}">&darr;</c:if>
                        <c:if test="${desc==true &&order=='email'}">&uarr;</c:if>
                    </th>
                    <th>Оценка     
                        <c:if test="${desc==false &&order=='results'}">&darr;</c:if>
                        <c:if test="${desc==true &&order=='results'}">&uarr;</c:if>
                    </th>
                    <th>Комментарии</th>
                </tr>
            </thead>
            <tbody id="studentTable">
            <c:forEach items="${showStudents}" var="student">
            <tr>
                <td>
                    ${student.getR()}
                </td>
                <td class="align-center">
                    ${student.getAppId()}
                </td>
                <td>
                    ${student.getFio()}
                </td>
                <td>
                    ${student.getEmail()}
                </td>
                <td>
                    ${student.getAvgMark()}
                </td>
                <td>
                    <a href="addResult.html?appId=<c:out value='${student.getAppId()}'/>">Комментарии</a>
                </td>
            </tr>
            </c:forEach>
            </tbody>
        </table>
                        <c:if test="${pages>1}">
                        <div class="pagination align-center">
                            <ul>
                              <li <c:if test="${currentpage==1}"> class="disabled"</c:if>>
                                  <a <c:if test="${currentpage!=1}"> href="showResults.html?page=<c:out value='${currentpage-1}'/>&count=<c:out value='${count}'/>&order=<c:out value='${order}'/>&desc=<c:out value='${desc}'/>" </c:if>>Prev</a>
                              </li>                           
                              
                              <c:forEach var="i" begin="1" end="${pages}">
                                 <li <c:if test="${i==currentpage}"> class="active"</c:if>>
                                     <a href="showResults.html?page=<c:out value='${i}'/>&count=<c:out value='${count}'/>&order=<c:out value='${order}'/>&desc=<c:out value='${desc}'/>">${i}</a>
                                 </li>
                              </c:forEach>
                                 
                              <li <c:if test="${currentpage==pages}"> class="disabled"</c:if>>
                                  <a <c:if test="${currentpage<pages}">  href="showResults.html?page=<c:out value='${currentpage+1}'/>&count=<c:out value='${count}'/>&order=<c:out value='${order}'/>&desc=<c:out value='${desc}'/>" </c:if>>Next</a>
                              </li>
                            </ul>
                        </div>
                        </c:if>
            </div>
		</div>
	</div>
        
    </body>
</html>
