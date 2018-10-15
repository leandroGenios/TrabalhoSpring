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
		<h1>Produtos</h1>
		<div class="w-25">
			<button class="btn btn-primary" data-toggle="modal" data-target="#cadastroModal">Novo Produto</button>
		</div>
		<div class="w-100">
			<table class="table table-hover">
				<thead class="thead-dark">
					<tr>
						<th scope="col">ID</th>
						<th scope="col">Descrição</th>
						<th scope="col">Editar</th>
						<th scope="col">Excluir</th>
					</tr>
				</thead>
				<tbody>
				 	<c:forEach items="${produtos}" var="item">
						<tr>
							<th scope="row">${item.id}</th>
							<td>${item.descricao}</td>
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
		   		<form:form action="produtos/setProduto" method="post" modelAttribute="produto" id="formCadastro">
					<div class="modal-header">
						<h4 class="modal-title">Novo Produto</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
		                    <form:input path="descricao" class="form-control" id="descricaoCad" placeholder="Didite a descrição do produto"  maxlength="45" required="true"/>
						</div>
					</div>
					<div class="modal-footer">
						<button type="submit" class="btn btn-primary" form="formCadastro">Salvar</button>
				     	<button type="button" class="btn btn-danger" data-dismiss="modal">Cancelar</button>
					</div>     
				</form:form>
			</div>
		</div>
	</div>
	<div class="modal fade" id="edicaoModal">
		<div class="modal-dialog">
	    	<div class="modal-content">
		   		<form:form action="produtos/editaProduto" method="post" modelAttribute="produto" id="formEdit">
                    <form:hidden path="id" id="idEdit"/>
					<div class="modal-header">
						<h4 class="modal-title">Edição de Produto</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						<div class="form-group">
		                    <form:input path="descricao" class="form-control" id="descricaoEdit" placeholder="Didite a descrição do produto" maxlength="45" required="true"/>
						</div>
					</div>
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
		   		<form:form action="produtos/excluirProduto" method="post" modelAttribute="produto" id="formDelete">
                    <form:hidden path="id" id="idDelete"/>
                    <form:hidden path="descricao" id="descricaoDelete"/>
					<div class="modal-header">
						<h4 class="modal-title">Exclusão de Produto</h4>
						<button type="button" class="close" data-dismiss="modal">&times;</button>
					</div>
					<div class="modal-body">
						Deseja realmente excluir o produto?
					</div>
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
			$('.nav-item').removeClass('active');
			$('#produtos').addClass('active');
		
			$('.btn.edit').on('click', function(){
				let el = this.parentElement.closest('tr');
				$('#edicaoModal #idEdit').val(el.getElementsByTagName('th')[0].textContent);
				$('#edicaoModal #descricaoEdit').val(el.getElementsByTagName('td')[0].textContent);
			});

			$('.btn.delete').on('click', function(){
				let el = this.parentElement.closest('tr');
				$('#exclusaoModal #idDelete').val(el.getElementsByTagName('th')[0].textContent);
				$('#exclusaoModal #descricaoDelete').val(el.getElementsByTagName('td')[0].textContent);
			});
		});
	</script>
</body>
</html>