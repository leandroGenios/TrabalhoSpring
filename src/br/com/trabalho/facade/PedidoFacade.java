package br.com.trabalho.facade;

import br.com.trabalho.dao.ClienteDAO;
import br.com.trabalho.dao.PedidoDAO;
import br.com.trabalho.model.Cliente;
import br.com.trabalho.model.Pedido;
import br.com.trabalho.util.ErroMessenger;
import br.com.trabalho.util.Retorno;

public class PedidoFacade {
	private ClienteDAO clientedao = new ClienteDAO();
	private PedidoDAO dao = new PedidoDAO();

	public Retorno listPedidos(String cpf) {
		Retorno retorno = new Retorno();
		Cliente cliente;
		Object r = clientedao.getCliente(cpf);
		if(r != null) {
			if(r.getClass() == RuntimeException.class) {
				retorno.setErro(new ErroMessenger("Ocorreu um erro ao buscar os registros.", (RuntimeException)r));
			}else {
				cliente = (Cliente)r;
				r = dao.listPedidos(cliente);
				if(r.getClass() == RuntimeException.class) {
					retorno.setErro(new ErroMessenger("Ocorreu um erro ao buscar os registros.", (RuntimeException)r));
				}else {
					retorno.setObjeto(r);
				}
			}			
		}else {
			retorno.setErro(new ErroMessenger("O CPF informado não existe.", null));			
		}
			
		return retorno;
	}
	
	public Retorno setPedido(Pedido pedido) {
		Retorno retorno = new Retorno();
		Object r = dao.setPedido(pedido);
		if(r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("O pedido não pode ser criado", null));	
		}else {
			pedido.setId((Integer)r);
			retorno.setObjeto(pedido);
		}
		return retorno;
	}
	
	public Retorno setItemPedido(Pedido pedido) {
		Retorno retorno = new Retorno();
		Object r = dao.setItensPedido(pedido);
		if(r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao salvar o pedido", null));	
		}else {
			retorno.setObjeto(r);
		}
		return retorno;
	}
}
