<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="head.jsp"></jsp:include>
<body>
	<input type="hidden" value="${page}" id="page">
	<center>
		<img alt="" width="200px" src="<c:url value="/resources/img/checked.svg"/>">
	</center>
	<center>
		success
	</center>
	<center id="time">Retornando em... </center>
	<jsp:include page="js-imports.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function() {
			setTimeout(function(){ 
				$('#time').text('Retornando em... '+3);
				setTimeout(function(){ 
					$('#time').text('Retornando em... '+2);
					setTimeout(function(){ 
						$('#time').text('Retornando em... '+1);
						setTimeout(function(){ 
							window.location.href="/TrabalhoSpring/" + $('#page').val();
						}, 1000);
					}, 1000);
				}, 1000);
			}, 1000);
		
		});
	</script>
</body>
</html>