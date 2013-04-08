<%-- 
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
        <jsp:include page="resources.jsp" />
         <link href="resources/css/template.css" rel="stylesheet">	
    </head>
    <body>
        
        <div class="mincontainer">
        <div class="navmenu">
		<jsp:include page="navbar.jsp" />
	</div>
	
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="sidebar">
				<jsp:include page="sidebar.jsp" />
			</div>
                        
                        <div class="span8 well align-center">
			<h2 class="text-info">${message}</h2> 
                        ${link} 
                        </div>
                        
		</div>
	</div>
                        </div>
    </body>
</html>
