<%-- 
    Author     : Alexander Lebed
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
<!--<![endif]-->
<head>
	<jsp:include page="../resources.jsp" />
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
			<div class="span9">
                            
                            <c:if test="${message != null}">
                                <span id="alertMessage">
                                    <div class="alert alert-error" align="center">
                                        <button type="button" class="close" onclick="closeMessage()" data-dismiss="alert">&times;</button>
                                        ${message}
                                        <p align="center">${link}</p>
                                    </div>
                                </span>
                            </c:if>
                                    
			</div>
		</div>
	</div>
</body>
</html>