<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Регистрационные данные</title>
</head>
<body>
${regForm.name}<br>
${regForm.lastName}<br>
${regForm.patronymic}<br><br>

${regForm.email}<br>
${regForm.phoneNumber}<br><br>

${regForm.userId}<br>
${regForm.appId}<br>
${regForm.templateId}<br><br>

${regForm.department}<br>
${regForm.course}<br>
${regForm.graduated}<br>


</body>
</html>