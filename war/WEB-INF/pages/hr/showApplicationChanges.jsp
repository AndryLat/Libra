<%-- 
    Author     : Alexander Lebed
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<link rel="stylesheet" type="text/css" href="../resources/css/table.css" />
	<title>Подтверждение</title>
	<script>
            function closeMessage()
            {
                document.getElementById("alertMessage").innerHTML="";
            }
        </script>
        <script language="JavaScript">
            function toggle(source) {
                checkboxes = document.getElementsByName('checker');
                for(var i in checkboxes)
                checkboxes[i].checked = source.checked;
            }
        </script>
        <style>
            table { table-layout: fixed; }
            table th, table td { overflow: hidden; }
        </style>
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
                            <br>
                            
                            <table class="bordered" style="margin-left: auto; margin-right: auto;">
                                <caption><div class="alert alert-info">Подтверждение изменений анкеты студентом</div></caption>
                                <tr>
                                    <th><a href="sortOldNewValues.html?orderBy=APP_ID">№</a> &nbsp; ${idOrder}</th>
                                    <th><a href="sortOldNewValues.html?orderBy=FIRST_NAME">имя</a> <a href="sortOldNewValues.html?orderBy=LAST_NAME">фамилия</a> &nbsp; ${nameOrder}</th>
                                    <th><a href="sortOldNewValues.html?orderBy=EMAIL">email</a> &nbsp; ${emailOrder}</th>
                                    <th><a href="sortOldNewValues.html?orderBy=FIELD_NAME">полe</a> &nbsp; ${fieldNameOrder}</th>
                                    <th>до</th>
                                    <th>после</th>
                                    <th></th>
                                    <th></th>
                                    <th>
                                        <form action="deleteOrConfirmAllChanges.html" method="POST">
                                            <input type="checkbox" onClick="toggle(this)" /> All
                                            <input type="submit" name="action" value="Y" title="Подтвердить"><input type="submit" name="action" value="N" title="Удалить">
                                        </form>
                                    </th>
                                </tr>
                                <tr>
                                    <c:forEach items="${list}" var="o">
                                        <tr>
                                        <td style="width: 3%">${o.getAppId()}</td>
                                        <td style="width: 10%">${o.getFirstName()} ${o.getLastName()}</td>
                                        <td style="width: 15%">${o.getEmail()}</td>
                                        <td style="width: 20%">${o.getFieldName()}</td>
                                        <td style="width: 20%">${o.getOldValue()}</td>
                                        <td style="width: 20%">${o.getNewValue()}</td>
                                        <c:choose>
                                            
                                            <c:when test="${o.getColumnName().equals('interview')}">
                                                <td style="width: 5%"><a href="doneConfirmInterviewTime.html?oldId=<c:out value='${o.getOldId()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>">
                                                    <img  src="../resources/images/admin/glyphicons_198_ok.png" width="20" height="20" title="Подтвердить"/></a></td>
                                                <td style="width: 5%"><a href="cancelConfirmInterviewTime.html?newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>">
                                                    <img  src="../resources/images/admin/glyphicons_197_remove.png" width="20" height="20" title="Отменить"/></a></td>
                                                <td style="width: 2%"><input type="checkbox" name="checker" value="val1"></td>
                                            </c:when>
                                                
                                            <c:when test="${o.getColumnName().equals('dynamic')}">
                                                <td style="width: 5%"><a href="doneConfirmDynamicField.html?oldId=<c:out value='${o.getOldId()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>">
                                                    <img  src="../resources/images/admin/glyphicons_198_ok.png" width="20" height="20" title="Подтвердить"/></a></td>
                                                <td style="width: 5%"><a href="cancelConfirmDynamicField.html?newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>">
                                                    <img  src="../resources/images/admin/glyphicons_197_remove.png" width="20" height="20" title="Отменить"/></a></td>
                                                <td style="width: 2%"><input type="checkbox" name="checker" value="val2"></td>
                                            </c:when>
                                                
                                            <c:otherwise>
                                                <td style="width: 5%"><a href="doneConfirmMainAppInfo.html?oldId=<c:out value='${o.getOldId()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>&columnName=<c:out value='${o.getColumnName()}'/>">
                                                    <img  src="../resources/images/admin/glyphicons_198_ok.png" width="20" height="20" title="Подтвердить"/></a></td>
                                                <td style="width: 5%"><a href="cancelConfirmMainAppInfo.html?objectId=<c:out value='${o.getObjectId()}'/>">
                                                     <img  src="../resources/images/admin/glyphicons_197_remove.png" width="20" height="20" title="Отменить"/></a></td>
                                                <td style="width: 2%"><input type="checkbox" name="checker" value="val3"></td>
                                            </c:otherwise>
                                                
                                        </c:choose>
                                        </tr>
                                    </c:forEach>
                                
                            </table>
        
			</div>
		</div>
	</div>
</body>
</html>