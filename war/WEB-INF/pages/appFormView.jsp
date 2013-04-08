<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% pageContext.setAttribute("curLevel", 1); %>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!-->

<html class="no-js">
<!--<![endif]-->
<head>
<jsp:include page="../pages/resources.jsp" />
<link rel="stylesheet"
	href="<c:url value="/resources/css/template.css"/>" />
	<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-fileupload.min.css"/>" />
<script src="<c:url value="/resources/js/bootstrap-fileupload.min.js" />"></script>
<title>Анкета кандидата</title>
</head>

<body>
	<div class="mincontainer">
		<div class="navmenu">
			<jsp:include page="../pages/navbar.jsp" />
		</div>

		<div class="container-fluid">
			<div class="row-fluid">

				<div class="sidebar">
					<jsp:include page="../pages/sidebar.jsp" />
				</div>
				<div class="span8 shift55">
					<h3 class="align-center">
						<strong class="title-color">Анкета кандидата</strong>
					</h3>
					<hr>
					<form:form method="POST" commandName="regForm"
						enctype="multipart/form-data">
						<div class="row-fluid">
							<div class="span3">
								<div class="fileupload fileupload-new"
									data-provides="fileupload">
									<div class="fileupload-new thumbnail"
										style="width: 120px; height: 160px;">
										<img
											src="http://www.placehold.it/120x160/EFEFEF/AAAAAA&text=Photo" />
									</div>
									<div class="fileupload-preview fileupload-exists thumbnail"
										style="max-width: 120px; max-height: 160px; line-height: 10px;"></div>
									<div>
										<span class="btn btn-file"><span class="fileupload-new">Обзор..</span><span
											class="fileupload-exists">Изменить</span><input name="photo"
											type="file" /></span> <a href="#" class="btn fileupload-exists"
											data-dismiss="fileupload">Удалить</a>
									</div>
								</div>
							</div>


						</div>
						<c:forEach items="${columns}" var="c">

							<c:if test="${c.getLevel()==1}">
								<div class="align-center">
									<h4>
										<strong class="title-color">${c.getName()}</strong>
									</h4>
								</div>
							</c:if>

							<c:if test="${c.getLevel()!=1}">
								<c:forEach var="i" begin="${c.getLevel()}" end="${curLevel-1}">
									</ul>
								</c:forEach>
								<c:forEach var="i" begin="${curLevel+1}" end="${c.getLevel()}">
									<ul>
								</c:forEach>
								<c:set var="curLevel" value="${c.getLevel()}" />
								<li><p class="p-apForm">${c.getName()}</p> <c:if
										test="${c.getTypeName()=='areastring'}">
										<textarea class="width100" name="map[${c.getColumnId()}]"></textarea>

									</c:if> <c:if test="${c.getTypeName()=='textstring'}">
										<input class="width100" name="map[${c.getColumnId()}]"
											type="text" />
									</c:if> <c:if test="${c.getTypeName()=='selectenum'}">

										<form:select class="select-min-width"
											path="map[${c.getColumnId()}]">
											<c:forEach items="${c.getcT().getEmums()}" var="t">
												<form:option value="${t}">${t}</form:option>
											</c:forEach>
										</form:select>
									</c:if> <c:if test="${c.getTypeName()=='checkboxenum'}">
										<c:forEach items="${c.getcT().getEmums()}" var="t">
											<label class="checkbox"> <form:checkbox
													class="checkbox" path="map[${c.getColumnId()}]"
													value="${t}" />${t}

											</label>
										</c:forEach>
									</c:if> <c:if test="${c.getTypeName()=='radioenum'}">
										<c:forEach items="${c.getcT().getEmums()}" var="t">
											<label class="radio"> <form:radiobutton class="radio"
													path="map[${c.getColumnId()}]" value="${t}" />${t}
											</label>
										</c:forEach>
									</c:if> <c:if test="${c.getTypeName()=='integer'}">
										<input class="select-min-width" name="map[${c.getColumnId()}]"
											type="number" size="30" min="${c.getcT().getMin()}"
											max="${c.getcT().getMax()}" value="" />

									</c:if></li>
							</c:if>

						</c:forEach>

						<div class="controls" align="center">
							<button <c:if test="${acceslevel==1}">disabled=""</c:if>
								class="btn btn-large  btn-primary width100">Подтвердить</button>
						</div>
					</form:form>

				</div>

			</div>
		</div>
	</div>
</body>
</html>