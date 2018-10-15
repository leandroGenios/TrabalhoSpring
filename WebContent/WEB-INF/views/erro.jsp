<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="head.jsp"></jsp:include>
<body>
	<center>
		<img alt="" width="200px" src="<c:url value="/resources/img/cancel.svg"/>">
	</center>
	<center>
		${erro.mensagem}
	</center>
	<center>
		<br><br>
		<div class="notification">
			${erro.erro}
		</div>
	</center>
	<jsp:include page="js-imports.jsp"></jsp:include>
</body>
</html>