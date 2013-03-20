<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
<!--<![endif]-->
<head>
<jsp:include page="../resources.jsp" />

<title>Анкета кандидата</title>
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
				<div id="legend">
					<legend>Анкета кандидата</legend>
				</div>
				<div class="row-fluid">
				<form:form action="submit.html" method="POST" commandName="regForm">
					
					<c:forEach items="${columns}" var="c">
					
						<c:if test="${c.getLevel()==1}">
							<div class="row-fluid"><div class="span8"><h3>${c.getName()}</h3></div></div>
						</c:if>
						
						<c:if test="${c.getLevel()==2}">
							<div class="row-fluid"><div class="span8"><h5>${c.getName()}</h5></div></div>
						</c:if>
						
						<c:if test="${c.getLevel()==2 && c.getTypeName()=='areastring'}">
								<div class="span8"><textarea name="map[${c.getColumnId()}]" required></textarea></div>
						</c:if>


						<c:if test="${c.getTypeName()=='areastring' && c.getLevel()==3}">
								<div class="span8">${c.getName()}</div>
									<div class="span8"><textarea name="map[${c.getColumnId()}]"></textarea></div>
						</c:if> 

						
						<c:if test="${c.getTypeName()=='textstring'}">
							${c.getName()}<input name="map[${c.getColumnId()}]" type="text" required/>
						</c:if>
						
						<c:if test="${c.getTypeName()=='selectenum'}">
						<div class="span8"><span class="span4">${c.getName()}</span>
							<span class="span2">
							<form:select class="input-mini" path="map[${c.getColumnId()}]" required="1">
								<c:forEach items="${c.getcT().getEmums()}" var="t">
										<form:option value="${t}">${t}</form:option>
								</c:forEach>
							</form:select>
							</span>
						</div>
						</c:if>
						
						<c:if test="${c.getTypeName()=='checkboxenum'}">
                             ${c.getName()}
								<c:forEach items="${c.getcT().getEmums()}" var="t">
									<form:checkbox path="map[${c.getColumnId()}]" value="${t}" />${t}
								</c:forEach>
						</c:if>
						
						<c:if test="${c.getTypeName()=='radioenum'}">
                             ${c.getName()}
								<c:forEach items="${c.getcT().getEmums()}" var="t">
									<div class="span3"><form:radiobutton path="map[${c.getColumnId()}]" value="${t}" required="1" />${t}</div>
								</c:forEach>
						</c:if>

						<c:if test="${c.getTypeName()=='integer'}">
                                 <div class="span8"><div class="span4">${c.getName()}</div>
                                 <div class="span2"><input class="input-mini" 
                                 	name="map[${c.getColumnId()}]" 
                                 	type="number" 
                                 	size="30" min="${c.getcT().getMin()}" 
                                 	max="${c.getcT().getMax()}" 
                                 	value="0" required />
                                 	</div>
                                 </div>
						</c:if>
					</c:forEach>
									<div class="control-group">
						<div class="controls" align="center">
							<button type="submit" class="btn btn-large btn-success span8">Подтвердить</button>
						</div>
					</div>
				</form:form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>