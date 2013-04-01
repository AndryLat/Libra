<%-- 
    Author     : Sashenka
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Типы ответов</title>  
        <link href="resources/css/bootstrap.css" rel="stylesheet">
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="resources/css/docs.css" rel="stylesheet">
    <link href="resources/js/google-code-prettify/prettify.css" rel="stylesheet">
    <link href="resources/css/template.css" rel="stylesheet">		
    <script src="resources/js/template.js"></script>
    <jsp:include page="resources.jsp" />
        	<%--<link href="http://bootsnipp.com/bundles/bootstrapper/css/bootstrap.min.css" media="all" type="text/css" rel="stylesheet">
<link href="http://bootsnipp.com/bundles/bootstrapper/css/bootstrap-responsive.min.css" media="all" type="text/css" rel="stylesheet">
        --%>
        <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>  

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

        <% int i=1; %>
        <%--<form class="well-template span8" action="delType.html" method="POST">--%>
        <div class="well-template span8">
            <h4><a href="addType.html">Добавить тип</a></h4>
            <hr>
        <table class="table-striped table-condensed table-font" border="1" cellspacing="0" cellpadding="4">
        <caption>Информация о типах данных</caption>
        <tr>
            <th class="checkbox-shift">
                <a href="#" onclick="submitDelete('delType.html',':checkbox[name^=type]')"><img src="resources/images/del.png" width="25" height="25" title="Удалить" /></a>
 
           </br><input id="one" type="checkbox" name="one" value="all" onclick="cbToggle();" />
            </th>
            <th>№</th>
            <th>Название</th>
            <th>Значение</th>
            <th>Изменить</th>
        </tr>
        <c:forEach items="${types}" var="t">            
        <tr>
            <td class="checkbox-shift">
                <%--
                <a href="delType.html?type=<c:out value='${t.getTypeId()} '/>"><img src="resources\images\del.jpg"  width="25" height="25" border="0" title="удалить"/></a>
                --%>
                <input  type="checkbox" class="case" name="types[]" value="<c:out value='${t.getTypeId()}'/>"/>        
            </td>

            <td>
                <%=i%>
                <%--${t.getTypeId()} --%>
                
            </td>
            <c:if test="${t.getTypeName()=='textstring'}">
            <td>Однострочное текстовое поле </td>
            </c:if>
            <c:if test="${t.getTypeName()=='areastring'}">
            <td>Многострочный текст </td>
            </c:if>
            <c:if test="${t.getTypeName()=='integer'}">
            <td>Поле для числа </td>
            </c:if>
            <c:if test="${(t.getTypeName()=='selectenum')}">
            <td>Выпадающий список</td>            
            </c:if>
            <c:if test="${(t.getTypeName()=='checkboxenum')}">
            <td>Флажки</td>            
            </c:if>
            <c:if test="${(t.getTypeName()=='radioenum')}">
            <td>Переключатели</td>            
            </c:if>
            <td>
                <form method="POST" action="showTypes.html">
                <input type="hidden" name="selType" value="<c:out value='${t.getTypeId()}  '/>"/>
                <div class="nya<c:out value='${t.getTypeId()}'/>">${t.getInfoDescription()}</div>
                <div class="nya<c:out value='${t.getTypeId()}'/>" style="display:none">
                        <input class="width96" type="text" name="description" value="<c:out value='${t.getDescription()}'/>"/>
                        <input class="btn btn-primary pull-right" type="submit" value="OK" title="внести изменения" />           
                </form>
                </div>  
               <%-- ${t.getDescription()}--%>
            </td>
            
            <td class="checkbox-shift">
                <%-- <input type="image"  src="resources\images\edit.png" width="25" height="25" title="внести изменения" OnClick="Forma1.submit()"/>--%>
                
                <%--    <input type="image"  src="resources\images\edit.png" width="25" height="25" title="внести изменения" onclick="location.href='showTypes.html'"/>--%>
            <%-- <input type="image"  src="resources/images/edit.png" width="25" height="25" title="внести изменения" onclick="location.href='showTypes.html'"/>
--%>

<a href="javascript:$('.nya<c:out value='${t.getTypeId()}'/>').toggle()"><img src="resources/images/edit.png" width="25" height="25" title="внести изменения"/></a>                        
    
             <%--  <a href="showTypes.html"><img src="../../resources/images/edit.png"/></a> --%>
            </td>
            <%--<td>
                <input type="text" name="name"/>
            </td>--%>
        </tr>

            <% i++;%>
        </c:forEach>
        </table>
           <%-- </form>--%>
           </div>
		</div>
	</div>
        
    </body>
</html>
