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
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap-fileupload.min.css"/>" />
<script src="<c:url value="/resources/js/bootstrap-fileupload.min.js" />"></script>

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
					<form:form method="POST" commandName="regForm" enctype="multipart/form-data">
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

						
					<div class="row-fluid">
						<c:forEach items="${columns}" var="c">

							<c:if test="${c.getLevel()==1}">
								<div class="row-fluid">
									<div class="span8">
										<h4>${c.getName()}</h4>
									</div>
								</div>
							</c:if>

							<c:if test="${c.getLevel()==2}">
								<div class="row-fluid">
									<div class="span8">
										<h6>${c.getName()}</h6>
									</div>
								</div>
							</c:if>

							<c:if test="${c.getLevel()==2 && c.getTypeName()=='areastring'}">
								<div class="span8">
									<textarea name="map[${c.getColumnId()}]" required></textarea>
								</div>
							</c:if>


							<c:if test="${c.getTypeName()=='areastring' && c.getLevel()==3}">
								<div class="span8">${c.getName()}</div>
								<div class="span8">
									<textarea name="map[${c.getColumnId()}]"></textarea>
								</div>
							</c:if>


							<c:if test="${c.getTypeName()=='textstring'}">
							${c.getName()}<input name="map[${c.getColumnId()}]" type="text"
									required />
							</c:if>

							<c:if test="${c.getTypeName()=='selectenum'}">
								<div class="span8">
									<span class="span4">${c.getName()}</span> <span class="span2">
										<form:select class="input-mini" path="map[${c.getColumnId()}]"
											required="1">
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
									<div class="span3">
										<form:radiobutton path="map[${c.getColumnId()}]" value="${t}"
											required="1" />
										${t}
									</div>
								</c:forEach>
							</c:if>

							<c:if test="${c.getTypeName()=='integer'}">
								<div class="span8">
									<div class="span4">${c.getName()}</div>
									<div class="span2">
										<input class="input-mini" name="map[${c.getColumnId()}]"
											type="number" size="30" min="${c.getcT().getMin()}"
											max="${c.getcT().getMax()}" value="0" required />
									</div>
								</div>
							</c:if>
						</c:forEach>
						</div>
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