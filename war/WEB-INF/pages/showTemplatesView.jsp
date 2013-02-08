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
    </head>
    <body>
        <form action="SubmitTemplate.html" method="POST">
       <!-- <table border="1" cellspacing="0" cellpadding="4">   -->
       <table class="table">
        <caption>Добавить новый шаблон</caption>
        <thead>
        <tr>
                    <td COLSPAN=4>
                    <input type="text" name="name">
                    </td>
                    <td >
                <input type="submit" value="OK">
                </td>  
        </tr>
        </thead>
        </table>
        </form>
        <form action="ActiveTemplate.html" method="POST">
        <!--<table border="1" cellspacing="0" cellpadding="4">-->
        <table class="table">
        <caption>Выбрать щаблон главным</caption>
         <tbody>
        <tr>
             <td COLSPAN=4>
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
             </td>
             <td >
                <input type="submit" value="OK">
             </td>  
        </tr>
        </tbody>
        </table>
            </form>
        <!--<table border="1" cellspacing="0" cellpadding="4">-->
        <table class="table table-bordered">
        <caption>Информация о существующих шаблонах</caption>
        <thead>
        <tr>
            <th>Удалить</th>
            <th>№</th>
            <th>Название</th>
            <th>Активный шаблон</th>
            <th>Изменить</th>
            <th>Новое имя</th>
            <th>Посмотреть шаблон целиком</th>
        </tr>
        </thead>
              <tbody>
       <c:forEach items="${templates}" var="t">
            <form action="showTemplates.html" method="POST">
        <tr>
            <td>
                <c:if test="${t.getActive() != 1}">
                <a href="delTemplate.html?template=<c:out value='${t.getTemplateId()} '/>"><img src="resources\images\del.jpg"  width="25" height="25" border="0" title="удалить"/></a>
                </c:if>
                <c:if test="${t.getActive() == 1}">
                <a href=""><img src="resources\images\del2.png"  width="25" height="25" border="0" title="нельзя удалить активный шаблон"/></a>
                </c:if>
            </td>
            <td>
                ${t.getTemplateId()} 
                <input type="hidden" name="selTemplate" value="<c:out value='${t.getTemplateId()}  '/>"/>
            </td>
            <td>${t.getName()} </td>
            <td>
            <c:if test="${t.getActive() == 1}">
                    <b>Активный</b>
                </c:if>
            <c:if test="${t.getActive() == 0}">
                   <b>Не активный</b>
                </c:if>
            </td>
            <td>
                <input type="image"  src="resources\images\edit.png" width="25" height="25" title="внести изменения" OnClick="Forma1.submit()"/>
            </td>
            <td>
                <input type="text" name="name"/>
            </td>
            <td>
                <input type="image"  src="resources\images\show.jpg" width="25" height="25" title="просмотреть"/>
            </td>
        </tr>
            </form>
        </c:forEach>
        </tbody>
        </table>
    </body>
</html>
