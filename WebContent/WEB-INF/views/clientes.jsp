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
		<h1>Clientes</h1>
		<div class="w-25">
			<button class="btn btn-primary" data-toggle="modal" data-target="#cadastroModal">Novo Cliente</button>
		</div>
		<div class="w-100">
			<table class="table table-hover">
				<thead class="thead-dark">
					<tr>
						<th scope="col">ID</th>
						<th scope="col">CPF</th>
						<th scope="col">Nome</th>
						<th scope="col">Sobrenome</th>
						<th scope="col">Editar</th>
						<th scope="col">Excluir</th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${clientes}" var="item">
						<tr>
							<th scope="row">${item.id}</th>
							<td class="maskCpf">${item.cpf}</td>
							<td>${item.nome}</td>
							<td>${item.sobreNome}</td>
							<td><button class="btn btn-default edit" data-toggle="modal" data-target="#edicaoModal"><i class="fas fa-edit"></i></button></td>
							<td><button class="btn btn-default delete" data-toggle="modal" data-target="#exclusaoModal"><i class="fas fa-trash-alt"></i></button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<div class="modal fade" id="cadastroModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
		   		<form:form action="clientes/setCliente" method="post" modelAttribute="cliente" id="form1">
					<div class="modal-header">
						<h4 class="modal-title">Novo Cliente</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="form-group">
		                    <form:input path="cpf" class="form-control" id="cpf" placeholder="Didite o CPF" required="true"/>
						</div>
						<div class="form-group">
							<form:input path="nome" class="form-control" id="nome" placeholder="Didite o nome" required="true" maxlength="30"/>
						</div>
						<div class="form-group">
							<form:input path="sobreNome" class="form-control" id="sobreNome" placeholder="Didite o sobrenome" required="true"  maxlength="50"/>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" form="form1">Salvar</button>
				     	<button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
					</div>     
				</form:form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="edicaoModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
		   		<form:form action="clientes/editaCliente" method="post" modelAttribute="cliente" id="formEdit">
                    <form:hidden path="id" id="id"/>
					<div class="modal-header">
						<h4 class="modal-title">Editar Cliente</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						<div class="form-group">
		                    <form:input path="cpf" class="form-control" id="cpfEdit" placeholder="Didite o CPF" required="true"/>
						</div>
						<div class="form-group">
							<form:input path="nome" class="form-control" id="nomeEdit" placeholder="Didite o nome" required="true"  maxlength="30"/>
						</div>
						<div class="form-group">
							<form:input path="sobreNome" class="form-control" id="sobreNomeEdit" placeholder="Didite o sobrenome" required="true"  maxlength="50"/>
						</div>
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" form="formEdit">Salvar</button>
				     	<button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
					</div>     
				</form:form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="exclusaoModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
		   		<form:form action="clientes/excluirCliente" method="post" modelAttribute="cliente" id="formDelete">
                    <form:hidden path="id" id="idDelete"/>
                    <form:hidden path="cpf" id="cpfDelete"/>
                    <form:hidden path="nome" id="nomeDelete"/>
                    <form:hidden path="sobreNome" id="sobreNomeDelete"/>
					<div class="modal-header">
						<h4 class="modal-title">Excluir Cliente</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<!-- Modal body -->
					<div class="modal-body">
						Deseja realmente excluir o cliente?
					</div>
					<!-- Modal footer -->
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" form="formDelete">Excluir</button>
				     	<button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
					</div>     
				</form:form>
			</div>
		</div>
	</div>
	<jsp:include page="js-imports.jsp"></jsp:include>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("input").prop('required',true);
			$('.nav-item').removeClass('active');
			$('#clientes').addClass('active');
		
		
		
			$(".maskCpf").mask("999.999.999-99");
			$("#cpf").mask("999.999.999-99");
			$("#cpfEdit").mask("999.999.999-99");
		
			$('.btn.edit').on('click', function(){
				let el = this.parentElement.closest('tr');
				console.log(el.getElementsByTagName('td'));
				$('#edicaoModal #id').val(el.getElementsByTagName('th')[0].textContent);
				$('#edicaoModal #cpfEdit').val(el.getElementsByTagName('td')[0].textContent);
				$('#edicaoModal #nomeEdit').val(el.getElementsByTagName('td')[1].textContent);
				$('#edicaoModal #sobreNomeEdit').val(el.getElementsByTagName('td')[2].textContent);
			});

			$('.btn.delete').on('click', function(){
				let el = this.parentElement.closest('tr');
				console.log(el.getElementsByTagName('td'));
				$('#exclusaoModal #idDelete').val(el.getElementsByTagName('th')[0].textContent);
				$('#exclusaoModal #cpfDelete').val(el.getElementsByTagName('td')[0].textContent);
				$('#exclusaoModal #nomeDelete').val(el.getElementsByTagName('td')[1].textContent);
				$('#exclusaoModal #sobreNomeDelete').val(el.getElementsByTagName('td')[2].textContent);
			});
		});
	</script>
</body>
</html>