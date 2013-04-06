<%-- 
    Author     : Sashenka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${title}</title>
          <link href="resources/css/template.css" rel="stylesheet">	
          <link rel="stylesheet" type="text/css" href="resources/css/table.css" />
        <jsp:include page="resources.jsp" />
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
<div class="span8">
           
				<h4>${h1}</h4>
        <c:if test="${infoSize == 0  }">
            <p>Смело удаляйте! </p>
        </c:if>
        <c:if test="${infoSize > 0  }">
            <p>При это будут изменены анкеты ${infoSize} студентов</p>
            <table class="bordered width100">
                <thead>
                <tr>
                    <th>номер анкеты</th>
                    <th>ФИО</th>
                    <th>фото</th>
                </tr>
                </thead>
            <c:forEach items="${info}" var="i">
            <tr>
                <td class="align-center"> <c:out value="${i.getAppId()}" /></td>
                <td> 
                    <c:out value="${i.getFirstname()} " />
                    <c:out value="${i.getPatronymic()} " />
                    <c:out value="${i.getLastname()}" />
                </td>
                <td> <c:out value="Пока номер ${i.getUserId()}" /></td>
            </tr>
           </c:forEach>
            <tr>
                <td colspan="3">
                    
                    <form action="<c:out value='${submit}'/>.html" method="POST">
                         <input type="hidden" name="templateId" value="<c:out value='${templateId}'/>"/>
                        <c:forEach items="${delete}" var="d">
                            <input type="hidden" name="delete[]" value="<c:out value='${d}'/>"/>
                        </c:forEach>
                    <input class="btn btn-info btn-block" type="submit" value="удалить"/>    
                    <input class="btn btn-info btn-block" value="НЕТ" onclick="location.href='${location}'" type="button"/>
                    </form>
                </td>
                
            </tr>
            </table>
        </c:if>
         <c:if test="${infoSize == 0  }">
             <form action="<c:out value='${submit}'/>.html" method="POST">
                 <input type="hidden" name="templateId" value="<c:out value='${templateId}'/>"/>
                        <c:forEach items="${delete}" var="d">
                            <input type="hidden" name="delete[]" value="<c:out value='${d}'/>"/>
                        </c:forEach>
                    <input class="btn btn-info btn-block" type="submit" value="удалить"/>    
                    <input class="btn btn-info btn-block" value="НЕТ" onclick="location.href='${location}'" type="button"/>
                    </form>
         </c:if>   
                
            </div>
		</div>
	</div>
      </div>  
    </body>
</html>
