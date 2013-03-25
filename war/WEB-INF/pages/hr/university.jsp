<%-- 
    Document   : university
    Created on : 24.03.2013, 17:00:14
    Author     : Yuliya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <option value="0"> Все </option>
        <c:forEach items="${univers}" var="u">
            <option value="${u.universityId}"> ${u.universityName} </option>
        </c:forEach>
