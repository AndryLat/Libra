<%-- 
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% pageContext.setAttribute("showSwop", false); %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Колонки</title>
        <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="resources/css/docs.css" rel="stylesheet">
    <link href="resources/js/google-code-prettify/prettify.css" rel="stylesheet">
    <link href="resources/css/template.css" rel="stylesheet">	
    	       <script src="resources/js/template.js"></script>
        <jsp:include page="resources.jsp" />

        <style type="text/css">
  </style>
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
<form class="well-template span8" method="POST" action="SubmitColumn.html">
            Введите имя колонки <input class="span-table" type="text" name="name" /></br>
            Выберите тип колонки<select class="span-table" name="selType">
                <option value="0">Колонка для инфорации</option>
                <c:forEach items="${types}" var="t">
                    <option value="${t.getTypeId()}"><c:out value="${t.getInfoDescription()}" /></option>
                </c:forEach>
            </select></br>
            Выберите тип колонки<select class="span-table" name="parentColumn">
                <option value="0">Нету родителя</option>
                <c:forEach items="${columns}" var="c">
                    <option value="${c.getColumnId()}"><c:out value="${c.getName()}" /></option>
                </c:forEach>
            </select></br>
           <input class="btn btn-primary "   type="submit" value="OK"/>
</form>
                     
          <%--  <form class="well-template span8" action="delColumns.html" method="POST">--%>
          <div class="well-template span8">
    <table class="table-striped table-condensed table-template" border="1" cellspacing="0" cellpadding="4">
        <caption>Информация о колонках</caption>
        <thead>
        <tr>
            <th>
             <a href="#" onclick="submitDelete('delColumns.html',':checkbox[name^=delete]')"><img src="resources/images/del.png" width="25" height="25" title="Удалить" /></a>
             </br><input id="one" type="checkbox" name="one" value="all" onclick="cbToggle();" />
           
            </th>
            <th>Номер</th>
            <th>Название</th>
            <th>Поменять местами с</th>
            <th>Возможные варианты ответов</th>
            <th>Редактировать</th>
        </tr>
        </thead>
        <tbody>  
            <c:forEach items="${columns}" var="c">
                <c:set var="showSwop" value="${false}"/>
            
            <tr>
                <td class="checkbox-shift">
               <input  type="checkbox" class="checkbox" name="delete[]" value="<c:out value='${c.getColumnId()}'/>"/>        
                </td>
                <td>${c.getNumbers()}</td>
                <td>${c.getName()}</td>
                <td> 
                    <c:forEach items="${columns}" var="list">  
                        <c:if test="${(c.getParentColumn()==list.getParentColumn())&&(list.getColumnId()!=c.getColumnId())}" >
                            <c:set var="showSwop" value="${true}"/>
                            
                        </c:if>
                     </c:forEach>
                    <c:if test="${showSwop==true}">
                    <form action="changeColumn.html" method="POST">
                        <input name="column1" type="hidden" value="${c.getColumnId()}">
                      
                      
                <select class="select-change" name="column2" size="1">
                <c:forEach items="${columns}" var="list">  
                <c:if test="${(list.getParentColumn()==c.getParentColumn())&&(list.getColumnId()!=c.getColumnId())}">
                        <option value="${list.getColumnId()}">${list.getName()}</option>
                </c:if>
                </c:forEach>
                </select>  
               
                    <input class="btn btn-primary pull-right"  type="submit" value="OK"/>
                    </form>
                         </c:if>
                    <c:if test="${showSwop==false}">
                        Ее не с кем менять
                    </c:if>
                </td>
                <td>
                    <c:if test="${c.getTypeId()!=0}">
                        ${c.getTypeDescription()}
                    </c:if>
                    <c:if test="${c.getTypeId()==0}">
                        Нету значений
                    </c:if>
                </td>
                <td class="align-center"><a href="editColumn.html?columnId=<c:out value='${c.getColumnId()}'/>"><img class="_img-size" src="resources/images/edit.png"  title="внести изменения"/></a></td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
                </div>
            <%--    </form>--%>
		</div>
	</div>
    
    </body>
</html>
