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
	<title>${title}</title>
        <script>
            function closeMessage()
            {
                document.getElementById("alertMessage").innerHTML="";
            }
        </script>
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
			<div class="span9" style="text-align:center">
                            
                            <table width="100%">
                                <tr>
                                    <c:if test="${message != null}">
                                        <td align="center" id="alertMessage">
                                        <div class="alert" align="center">
                                        <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                                        ${message}
                                        </div>
                                        </td>
                                    </c:if>
                                </tr>
                            </table>
                            <br>
                            
                            <table class="bordered" style="margin-left: auto; margin-right: auto;">
                                <tr>
                                    <th>№</th>
                                    <th>Имя</th>
                                    <th>Email</th>
                                    <th>Поле</th>
                                    <th>До</th>
                                    <th>После</th>
                                </tr>
                                <tr>
                                    <td>${o.getAppId()}</td>
                                    <td>${o.getFirstName()} ${o.getLastName()}</td>
                                    <td>${o.getEmail()}</td>
                                    <td>${o.getFieldName()}</td>
                                    <td>${o.getOldValue()}</td>
                                    <td>${o.getNewValue()}</td>
                                </tr>
                            </table>
                            <br>
                            
                            <!-- Confirm button -->
                            <c:if test="${action.equals('confirm')}">
                            <c:choose>
                            <c:when test="${o.getColumnName().equals('dynamic')}">
                                <a class="btn btn-primary" href="doneConfirmDynamicField.html?columnName=<c:out value='${o.getColumnName()}'/>&oldId=<c:out value='${o.getOldId()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15" title="Подтвердить"/> Да</a>
                            </c:when>
                            
                            <c:when test="${o.getColumnName().equals('interview')}">
                                <a class="btn btn-primary" href="doneConfirmInterviewTime.html?oldId=<c:out value='${o.getOldId()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15" title="Подтвердить"/> Да</a>
                            </c:when>
                                
                            <c:otherwise>
                                <a class="btn btn-primary" href="doneConfirmMainAppInfo.html?oldId=<c:out value='${o.getOldId()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>&columnName=<c:out value='${o.getColumnName()}'/>"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15" title="Подтвердить"/> Да</a>
                            </c:otherwise>
                            </c:choose>
                            </c:if>
                            
                            <!-- Cancel button -->
                            <c:if test="${action.equals('cancel')}">
                            <c:choose>
                            <c:when test="${o.getColumnName().equals('dynamic')}">
                                <a class="btn btn-primary" href="cancelConfirmDynamicField.html?columnName=<c:out value='${o.getColumnName()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15" title="Подтвердить"/> Да</a>
                            </c:when>
                            
                            <c:when test="${o.getColumnName().equals('interview')}">
                                <a class="btn btn-primary" href="cancelConfirmInterviewTime.html?columnName=<c:out value='${o.getColumnName()}'/>&newId=<c:out value='${o.getNewId()}'/>&objectId=<c:out value='${o.getObjectId()}'/>"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15" title="Подтвердить"/> Да</a>
                            </c:when>
                                
                            <c:otherwise>
                                <a class="btn btn-primary" href="cancelConfirmMainAppInfo.html?columnName=<c:out value='${o.getColumnName()}'/>&objectId=<c:out value='${o.getObjectId()}'/>"><img  src="../resources/images/admin/symbol_check.gif" width="15" height="15" title="Подтвердить"/> Да</a>
                            </c:otherwise>
                            </c:choose>
                            </c:if>
                                
                            <a class="btn" href="currentConfirmChanges.html"> &nbsp; Нет &nbsp;</a>
                            
                            
                            
                                
			</div>
		</div>
	</div>
</body>
</html>