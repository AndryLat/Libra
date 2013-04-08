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
                checkboxes = document.getElementsByName('checker[]');
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
                                        <form action="checkAllMessage.html" method="POST">
                                            <input type="checkbox" onClick="toggle(this)" /> All
                                            <input type="submit" name="action" value="Y" title="Подтвердить">
                                            <input type="submit" name="action" value="N" title="Отклонить">
                                        
                                    </th>
                                </tr>
                                    <c:forEach items="${list}" var="o">
                                        <tr>
                                        <td style="width: 3%">${o.getAppId()}</td>
                                        <td style="width: 10%">${o.getFirstName()} ${o.getLastName()}</td>
                                        <td>${o.getEmail()}</td>
                                        <td style="width: 15%">${o.getFieldName()}</td>
                                        <td style="width: 20%">${o.getOldValue()}</td>
                                        <td style="width: 20%">${o.getNewValue()}</td>
                                        
                                        
                                        <td style="width: 10%"><a href="message.html?objectId=<c:out value='${o.getObjectId()}'/>&action=confirm">
                                                    <img  src="../resources/images/admin/glyphicons_206_ok_2.png" width="20" height="20" title="Подтвердить"/></a></td>
                                        
                                        <td style="width: 10%"><a href="message.html?objectId=<c:out value='${o.getObjectId()}'/>&action=cancel">
                                                    <img  src="../resources/images/admin/glyphicons_207_remove_2.png" width="15" height="15" title="Отклонить"/></a></td>
                                           
                                        <td><input type="checkbox" name="checker[]" value="<c:out value='${o.getObjectId()}'/>"/></td>
                                        
                                        </tr>
                                    </c:forEach>
                                </form>
                            </table>
        
			</div>
		</div>
	</div>
</body>
</html>