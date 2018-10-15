package br.com.trabalho.facade;

import br.com.trabalho.dao.ProdutoDAO;
import br.com.trabalho.model.Produto;
import br.com.trabalho.util.ErroMessenger;
import br.com.trabalho.util.Retorno;

public class ProdutoFacade {
	private ProdutoDAO dao = new ProdutoDAO();

	public Retorno listProdutos() {
		Retorno retorno = new Retorno();
		Object r = dao.listProdutos();
		if(r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao buscar os registros.", (RuntimeException)r));
		}else {
			retorno.setObjeto(r);
		}
		return retorno;
	}

	public Retorno setProduto(Produto produto) {
		Retorno retorno = new Retorno();
		if(validaProduto(produto, "")) {
			Object r = dao.setProduto(produto);
			if(r.getClass() == RuntimeException.class) {
				retorno.setErro(new ErroMessenger("Ocorreu um erro ao inserir o registro.", (RuntimeException)r));
			}else {
				retorno.setObjeto(r);
			}			
		}else {
			retorno.setErro(new ErroMessenger("A descrição deve estar preenchido e não pode existir.", null));
			
		}
		return retorno;
	}
	
	public Retorno editProduto(Produto produto) {
		Retorno retorno = new Retorno();
		if(validaProduto(produto, "")) {
			Object r = dao.updateProduto(produto);
			if(r.getClass() == RuntimeException.class) {
				retorno.setErro(new ErroMessenger("Ocorreu um erro ao editar o registro.", (RuntimeException)r));
			}else {
				retorno.setObjeto(r);
			}
		}else {
			retorno.setErro(new ErroMessenger("A descrição deve estar preenchido e não pode existir.", null));
		}
		return retorno;
	}
	
	public Retorno excluirProduto(int codigo) {
		Retorno retorno = new Retorno();
		
		Object r = dao.deleteProduto(codigo);
		if(r != null && r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao excluir o registro.", (RuntimeException)r));
		}else {
			retorno.setObjeto("true");
		}
		return retorno;
	}

	public Retorno getProduto(int codigo) {
		Retorno retorno = new Retorno();
		
		Object r = dao.getProduto(codigo);
		if(r != null && r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao buscar produto.", (RuntimeException)r));
		}else {
			retorno.setObjeto(r);
		}
		return retorno;
	}
	
	public boolean validaProduto(Produto produto, String metodo) {
		boolean status = true;
		if(produto.getDescricao().equals("")) {
			status = false;
		}
		Produto p = dao.getProduto(produto.getDescricao());
		if(p != null) {
			status = false;
		}
		
		if(metodo.equals("editar")) {
		}
		return status;
	}
}
