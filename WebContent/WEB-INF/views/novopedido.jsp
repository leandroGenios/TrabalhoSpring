<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib  uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<jsp:include page="head.jsp"></jsp:include>
<body>
	<jsp:include page="menu.jsp"></jsp:include>
	<div class="container">
		<h1>Novo Pedido</h1>
		<div class="w-100">
			<form action="novopedido" method="post" id="formProduto">
				<div class="w-25">
					<input name="cpf" type="text" class="form-control cpfMask" placeholder="Digite o CPF" value="${cpf}" required>
				</div>
				<div class="w-25">
					<select name="produto" class="form-control" required>
						<c:forEach items="${produtos.objeto}" var="item">
							<option value="${item.id}">${item.descricao}</option>
						</c:forEach>
					</select>
				</div>
				<div class="w-25">
					<input name="quantidade" type="number" class="form-control" placeholder="Digite a quantidade" required>
				</div>
				<div class="w-25">
					<button class="btn btn-primary" type="submit" form="formProduto" style="float: right">Incluir</button>
				</div>
			</form>
		</div>
		<div class="w-100">
			<table class="table table-hover">
				<thead class="thead-dark">
					<tr>
						<th scope="col">Produto</th>
						<th scope="col">Quantidade</th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${itens}" var="item">
						<tr>
							<th scope="row">${item.produto.descricao}</th>
							<th scope="row">${item.quantidade}</th>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="w-100">
			<form action="novopedido/salvar" method="post" id="formSalvar">
				<input name="cpf" type="hidden" value="${cpf}"/>
			</form>
			<button class="btn btn-primary" type="submit" form="formSalvar" style="float: right;">Salvar</button>
		</div>
	</div>
	<jsp:include page="js-imports.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function() {
			$('.nav-item').removeClass('active');
			$('#novopedido').addClass('active');
		
			$(".cpfMask").mask("999.999.999-99");
		});
	</script>
</body>
</html>