package br.com.trabalho.facade;

import br.com.trabalho.dao.ClienteDAO;
import br.com.trabalho.model.Cliente;
import br.com.trabalho.util.ErroMessenger;
import br.com.trabalho.util.Retorno;

public class ClienteFacade {
	private ClienteDAO dao = new ClienteDAO();

	public Retorno listClientes() {
		Retorno retorno = new Retorno();
		Object r = dao.listClientes();
		if(r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao buscar os registros.", (RuntimeException)r));
		}else {
			retorno.setObjeto(r);
		}
		return retorno;
	}

	public Retorno setCliente(Cliente cliente) {
		Retorno retorno = new Retorno();
		if(validaCliente(cliente)) {
			Retorno c = verificaCpf(cliente);
			if(!c.getObjeto().equals("true")) {
				retorno.setErro(c.getErro());				
			}else {
				Object r = dao.setCliente(cliente);
				if(r.getClass() == RuntimeException.class) {
					retorno.setErro(new ErroMessenger("Ocorreu um erro ao inserir o registro.", (RuntimeException)r));
				}else {
					retorno.setObjeto(r);
				}
			}
			return retorno;			
		}else {
			retorno.setErro(new ErroMessenger("Todos os campos devem ser preenchidos", null));
			return retorno;			
		}
	}

	private boolean validaCliente(Cliente cliente) {
		boolean status = true;
		if(cliente.getCpf().equals("")) {
			status = false;
		}else if(cliente.getNome().equals("")) {
			status = false;
		}else if(cliente.getSobreNome().equals("")) {
			status = false;
		}
		
		return status;
	}

	public Retorno editaCliente(Cliente cliente) {
		Retorno retorno = new Retorno();
		
		if(validaCliente(cliente)) {
			Retorno c = verificaCpfEditar(cliente);
			if(!c.getObjeto().equals("true")) {
				retorno.setErro(c.getErro());				
			}else {
				Object r = dao.editarCliente(cliente);
				if(r.getClass() == RuntimeException.class) {
					retorno.setErro(new ErroMessenger("Ocorreu um erro ao editar o registro.", (RuntimeException)r));
				}else {
					retorno.setObjeto(r);
				}
			}
		}
		
		return retorno;
	}
	
	public Retorno verificaCpf(Cliente cliente) {
		Retorno retorno = new Retorno();
		retorno.setObjeto("");
		
		Object r = dao.getCliente(cliente.getCpf());
		if(r != null && r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao editar o cliente.", (RuntimeException)r));
		}else {
			if(r != null && ((Cliente)r).getId() != cliente.getId()) {
				retorno.setErro(new ErroMessenger("O CPF informado já existe.", null));				
			}else {
				retorno.setObjeto("true");
			}
		}
		return retorno;
	}

	public Retorno verificaCpfEditar(Cliente cliente) {
		Retorno retorno = new Retorno();
		retorno.setObjeto("");
		Object r = dao.getCliente(cliente.getCpf());
		if(r != null && r.getClass() == RuntimeException.class) {
			retorno.setErro(new ErroMessenger("Ocorreu um erro ao editar o cliente.", (RuntimeException)r));
		}else {
			if(((Cliente)r).getId() == cliente.getId()){
				retorno.setObjeto("true");				
			}else {
				retorno.setErro(new ErroMessenger("O CPF informado já existe.", (RuntimeException)r));				
			}
		}
		retorno.setObjeto("true");
		return retorno;
	}

	public Retorno excluirCliente(int codigo) {
		Retorno retorno = new Retorno();
		
		Object r = dao.deleteCliente(codigo);
		if(r != null) {
			if(r.getClass() == RuntimeException.class) {
				retorno.setErro(new ErroMessenger("Ocorreu um erro ao editar o cliente.", (RuntimeException)r));
			}else {
				retorno.setErro(new ErroMessenger("O cliente selecionado possui pedidos pendentes e não pode ser removido.", null));				
			}
		}else {
			retorno.setObjeto("true");
		}
		return retorno;
	}
	
	public Retorno getCliente(String cpf) {
		Retorno retorno = new Retorno();
		
		Object r = dao.getCliente(cpf);
		if(r != null) {
			if(r.getClass() == RuntimeException.class) {
				retorno.setErro(new ErroMessenger("Ocorreu um erro ao buscar o cliente", (RuntimeException)r));
			}else {
				retorno.setObjeto(r);
			}
		}else {
			retorno.setErro(new ErroMessenger("Cliente não encontrado.", null));			
		}
		return retorno;
	}
}
