<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib  uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="head.jsp"></jsp:include>
<body>
	<jsp:include page="menu.jsp"></jsp:include>
	<div class="container">
		<h1>Pedidos</h1>
		<div class="w-100">
			<form action="pedidos" method="get" id="formCliente">
				<div class="w-25">
					<input name="cpf" type="text" class="form-control cpfMask" placeholder="Digite o CPF" value="${cpf}" required>
				</div>
				<div class="w-25">
					<button class="btn btn-primary" type="submit" form="formCliente">Pesquisar</button>
				</div>
			</form>
		</div>
		<div class="w-100">
			<table class="table table-hover">
				<thead class="thead-dark">
					<tr>
						<th scope="col">ID</th>
						<th scope="col">Data</th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${pedidos}" var="item">
						<tr>
							<th scope="row">${item.id}</th>
							<th scope="row"><fmt:formatDate pattern = "dd/MM/yyyy" value = "${item.data}" /></th>
							<th scope="row">
								<form action="pedidos" method="POST" id="form${item.id}">
									<input type="hidden" value="${cpf}" name="cpf">
									<input type="hidden" value="${item.id}" name="item">
								</form>
								<button class="btn btn-primary" type="submit" form="form${item.id}">Visualizar</button>
							</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="w-100">
			<table class="table table-hover">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Produto</th>
						<th scope="col">Quantidade</th>
						<th scope="col"></th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${itens}" var="item">
						<tr>
							<th scope="row">${item.produto}</th>
							<th scope="row">${item.quantidade}</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<jsp:include page="js-imports.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.nav-item').removeClass('active');
			$('#pedidos').addClass('active');
		
			$(".cpfMask").mask("999.999.999-99");
		});
	</script>
</body>
</html>