<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<option value="0" disabled selected>Выберите</option>
<c:forEach items="${univers}" var="u">
	<option value="${u.universityId}">${u.universityName}</option>
</c:forEach>