<%-- 
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Редактирование шаблона</title>
        <link href="resources/css/bootstrap.css" rel="stylesheet">
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet">
    <link href="resources/css/docs.css" rel="stylesheet">
    <link href="resources/js/google-code-prettify/prettify.css" rel="stylesheet">
        <link href="resources/css/template.css" rel="stylesheet">	
    		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="http://code.jquery.com/jquery-migrate-1.1.1.min.js"></script>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="resources/js/template.js"></script>
 <link rel="stylesheet" type="text/css" href="resources/css/table-template.css" />
    </head>
    <body>
        <div class="mincontainer">
        <div class="navmenu">
		<jsp:include page="navbar.jsp" />
	</div>
	
	<div class="container">
		<div class="row">
			<div class="sidebar">
				<jsp:include page="sidebar.jsp" />
			</div>
 <div class="well span8"> 
     <div class="div-left">
         <div >
        <form action="SubmitTemplate.html" method="POST">
        <h4>Добавить новый шаблон</h4>
            <input type="text" name="name">
            </br>
            <input class="btn btn-primary"  type="submit" value="OK">
        </form> 
        </div>
     </div>
<div class="div-left">
<form action="ActiveTemplate.html" method="POST">
        <!--<table border="1" cellspacing="0" cellpadding="4">-->
        <div >
        <h4>Выбрать щаблон главным</h4>
       <select name="activeTemplate">
           ${activeTemplate.getName()}
            <c:forEach items="${templates}" var="t">
                <c:if test="${activeTemplate.getTemplateId() == t.getTemplateId()}">
                <option selected value="${t.getTemplateId()}">
                       ${t.getName()}     
                </option> 
                </c:if>  
                 <c:if test="${activeTemplate.getTemplateId() != t.getTemplateId()}">
                <option  value="${t.getTemplateId()}">
                       ${t.getName()}     
                </option> 
                </c:if>  
            </c:forEach>
        </select>
            </br>
                <input class="btn btn-primary" type="submit" value="OK">
        </div>
            </form>
</div>           
</div>                 
        

        <!--<table border="1" cellspacing="0" cellpadding="4">-->
        <div class="span8">
      <%--  <form class="well-template span8" action="delTemplate.html" method="POST">--%>
        
        <table  class="bordered width100">
        <caption>Информация о существующих шаблонах</caption>
        <thead>
        <tr>
            <th>
                <%--<input type="image"  src="resources/images/del.png" width="25" height="25" title="Удалить" onclick="submitDelete()"/>
--%>
 <a href="#" onclick="submitDelete('delTemplate.html',':checkbox[name^=template]')"><img src="resources/images/del.png" width="25" height="25" title="Удалить" /></a>
        
</br><input id="one" type="checkbox" name="one" value="all" onclick="cbToggle();" />
            </th><th>№</th>
            <th>Название</th>
            <th>Статус</th>
            <th>Действия</th>
        </tr>
        </thead>
              <tbody>
                  <% int i=1;%>
       <c:forEach items="${templates}" var="t">
            
        <tr>
            <td  class="checkbox-shift">
                <c:if test="${t.getActive() != 1}">
                    <%--<a href="delTemplate.html?template=<c:out value='${t.getTemplateId()} '/>"><img src="resources\images\del.jpg"  width="25" height="25" border="0" title="удалить"/></a>
                --%>     
                <input type="checkbox" class="checkbox" name="templates[]" value="<c:out value='${t.getTemplateId()}'/>"/>
                </c:if>
                <c:if test="${t.getActive() == 1}">
                <%--
                    <a href=""><img src="resources\images\del2.png"  width="25" height="25" border="0" title="нельзя удалить активный шаблон"/></a>
                --%>
                <input type="checkbox"   disabled/>
                    
                </c:if>
            </td>
            <form action="showTemplates.html"  method="POST">
            <td>
                <%=i%>
                <%--${t.getTemplateId()} --%>
                <input type="hidden" name="selTemplate" value="<c:out value='${t.getTemplateId()}  '/>"/>
            </td>
            <td>
            <div class="nya<c:out value='${t.getTemplateId()}'/>">${t.getName()}</div>
                <div class="nya<c:out value='${t.getTemplateId()}'/>" style="display:none">
                    
                    <input class="width96" type="text" name="name" value="<c:out value='${t.getName()}'/>"/>
                    <input class="btn btn-primary pull-right" type="submit" value="OK"/>
                </div>    
            </td>
            <td>
            <c:if test="${t.getActive() == 1}">
                    <b>Активный</b>
                </c:if>
            <c:if test="${t.getActive() == 0}">
                   <b>Не активный</b>
                </c:if>
            </td>
            <td class="checkbox-shift">
                <a href="#" onclick="javascript:toggleedit('.nya<c:out value='${t.getTemplateId()}'/>');"><img src="resources/images/icons/glyphicons_137_cogwheels.png"  title="внести изменения"/></a>                        
            <%--
<input type="image"  src="resources/images/edit.png" width="25" height="25" title="внести изменения" onclick="location.href='showTemplates.html'"/>
            --%>

                <a href="showColumn.html?templateId=<c:out value='${t.getTemplateId()}'/>"><img  src="resources/images/icons/glyphicons_119_table.png"  title="редактировать колонки"/></a>
               <a href="showAppForm.html?templateId=<c:out value='${t.getTemplateId()}'/>"><img  src="resources/images/icons/glyphicons_051_eye_open.png"  title="просмотреть шаблон"/></a>
            
            </td>
            </form>
            </tr>

            <% i++;%>
        </c:forEach>
        </tbody>
        </table>
  <%-- </form>--%>
		</div>
	</div>
        </div>  
                </div>
    </body>
</html>

